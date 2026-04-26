package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.CustomerOrdersDTO;
import com.myprojects.realtimekotsystem.dto.response.OrderStatusDTO;
import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping(
            path = "/active"
    )
    public ResponseEntity<ApiResponse<List<OrdersDTO>>> getActiveOrdersForKitchen() {
        List<OrdersDTO> result = orderService.getActiveOrdersForKitchen();
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Active orders for kitchen"
                )
        );
    }

    @GetMapping(
            path = "/{id}/active"
    )
    public ResponseEntity<ApiResponse<CustomerOrdersDTO>> getOrdersForCustomer(@PathVariable Long id) {
        CustomerOrdersDTO result = orderService.getOrdersForCustomer(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Orders for customer " + id
                )
        );
    }

    @PatchMapping(
            path = "{id}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<OrderStatusDTO>> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> status) {
        OrderStatusDTO result = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order status updated successfully"
                )
        );
    }
    
}
