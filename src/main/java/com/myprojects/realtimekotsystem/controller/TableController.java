package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.service.TablesService;
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
    @PostMapping(
            consumes = "application/json"
            )
    public ResponseEntity<ApiResponse<TablesDTO>> addTable(@RequestBody TablesDTO tableDTO) {
        TablesDTO result = tablesService.addTable(tableDTO);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "New table added successfully"
                )
        );
    }

    // TO GET ALL TABLES
    @GetMapping()
    public ResponseEntity<ApiResponse<List<TablesDTO>>> getTables() {
        List<TablesDTO> reuslt = tablesService.getAllTables();
        return ResponseEntity.ok(
                ApiResponse.success(
                        reuslt,
                        "All tables Information"
                )
        );
    }

    // TO UPDATE TABLE STATUS
    @PatchMapping(
            path = "/{id}/status",
            consumes = "application/json"
    )
    public ResponseEntity<ApiResponse<TablesDTO>> updateTableStatus(@PathVariable Long id, @RequestBody Map<String, String> tableStatus) {
        TablesDTO result = tablesService.updateTable(id, tableStatus);
        return ResponseEntity.ok(
                ApiResponse.success(
                        result,
                        "Table status updated successfully"
                )
        );
    }

    // TO DELETE A TABLE
    @DeleteMapping(
            path = "/{tableNumber}"
    )
    public ResponseEntity<?> deleteTable(@PathVariable int tableNumber) {
        tablesService.deleteTable(tableNumber);
        return ResponseEntity.noContent().build();
    }
}
