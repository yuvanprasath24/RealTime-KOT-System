package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tables_Repo extends JpaRepository<Tables, Long> {
    Tables findByTableNumber(int tableNumber);
}
