package com.myprojects.realtimekotsystem.mappers;

import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.entity.Menu_items;


import java.util.ArrayList;
import java.util.List;

public class MenuItemMapper {

    public static MenuItemDTO toDto(Menu_items menu_items) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();

        menuItemDTO.setName(menu_items.getName());
        menuItemDTO.setPrice(menu_items.getPrice());
        menuItemDTO.setCategory(menu_items.getCategory());
        menuItemDTO.setMenuStatus(menu_items.getStatus());
        
        return menuItemDTO;
    }

    public static List<MenuItemDTO> toDtoList(List<Menu_items> menu_items) {
        List<MenuItemDTO> menuItemDTOs = new ArrayList<>();
        for (Menu_items menu_item : menu_items) {
            menuItemDTOs.add(toDto(menu_item));
        }
        return menuItemDTOs;
    }

    public static Menu_items toEntity(MenuItemDTO menuItemDTO) {
        Menu_items menu_items = new Menu_items();
        menu_items.setName(menuItemDTO.getName());
        menu_items.setPrice(menuItemDTO.getPrice());
        menu_items.setCategory(menuItemDTO.getCategory());
        menu_items.setStatus(menuItemDTO.getMenuStatus());
        return menu_items;
    }
}
