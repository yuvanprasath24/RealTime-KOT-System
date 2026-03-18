package com.myprojects.realtimekotsystem.controller;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(
        path = "/api/tables"
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
}
