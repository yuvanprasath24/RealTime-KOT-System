package com.myprojects.realtimekotsystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long menuItemId;
    private int quantity;
}
