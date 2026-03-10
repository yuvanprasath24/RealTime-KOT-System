package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.entity.MenuStatus;
import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.exception.ResourceNotFoundException;
import com.myprojects.realtimekotsystem.mappers.MenuItemMapper;
import com.myprojects.realtimekotsystem.repository.Menu_items_Repo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private Menu_items_Repo menu_items_repo;

    @Transactional
    public MenuItemDTO post_menu_items(MenuItemDTO menuItemDTO) {

        Menu_items menuItems =  MenuItemMapper.toEntity(menuItemDTO);
        menu_items_repo.save(menuItems);

        return MenuItemMapper.toDto(menuItems);
    }

    public List<MenuItemDTO> get_menu_items() {
        List<Menu_items> menuItems = menu_items_repo.findAll();
        return MenuItemMapper.toDtoList(menuItems);
    }

    @Transactional
    public MenuItemDTO updateMenuStatus(Long id, Map<String, String> menuStatus) {

        MenuStatus status = MenuStatus.valueOf(menuStatus.get("status"));

        Menu_items menu_items = menu_items_repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu items not found"));

        menu_items.setStatus(status);
        menu_items_repo.save(menu_items);

        return MenuItemMapper.toDto(menu_items);
    }
}
