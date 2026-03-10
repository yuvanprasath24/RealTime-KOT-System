package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Entity
@Table(name = "menu_items", indexes = {
        @Index(name = "idx_category", columnList = "category"),
        @Index(name = "idx_status", columnList = "status")
})
@Data
@Getter
@Setter
public class Menu_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuStatus status;
}
