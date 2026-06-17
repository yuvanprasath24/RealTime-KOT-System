package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Menu_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Menu_items_Repo extends JpaRepository<Menu_items, Long> {
    List<Menu_items> findByRestaurantId(Long restaurantId);
}
