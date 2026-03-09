package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Orders {
    @Id
    private Long id;
    private Long tableId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date date;
}
