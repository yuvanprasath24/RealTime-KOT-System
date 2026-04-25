package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.OrderItemStatus;
import lombok.Data;

@Data
public class OrderItemDTO {
    private String menuItemName;
    private int quantity;
}
