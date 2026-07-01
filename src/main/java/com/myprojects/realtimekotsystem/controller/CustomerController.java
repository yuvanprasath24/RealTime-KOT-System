package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.request.CreateOrderRequest;
import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.CustomerOrdersDTO;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.dto.response.OrdersDTO;
import com.myprojects.realtimekotsystem.service.AdminService;
import com.myprojects.realtimekotsystem.service.OrderService;
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
}
