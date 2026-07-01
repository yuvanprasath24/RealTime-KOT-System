package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.response.*;
import com.myprojects.realtimekotsystem.service.AdminService;
import com.myprojects.realtimekotsystem.service.OrderService;
import com.myprojects.realtimekotsystem.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "public/api"
)
public class CustomerController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantService restaurantService;

    // TO GET RESTAURANT
    @GetMapping(
            path = "/restaurant"
    )
    public ResponseEntity<ApiResponse<RestaurantDTO>> getRestaurantName(@RequestParam Long restaurantID) {
        RestaurantDTO result = restaurantService.getRestaurant(restaurantID);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Restaurant Details"
                )
        );
    }

    // TO GET MENU ITEMS
    @GetMapping(
            path = "/menu_items/customer/all"
    )
    public ResponseEntity<ApiResponse<List<MenuItemDTO>>> getMenuItemsCustomer(@RequestParam Long restaurantID) {
        List <MenuItemDTO> result = adminService.getMenuItems(restaurantID);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Menu Items retrived"
                )
        );
    }

    // TO POST NEW ORDER
    @PostMapping(
            path = "/orders"
    )
    public ResponseEntity<ApiResponse<CustomerOrdersDTO>> createOrder(
            @RequestParam Long restaurantID,
            @RequestBody CreateOrderRequest createOrderRequest
            ) {
        CustomerOrdersDTO result = orderService.createOrders(createOrderRequest, restaurantID);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order created"
                )
        );
    }

    // TO GET ALL ACTIVE ORDERS FOR A CUSTOMER
    @GetMapping(
            path = "orders/{tableId}/active"
    )
    public ResponseEntity<ApiResponse<CustomerOrdersDTO>> getOrdersForCustomer(
            @PathVariable Long tableId,
            @RequestParam Long restaurantID) {
        CustomerOrdersDTO result = orderService.getOrdersForCustomer(tableId, restaurantID);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Orders for customer " + tableId
                )
        );
    }

    // TO CLOSE A ORDER
    @PatchMapping(
            path = "{orderId}/close"
    )
    public ResponseEntity<ApiResponse<OrdersDTO>> deleteOrder(
            @PathVariable Long orderId,
            @RequestParam Long restaurantID) {
        OrdersDTO result = orderService.closeOrder(orderId, restaurantID);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Order closed successfully"
                )
        );
    }
}
