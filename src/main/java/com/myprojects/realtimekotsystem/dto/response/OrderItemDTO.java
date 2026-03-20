package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.entity.OrderItemStatus;
import com.myprojects.realtimekotsystem.entity.Orders;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Orders orders;
    private Menu_items menu_items;
    private int quantity;
    private double priceAtOrderTime;
    private OrderItemStatus status;
}
