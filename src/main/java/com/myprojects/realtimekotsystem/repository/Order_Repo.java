package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.OrderStatus;
import com.myprojects.realtimekotsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order_Repo extends JpaRepository<Orders, Long> {
    List<Orders> findByStatusInOrderByCreatedAtAsc(List<OrderStatus> activeStatus);
}
