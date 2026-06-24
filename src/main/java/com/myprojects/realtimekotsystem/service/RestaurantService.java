package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.entity.Restaurant;
import com.myprojects.realtimekotsystem.entity.User;
import com.myprojects.realtimekotsystem.repository.RestaurantRepo;
import com.myprojects.realtimekotsystem.repository.UserRepo;
import com.myprojects.realtimekotsystem.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;

    public Map<String, String> setupRestaurant(String email, String restaurantName){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRestaurant() != null) {
            throw new RuntimeException("Restaurant has already been set up");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        user.setRestaurant(savedRestaurant);
        userRepo.save(user);

        String freshToken = jwtUtils.generateToken(user.getEmail(),user.getRole(), savedRestaurant.getId());

        return Map.of("token", freshToken);
    }

    public String getUserName(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }
}
