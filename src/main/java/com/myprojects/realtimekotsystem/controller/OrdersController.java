package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.entity.Orders;
import com.myprojects.realtimekotsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<OrdersDTO>> createOrder(@RequestBody CreateOrderRequest request) {

        OrdersDTO result = orderService.createOrders(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order created successfully"
                )
        );
    }

    
}
