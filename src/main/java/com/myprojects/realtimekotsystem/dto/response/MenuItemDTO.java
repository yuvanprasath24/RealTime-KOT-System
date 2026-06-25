package com.myprojects.realtimekotsystem.dto.response;

import com.myprojects.realtimekotsystem.entity.Category;
import com.myprojects.realtimekotsystem.entity.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Data
public class MenuItemDTO {
    private Long id;
    private String name;
    private Double price;
    private Category category;
    private MenuStatus menuStatus;
}
