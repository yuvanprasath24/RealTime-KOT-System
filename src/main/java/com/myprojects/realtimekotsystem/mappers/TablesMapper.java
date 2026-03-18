package com.myprojects.realtimekotsystem.mappers;

import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.Tables;

import java.util.ArrayList;
import java.util.List;

public class TablesMapper {

    public  static TablesDTO toDTO(Tables table){
        TablesDTO dto = new TablesDTO();

        dto.setId(table.getId());
        dto.setTable_number(table.getTableNumber());
        dto.setStatus(table.getStatus());

        return dto;
    }

    public static Tables toEntity(TablesDTO dto){
        Tables table = new Tables();
        table.setId(dto.getId());
        table.setTableNumber(dto.getTable_number());
        table.setStatus(dto.getStatus());
        return table;
    }

    public static List<TablesDTO> toDTO(List<Tables> tables){
        List<TablesDTO> dtos = new ArrayList<TablesDTO>();
        for (Tables table : tables) {
            dtos.add(toDTO(table));
        }
        return dtos;
    }
}
