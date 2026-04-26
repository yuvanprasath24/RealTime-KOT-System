package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusDTO {
    private Long orderId;
    private OrderStatus orderStatus;
}
