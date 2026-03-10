package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.service.AdminService;
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
        MenuItemDTO saved = adminService.post_menu_items(menuItemDTO);
        return ResponseEntity.ok(
                ApiResponse.success(
                        saved,
                        "New menu item added successfully"
                )
        );
    }

    // TO GET ALL MENU ITEMS
    @GetMapping()
    public List<MenuItemDTO> getMenu_items() {
        return adminService.get_menu_items();
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

        MenuItemDTO updatedMenu = adminService.updateMenuStatus(id, menuStatus);

        return ResponseEntity.ok(
                ApiResponse.success(
                        updatedMenu,
                        "Item status updated"
                )
        );
    }
}
