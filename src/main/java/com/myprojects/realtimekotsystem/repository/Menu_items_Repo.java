package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Menu_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Menu_items_Repo extends JpaRepository<Menu_items, Long> {
}
