package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Tables {
    @Id
    private Long id;
    private int table_number;
    @Enumerated(EnumType.STRING)
    private TableStatus status;
}
