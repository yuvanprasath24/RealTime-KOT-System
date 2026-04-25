package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDTO {
    private Long id;
    private TablesDTO table;
    private OrderStatus status;
    private List<OrderItemDTO> orderItem;
}
