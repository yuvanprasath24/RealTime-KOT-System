package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_Repo extends JpaRepository<Orders, Long> {
}
