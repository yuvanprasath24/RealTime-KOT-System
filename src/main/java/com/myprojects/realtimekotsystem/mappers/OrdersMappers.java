package com.myprojects.realtimekotsystem.mappers;

import com.myprojects.realtimekotsystem.dto.response.KitchenOrdersDTO;
import com.myprojects.realtimekotsystem.dto.response.OrderItemDTO;
import com.myprojects.realtimekotsystem.entity.OrderItems;
import com.myprojects.realtimekotsystem.entity.Orders;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrdersMappers {
    public  KitchenOrdersDTO convertToKitchenOrdersDTO(Orders orders) {
        KitchenOrdersDTO dto = new KitchenOrdersDTO();
        dto.setId(orders.getId());

        dto.setTable(TablesMapper.toDTO(orders.getTable()));

        dto.setStatus(orders.getStatus());

        dto.setOrderItem(
                orders.getOrderItems()
                        .stream()
                        .map(this::convertToOrderItemDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public  OrderItemDTO convertToOrderItemDTO(OrderItems orderItems) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setMenuItemName(orderItems.getMenuItem().getName());
        dto.setQuantity(orderItems.getQuantity());
        return dto;
    }
}
