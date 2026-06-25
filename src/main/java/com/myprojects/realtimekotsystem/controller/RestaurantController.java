package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.entity.Restaurant;
import com.myprojects.realtimekotsystem.entity.User;
import com.myprojects.realtimekotsystem.repository.RestaurantRepo;
import com.myprojects.realtimekotsystem.repository.UserRepo;
import com.myprojects.realtimekotsystem.security.JwtUtils;
import com.myprojects.realtimekotsystem.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(
        path = "api/restaurant",
        produces = "application/json"
)
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(
            path = "/setup"
    )
    public ResponseEntity<ApiResponse<Map<String, String>>> setupRestaurant(@RequestBody Map<String,String> body, HttpServletRequest request) {

        String email = (String) request.getAttribute("email");
        Map<String, String> result = restaurantService.setupRestaurant(email,body.get("restaurantName"));
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Restaurant setup initialized successfully"
                )
        );
    }

    @GetMapping(
            path = "/me"
    )
    public ResponseEntity<ApiResponse<String>> getUserName(HttpServletRequest request){
        String email = (String) request.getAttribute("email");
        String result = restaurantService.getUserName(email);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "User name sent successfully"
                )
        );
    }
}
