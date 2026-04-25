package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.request.OrderItemRequest;
import com.myprojects.realtimekotsystem.dto.response.KitchenOrdersDTO;
import com.myprojects.realtimekotsystem.entity.*;
import com.myprojects.realtimekotsystem.mappers.OrdersMappers;
import com.myprojects.realtimekotsystem.repository.Menu_items_Repo;
import com.myprojects.realtimekotsystem.repository.Order_Item_Repo;
import com.myprojects.realtimekotsystem.repository.Order_Repo;
import com.myprojects.realtimekotsystem.repository.Tables_Repo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
    public String createOrders(CreateOrderRequest request) {

        Tables tables = tables_Repo.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

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

        return "Success";
    }

    public List<KitchenOrdersDTO> getActiveOrdersForKitchen() {
        List<OrderStatus> activeStatus = List.of(OrderStatus.PLACED);
        return order_Repo.findByStatusInOrderByCreatedAtAsc(activeStatus)
                .stream()
                .map(mappers::convertToKitchenOrdersDTO)
                .collect(Collectors.toList());
    }
}
