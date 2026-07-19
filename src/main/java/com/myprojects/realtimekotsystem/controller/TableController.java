package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.service.TablesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Tables", description = "Table management APIs")
@RestController
@RequestMapping(
        path = "/api/tables",
        produces = "application/json"
)
public class TableController {

    @Autowired
    private TablesService tablesService;

    // TO ADD TABLES
    @Operation(summary = "Create a new table")
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
            path = "/{tableId}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<TablesDTO>> updateTableStatus(
            @PathVariable Long tableId,
            @RequestBody Map<String, String> tableStatus,
            HttpServletRequest request ) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        TablesDTO result = tablesService.updateTable(tableId, tableStatus, restaurantId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Table status updated successfully"
                )
        );
    }

    // TO DELETE A TABLE
    @DeleteMapping(
            path = "/{tableId}/delete"
    )
    public ResponseEntity<?> deleteTable(@PathVariable Long tableId, HttpServletRequest request) {
        Long restaurantId = (Long) request.getAttribute("restaurantId");
        tablesService.deleteTable(tableId, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
