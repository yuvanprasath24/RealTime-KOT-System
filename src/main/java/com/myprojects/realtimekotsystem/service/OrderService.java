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
    public OrdersDTO createOrders(CreateOrderRequest request) {

        Tables tables = tables_Repo.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        if(tables.getStatus() == TableStatus.OCCUPIED){
            throw new RuntimeException("Table is occupied");
        }

        Orders orders = new Orders();
        orders.setTable(tables);
        orders.setStatus(OrderStatus.PLACED);

        double totalAmount = 0;

        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            Menu_items menuItems = menu_items_Repo.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found"));

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

    public List<OrdersDTO> getActiveOrdersForKitchen() {
        List<OrderStatus> activeStatus = List.of(OrderStatus.PLACED);
        return order_Repo.findByStatusInOrderByCreatedAtAsc(activeStatus)
                .stream()
                .map(mappers::convertToOrdersDTO)
                .collect(Collectors.toList());
    }

    public CustomerOrdersDTO getOrdersForCustomer(Long tableId) {
        return order_Repo.findFirstByTableIdAndStatusNotOrderByCreatedAtDesc(tableId, OrderStatus.CLOSED)
                .map(mappers::convertToCustomerOrdersDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No active order for this table"));

    }

    @Transactional
    public OrderStatusDTO updateOrderStatus(Long orderId, Map<String, String> orderStatus) {

        OrderStatus status = OrderStatus.valueOf(orderStatus.get("status"));
        Orders orders = order_Repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orders.setStatus(status);

        if(status == OrderStatus.CLOSED) {
            Tables tables = orders.getTable();
            if (tables != null) {
                tables.setStatus(TableStatus.VACANT);
            }
        }

        Orders saveOrder = order_Repo.save(orders);
        return mappers.convertToOrderStatusDTO(saveOrder);
    }
}
