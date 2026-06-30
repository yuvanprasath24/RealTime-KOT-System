package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "public/api/menu_items"
)
public class MenuController {

    @Autowired
    private AdminService adminService;

    // TO GET MENU ITEMS
    @GetMapping(
            path = "customer/all"
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
}
