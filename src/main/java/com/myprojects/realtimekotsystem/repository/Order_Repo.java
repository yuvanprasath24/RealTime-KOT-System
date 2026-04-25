package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.entity.OrderStatus;
import com.myprojects.realtimekotsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Order_Repo extends JpaRepository<Orders, Long> {
    List<Orders> findByStatusInOrderByCreatedAtAsc(List<OrderStatus> activeStatus);

    Optional<Orders> findFirstByTableIdAndStatusNotOrderByCreatedAtDesc(Long tableId, OrderStatus status);
}
