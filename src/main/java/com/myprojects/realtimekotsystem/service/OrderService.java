package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.request.OrderItemRequest;
import com.myprojects.realtimekotsystem.dto.response.CustomerOrdersDTO;
import com.myprojects.realtimekotsystem.dto.response.OrderStatusDTO;
import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.entity.*;
import com.myprojects.realtimekotsystem.exception.ResourceNotFoundException;
import com.myprojects.realtimekotsystem.mappers.OrdersMappers;
import com.myprojects.realtimekotsystem.mappers.TablesMapper;
import com.myprojects.realtimekotsystem.repository.Menu_items_Repo;
import com.myprojects.realtimekotsystem.repository.Order_Item_Repo;
import com.myprojects.realtimekotsystem.repository.Order_Repo;
import com.myprojects.realtimekotsystem.repository.Tables_Repo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private Order_Repo order_Repo;

    @Autowired
    private Order_Item_Repo order_Item_Repo;

    @Autowired
    private Menu_items_Repo menu_items_Repo;

    @Autowired
    private Tables_Repo tables_Repo;

    @Autowired
    private OrdersMappers mappers;

    @Transactional
    public OrdersDTO createOrders(CreateOrderRequest request,Long restaurant_id){

        Tables tables = tables_Repo.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        if(!tables.getRestaurant().getId().equals(restaurant_id)){
            throw new RuntimeException("Cross-tenant access violation: Table does not belong to this restaurant");
        }

        if(tables.getStatus() == TableStatus.OCCUPIED){
            throw new RuntimeException("Table is occupied");
        }

        Restaurant restaurant = tables.getRestaurant();

        Orders orders = new Orders();
        orders.setTable(tables);
        orders.setStatus(OrderStatus.PLACED);
        orders.setRestaurant(restaurant);

        double totalAmount = 0;

        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            Menu_items menuItems = menu_items_Repo.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found"));

            if (!menuItems.getRestaurant().getId().equals(restaurant_id)) {
                throw new RuntimeException("Domain mismatch on item request");
            }

            OrderItems orderItems = new OrderItems();
           // orderItems.setOrders(orders);
            orderItems.setMenuItem(menuItems);
            orderItems.setQuantity(itemRequest.getQuantity());
            orderItems.setStatus(OrderItemStatus.PENDING);
            orderItems.setPriceAtOrderTime(menuItems.getPrice());

            totalAmount += menuItems.getPrice() * itemRequest.getQuantity();

            orders.addOrderItems(orderItems);
        }

        orders.setTotalAmount(totalAmount);

        tables.setStatus(TableStatus.OCCUPIED);

        Orders savedOrders = order_Repo.save(orders);

        return mappers.convertToOrdersDTO(savedOrders);
    }

    public List<OrdersDTO> getActiveOrdersForKitchen(Long restaurantId) {

        List<OrderStatus> activeStatus = List.of(OrderStatus.PLACED);
        return order_Repo.findByRestaurantIdAndStatusInOrderByCreatedAtAsc(restaurantId,activeStatus)
                .stream()
                .map(mappers::convertToOrdersDTO)
                .collect(Collectors.toList());
    }

    public CustomerOrdersDTO getOrdersForCustomer(Long tableId,Long restaurantId) {
        return order_Repo.findFirstByTableIdAndRestaurantIdAndStatusNotOrderByCreatedAtDesc(tableId, restaurantId ,OrderStatus.CLOSED)
                .map(mappers::convertToCustomerOrdersDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No active operational order found for this table under the current restaurant domain"));

    }

    @Transactional
    public OrderStatusDTO updateOrderStatus(Long orderId, Map<String, String> orderStatus,Long restaurantId) {

        OrderStatus status = OrderStatus.valueOf(orderStatus.get("status"));
        Orders orders = order_Repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orders.setStatus(status);

        if (!orders.getRestaurant().getId().equals(restaurantId)) {
            throw new SecurityException("Unauthorized cross-tenant state mutation blocked");
        }

        if(status == OrderStatus.CLOSED) {
            Tables tables = orders.getTable();
            if (tables != null) {
                tables.setStatus(TableStatus.VACANT);
            }
        }

        Orders saveOrder = order_Repo.save(orders);
        return mappers.convertToOrderStatusDTO(saveOrder);
    }

    @Transactional
    public OrdersDTO updateOrderItemStatus(Long orderItemId, Map<String, String> orderItemStatus,Long restaurantId) {
        OrderItemStatus status = OrderItemStatus.valueOf(orderItemStatus.get("status"));

        OrderItems orderItems = order_Item_Repo.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));


        Orders parentOrder = orderItems.getOrders();
        if (parentOrder == null || !parentOrder.getRestaurant().getId().equals(restaurantId)) {
            throw new SecurityException("Unauthorized manipulation of foreign tenant items blocked");
        }

        orderItems.setStatus(status);
        parentOrder.updateOrderStatusBasedOnItems();
        Orders savedOrder = order_Repo.save(parentOrder);
        return mappers.convertToOrdersDTO(savedOrder);
    }

    @Transactional
    public OrdersDTO appendOrders(Long orderId, List<OrderItemRequest> orderItems ,Long restaurantId) {

        Orders orders = order_Repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!orders.getRestaurant().getId().equals(restaurantId)) {
            throw new SecurityException("Unauthorized access: Order domain mismatch");
        }

        if(orders.getStatus() == OrderStatus.CLOSED) {
            throw new RuntimeException("Cannot add items to a closed order.");
        }

        double addtionalAmount = 0;

        for(OrderItemRequest orderItemRequest : orderItems) {
            Menu_items menuItems = menu_items_Repo.findById(orderItemRequest.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found"));

            if (!menuItems.getRestaurant().getId().equals(restaurantId)) {
                throw new SecurityException("Security Violation: Appended item does not match the active restaurant domain");
            }

            OrderItems orderItem = new OrderItems();
            orderItem.setMenuItem(menuItems);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPriceAtOrderTime(menuItems.getPrice());
            orderItem.setStatus(OrderItemStatus.PENDING);

            orders.addOrderItems(orderItem);

            addtionalAmount += menuItems.getPrice() * orderItemRequest.getQuantity();
        }

        orders.setTotalAmount(orders.getTotalAmount()+ addtionalAmount);
        orders.updateOrderStatusBasedOnItems();

        Orders savedOrders = order_Repo.save(orders);
        return mappers.convertToOrdersDTO(savedOrders);
    }

    @Transactional
    public OrdersDTO closeOrder(Long orderId,Long restaurantId  ) {
        Orders orders = order_Repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(!orders.getRestaurant().getId().equals(restaurantId)) {
            throw new SecurityException("Unauthorized cross-tenant state mutation blocked");
        }

        if(orders.getStatus() == OrderStatus.CLOSED) {
            throw new RuntimeException("Order is already closed");
        }
        orders.setStatus(OrderStatus.CLOSED);

        Tables tables = orders.getTable();

        if(tables != null) {
            if(!tables.getRestaurant().getId().equals(restaurantId)) {
                throw new SecurityException("Unauthorized cross-tenant state mutation blocked");
            }
            tables.setStatus(TableStatus.VACANT);
        }

        return mappers.convertToOrdersDTO(order_Repo.save(orders));
    }
}
