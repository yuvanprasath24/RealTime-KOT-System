package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_Item_Repo extends JpaRepository<OrderItems, Long> {
}
