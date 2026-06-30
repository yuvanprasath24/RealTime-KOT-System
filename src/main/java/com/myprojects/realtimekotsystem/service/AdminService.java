package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.response.ApiResponse;
import com.myprojects.realtimekotsystem.dto.response.MenuItemDTO;
import com.myprojects.realtimekotsystem.entity.MenuStatus;
import com.myprojects.realtimekotsystem.entity.Menu_items;
import com.myprojects.realtimekotsystem.entity.Restaurant;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.exception.ResourceNotFoundException;
import com.myprojects.realtimekotsystem.mappers.MenuItemMapper;
import com.myprojects.realtimekotsystem.repository.Menu_items_Repo;
import com.myprojects.realtimekotsystem.repository.RestaurantRepo;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Transactional
    public MenuItemDTO post_menu_items(MenuItemDTO menuItemDTO, Long restaurant_id) {

        Restaurant restaurant = restaurantRepo.findById(restaurant_id)
                .orElseThrow(() -> new RuntimeException("Restaurant Profile Could Not Found"));

        Menu_items menu_items = MenuItemMapper.toEntity(menuItemDTO);
        menu_items.setRestaurant(restaurant);
        Menu_items savedMenuItem = menu_items_repo.save(menu_items);

        return MenuItemMapper.toDto(savedMenuItem);
    }

    public List<MenuItemDTO> getMenuItems(Long restaurant_id) {
        List<Menu_items> menu_items = menu_items_repo.findByRestaurantId(restaurant_id);
        return MenuItemMapper.toDtoList(menu_items);
    }

    @Transactional
    public MenuItemDTO updateMenuStatus(Long id, Map<String, String> menuStatus, Long restaurant_id) {

        MenuStatus status = MenuStatus.valueOf(menuStatus.get("menuStatus"));

        Menu_items menu_items = menu_items_repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu items not found"));

        if(!menu_items.getRestaurant().getId().equals(restaurant_id)) {
            throw new RuntimeException("Restaurant Profile Could Not Found");
        }

        menu_items.setStatus(status);
        return MenuItemMapper.toDto(menu_items_repo.save(menu_items));
    }

    public void deleteMenuItem(Long id, Long restaurant_id) {
        Menu_items menuItems = menu_items_repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu items not found"));
        if(!menuItems.getRestaurant().getId().equals(restaurant_id)) {
            throw new RuntimeException("Restaurant Profile Could Not Found");
        }
        menu_items_repo.delete(menuItems);
    }

    @Transactional
    public MenuItemDTO updateMenu_items(Long id, MenuItemDTO menuItemDTO, Long restaurantId) {
        Menu_items menu_items = menu_items_repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu items not found"));

        if(!menu_items.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Restaurant Profile Could Not Found");
        }
        menu_items.setName(menuItemDTO.getName());
        menu_items.setPrice(menuItemDTO.getPrice());
        menu_items.setCategory(menuItemDTO.getCategory());
        menu_items.setStatus(menuItemDTO.getMenuStatus());
        return MenuItemMapper.toDto(menu_items_repo.save(menu_items));
    }
}
