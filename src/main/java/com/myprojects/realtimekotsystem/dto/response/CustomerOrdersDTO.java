package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class CustomerOrdersDTO {
    private Long id;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemDTO> orderItem;
}
