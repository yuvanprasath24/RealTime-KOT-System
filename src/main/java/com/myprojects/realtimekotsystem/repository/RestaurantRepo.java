package com.myprojects.realtimekotsystem.repository;

import com.myprojects.realtimekotsystem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findById(Long restaurantId);
}
