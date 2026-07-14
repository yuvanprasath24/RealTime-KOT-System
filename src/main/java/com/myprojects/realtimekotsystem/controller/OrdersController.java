package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.request.OrderItemRequest;
import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.CustomerOrdersDTO;
import com.myprojects.realtimekotsystem.dto.response.OrderStatusDTO;
import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.ast.Or;
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

    // TO POST NEW ORDERS
//    @PostMapping(
//            consumes = "application/json"
//    )
//    public ResponseEntity<ApiResponse<OrdersDTO>> createOrder(
//            @RequestBody CreateOrderRequest request,
//            HttpServletRequest httpServletRequest) {
//        Long restaurantId = (Long)  httpServletRequest.getAttribute("restaurantId");
//        OrdersDTO result = orderService.createOrders(request,restaurantId);
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        result,
//                        "Order created successfully"
//                )
//        );
//    }

    // TO GET ALL ACTIVE ORDERS
    @GetMapping(
            path = "/active"
    )
    public ResponseEntity<ApiResponse<List<OrdersDTO>>> getActiveOrdersForKitchen(HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        List<OrdersDTO> result = orderService.getActiveOrdersForKitchen(restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Active orders for kitchen"
                )
        );
    }

    // TO GET ALL ACTIVE ORDERS FOR A CUSTOMER
    @GetMapping(
            path = "/{id}/active"
    )
    public ResponseEntity<ApiResponse<CustomerOrdersDTO>> getOrdersForCustomer(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        CustomerOrdersDTO result = orderService.getOrdersForCustomer(id, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Orders for customer " + id
                )
        );
    }

    // TO FORCE ORDER STATUS
    @PatchMapping(
            path = "{id}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<OrderStatusDTO>> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> status,
            HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        OrderStatusDTO result = orderService.updateOrderStatus(id, status,restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order status updated successfully"
                )
        );
    }

    // TO UPDATE ORDER ITEM STATUS
    @PatchMapping(
            path = "orderItem/{id}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<OrdersDTO>> updateOrderItemStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> status,
            HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        OrdersDTO result = orderService.updateOrderItemStatus(id, status,restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order item status updated successfully"
                )
        );
    }

    // TO APPEND ORDERS
    @PatchMapping(
            path = "{orderId}/append",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<OrdersDTO>> appendOrder(
            @PathVariable Long orderId,
            @RequestBody List<OrderItemRequest> orderItems,
            HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        OrdersDTO result = orderService.appendOrders(orderId, orderItems,restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order append successfully"
                )
        );
    }

    // TO CLOSE A ORDER
    @PatchMapping(
            path = "{orderId}/close"
    )
    public ResponseEntity<ApiResponse<OrdersDTO>> deleteOrder(
            @PathVariable Long orderId,
            HttpServletRequest httpServletRequest) {
        Long restaurantId = (Long) httpServletRequest.getAttribute("restaurantId");
        OrdersDTO result = orderService.closeOrder(orderId, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order closed successfully"
                )
        );
    }
}
