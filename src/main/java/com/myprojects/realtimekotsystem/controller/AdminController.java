package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.service.AdminService;
import com.myprojects.realtimekotsystem.service.TablesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/api/menu_items",
        produces = "application/json"
)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // TO POST MENU ITEMS
    @PostMapping(
            path = "/addMenu",
            consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<MenuItemDTO>> postMenu_items(
            @RequestBody MenuItemDTO menuItemDTO,
            HttpServletRequest request) {

        Long restaurantId = (Long) request.getAttribute("restaurantId");

        MenuItemDTO result = adminService.post_menu_items(menuItemDTO, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "New menu item added successfully"
                )
        );
    }

    // TO GET ALL MENU ITEMS
    @GetMapping(
            path = "admin/all"
    )
    public ResponseEntity<ApiResponse<List<MenuItemDTO>>> getMenuItemsAdmin(HttpServletRequest request) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        List<MenuItemDTO> result = adminService.getMenuItems(restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Menu Items retrived"
                )
        );
    }



    // TO UPDATE THE MENU ITEM STATUS
    @PatchMapping(
            path = "/{id}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<MenuItemDTO>> updateMenuStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> menuStatus,
            HttpServletRequest request
            ){
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        MenuItemDTO result = adminService.updateMenuStatus(id, menuStatus, restaurantId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Item status updated"
                )
        );
    }

    // TO UPDATE THE EXISTING MENU ITEM
    @PutMapping(
            path = "/{id}/update",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<MenuItemDTO>> updateMenu_items(
            @PathVariable Long id,
            @RequestBody MenuItemDTO menuItemDTO,
            HttpServletRequest request
    ){
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        MenuItemDTO result = adminService.updateMenu_items(id, menuItemDTO, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Item updated"
                )
        );
    }

    // TO DELETE A TABLE
    @DeleteMapping(
            path = "/{id}/delete"
    )
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long id, HttpServletRequest request) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        adminService.deleteMenuItem(id, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
