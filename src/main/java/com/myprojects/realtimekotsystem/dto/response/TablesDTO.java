package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.TableStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TablesDTO {
    private Long tableId;
    private int table_number;
    private TableStatus status;
}
