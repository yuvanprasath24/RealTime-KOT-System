package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import com.myprojects.realtimekotsystem.entity.Tables;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersDTO {
    private Long id;
    private Tables table;
    private OrderStatus status;
    private double totalAmount;
    private LocalDateTime createdAt;
}
