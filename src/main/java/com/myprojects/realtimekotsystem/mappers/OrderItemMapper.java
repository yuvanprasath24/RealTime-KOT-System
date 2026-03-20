package com.myprojects.realtimekotsystem.mappers;

import com.myprojects.realtimekotsystem.dto.response.OrderItemDTO;
import com.myprojects.realtimekotsystem.entity.OrderItems;

import java.util.ArrayList;
import java.util.List;

public class OrderItemMapper {

    public static OrderItemDTO toDto(OrderItems orderItems) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItems.getId());
        dto.setOrders(orderItems.getOrders());
        dto.setMenu_items(orderItems.getMenuItem());
        dto.setQuantity(orderItems.getQuantity());
        dto.setPriceAtOrderTime(orderItems.getPriceAtOrderTime());
        dto.setStatus(orderItems.getStatus());
        return dto;
    }

    public static List<OrderItemDTO> toDto(List<OrderItems> orderItems) {
        List<OrderItemDTO> dtos = new ArrayList<OrderItemDTO>();
        for (OrderItems orderItem : orderItems) {
            dtos.add(toDto(orderItem));
        }
        return dtos;
    }

    public static OrderItems toEntity(OrderItemDTO dto) {
        OrderItems entity = new OrderItems();
        entity.setId(dto.getId());
        entity.setOrders(dto.getOrders());
        entity.setMenuItem(dto.getMenu_items());
        entity.setQuantity(dto.getQuantity());
        entity.setPriceAtOrderTime(dto.getPriceAtOrderTime());
        entity.setStatus(dto.getStatus());
        return entity;
    }



}
