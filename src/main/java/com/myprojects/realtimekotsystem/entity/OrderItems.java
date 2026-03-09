package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderItems {
    @Id
    private Long id;
    private Long orderId;
    private Long menuItemId;
    private int quantity;

}
