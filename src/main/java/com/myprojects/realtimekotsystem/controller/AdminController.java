package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.service.AdminService;
import com.myprojects.realtimekotsystem.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/api/menu_items",
        produces = "application/json"
)
public class AdminController {

    @Autowired
    private AdminService adminService;

    // TO POST MENU ITEMS
    @PostMapping(
            consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<MenuItemDTO>> postMenu_items(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO result = adminService.post_menu_items(menuItemDTO);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "New menu item added successfully"
                )
        );
    }

    // TO GET ALL MENU ITEMS
    @GetMapping()
    public ResponseEntity<ApiResponse<List<MenuItemDTO>>> getMenu_items() {
        List<MenuItemDTO> result = adminService.get_menu_items();
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
            @RequestBody Map<String, String> menuStatus
            ){

        MenuItemDTO result = adminService.updateMenuStatus(id, menuStatus);

        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Item status updated"
                )
        );
    }
}
