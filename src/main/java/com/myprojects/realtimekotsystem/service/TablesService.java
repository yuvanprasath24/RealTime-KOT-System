package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.TableStatus;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.mappers.TablesMapper;
import com.myprojects.realtimekotsystem.repository.Tables_Repo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TablesService {

    @Autowired
    private Tables_Repo tables_repo;

    @Transactional
    public TablesDTO addTable(TablesDTO tableDTO) {

        Tables table = TablesMapper.toEntity(tableDTO);
        tables_repo.save(table);

        return TablesMapper.toDTO(table);
    }

    public List<TablesDTO> getAllTables() {
        List<Tables> tables = tables_repo.findAll();
        return TablesMapper.toDTO(tables);
    }

    @Transactional
    public TablesDTO updateTable(Long id, Map<String, String> tableStatus) {

        TableStatus status = TableStatus.valueOf(tableStatus.get("status"));
        Tables table = tables_repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setStatus(status);
        tables_repo.save(table);
        return TablesMapper.toDTO(table);
    }

    @Transactional
    public void deleteTable(int tableNumber) {
        Tables tables = tables_repo.findByTableNumber(tableNumber);
        if (tables != null) {
            tables_repo.delete(tables);
        }
        else {
            throw new RuntimeException("Table not found");
        }
    }
}
