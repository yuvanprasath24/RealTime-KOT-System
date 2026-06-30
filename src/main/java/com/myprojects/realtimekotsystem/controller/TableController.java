package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.service.TablesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(
        path = "/api/tables",
        produces = "application/json"
)
public class TableController {

    @Autowired
    private TablesService tablesService;

    // TO ADD TABLES
    @PostMapping()
    public ResponseEntity<ApiResponse<TablesDTO>> addTable(
            HttpServletRequest request ) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        TablesDTO result = tablesService.addTable(restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "New table added successfully"
                )
        );
    }

    // TO GET ALL TABLES
    @GetMapping()
    public ResponseEntity<ApiResponse<List<TablesDTO>>> getTables(HttpServletRequest request) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        List<TablesDTO> result = tablesService.getAllTables(restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "All tables Information"
                )
        );
    }

    // TO UPDATE TABLE STATUS
    @PatchMapping(
            path = "/{tableNumber}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<TablesDTO>> updateTableStatus(
            @PathVariable int tableNumber,
            @RequestBody Map<String, String> tableStatus,
            HttpServletRequest request ) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        TablesDTO result = tablesService.updateTable(tableNumber, tableStatus, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Table status updated successfully"
                )
        );
    }

    // TO DELETE A TABLE
    @DeleteMapping(
            path = "/{tableNumber}/delete"
    )
    public ResponseEntity<?> deleteTable(@PathVariable int tableNumber, HttpServletRequest request) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        tablesService.deleteTable(tableNumber, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
