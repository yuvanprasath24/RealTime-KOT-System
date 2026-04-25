package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import com.myprojects.realtimekotsystem.entity.Tables;
import lombok.Data;

import java.util.List;

@Data
public class KitchenOrdersDTO {
    private Long id;
    private Tables table;
    private OrderStatus status;
    private List<OrderItemResponse> orderItem;
}
