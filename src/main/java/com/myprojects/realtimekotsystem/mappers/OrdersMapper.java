package com.myprojects.realtimekotsystem.mappers;

import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersMapper {
    public static OrdersDTO toOrdersDTO(Orders orders) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(orders.getId());
        dto.setTable(orders.getTable());
        dto.setStatus(orders.getStatus());
        dto.setTotalAmount(orders.getTotalAmount());
        dto.setCreatedAt(orders.getCreatedAt());
        return dto;
    }
    public static List<OrdersDTO> toOrdersDTOList(List<Orders> orders) {
        List<OrdersDTO> dtos = new ArrayList<OrdersDTO>();
        for (Orders order : orders) {
            dtos.add(toOrdersDTO(order));
        }
        return dtos;
    }
    public static Orders toEntity(OrdersDTO dto) {
        Orders orders = new Orders();
        orders.setId(dto.getId());
        orders.setTable(dto.getTable());
        orders.setStatus(dto.getStatus());
        orders.setTotalAmount(dto.getTotalAmount());
        orders.setCreatedAt(dto.getCreatedAt());
        return orders;
    }
}
