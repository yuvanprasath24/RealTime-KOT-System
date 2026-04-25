package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.KitchenOrdersDTO;
import com.myprojects.realtimekotsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "api/orders",
        produces = "application/json"
)
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping(
            consumes = "application/json"
    )
    public String createOrder(@RequestBody CreateOrderRequest request) {

        String result = orderService.createOrders(request);
        return result;
    }

    @GetMapping(
            path = "/active"
    )
    public ResponseEntity<ApiResponse<List<KitchenOrdersDTO>>> getActiveOrdersForKitchen() {
        List<KitchenOrdersDTO> result = orderService.getActiveOrdersForKitchen();
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Active orders for kitchen"
                )
        );
    }
    
}
