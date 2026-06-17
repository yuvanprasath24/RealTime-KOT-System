package com.myprojects.realtimekotsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id",nullable = false)
    private Tables table;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private double totalAmount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;



    //Methods

    public void addOrderItems(OrderItems orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void removeOrderItems(OrderItems orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrders(null);
    }

    // TO UPDATE STATUS
    public void updateOrderStatusBasedOnItems() {
        if(orderItems == null || orderItems.isEmpty()) return;

        boolean allReady = orderItems.stream()
                .allMatch(item -> item.getStatus() == OrderItemStatus.READY);

        boolean anyPreparing = orderItems.stream()
                .anyMatch(orderItem -> orderItem.getStatus() == OrderItemStatus.PREPARING);

//        boolean anyPending = orderItems.stream()
//                .anyMatch(orderItem -> orderItem.getStatus() == OrderItemStatus.PENDING);

        if(allReady) {
            this.setStatus(OrderStatus.READY);
        }else if(anyPreparing) {
            this.setStatus(OrderStatus.COOKING);
        }else {
            this.setStatus(OrderStatus.PLACED);
        }
    }
}
