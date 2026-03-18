package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.mappers.TablesMapper;
import com.myprojects.realtimekotsystem.repository.Tables_Repo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
