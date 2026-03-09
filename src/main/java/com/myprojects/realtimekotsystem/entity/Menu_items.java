package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Menu_items {
    @Id
    private Long id;
    private String name;
    private String price;
    private String category;
    private String status;
}
