package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Tables_Repo extends JpaRepository<Tables, Long> {
    Optional<Tables> findByTableNumber(int tableNumber);

    List<Tables> findByRestaurantId(Long restuarantId);

    @Query("SELECT MAX(t.tableNumber) FROM Tables t")
    Integer findMaxTableNumber();
}
