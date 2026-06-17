package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "restaurant-tables"
)
@Data
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int tableNumber;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
