package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String role;

    @OneToOne
    @JoinColumn(name = "restaurant_id",referencedColumnName = "id")
    private Restaurant restaurant;

}
