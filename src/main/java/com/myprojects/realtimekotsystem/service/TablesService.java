package com.myprojects.realtimekotsystem.service;

import com.myprojects.realtimekotsystem.dto.response.TablesDTO;
import com.myprojects.realtimekotsystem.entity.Restaurant;
import com.myprojects.realtimekotsystem.entity.TableStatus;
import com.myprojects.realtimekotsystem.entity.Tables;
import com.myprojects.realtimekotsystem.mappers.TablesMapper;
import com.myprojects.realtimekotsystem.repository.RestaurantRepo;
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

    @Autowired
    private RestaurantRepo restaurant_repo;

    @Transactional
    public TablesDTO addTable(TablesDTO tableDTO, Long restaurantId) {
        Restaurant restaurant = restaurant_repo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant Profile Could Not Found"));

        Tables table = TablesMapper.toEntity(tableDTO);
        table.setRestaurant(restaurant);
        return TablesMapper.toDTO(tables_repo.save(table));
    }

    public List<TablesDTO> getAllTables(Long restaurantId) {
        List<Tables> tables = tables_repo.findByRestaurantId(restaurantId);
        return TablesMapper.toDTO(tables);
    }

    @Transactional
    public TablesDTO updateTable(Long id, Map<String, String> tableStatus, Long restaurantId) {

        TableStatus status = TableStatus.valueOf(tableStatus.get("status"));
        Tables table = tables_repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        if(!table.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Restaurant Profile Could Not Found");
        }

        table.setStatus(status);
        return TablesMapper.toDTO(tables_repo.save(table));
    }

    @Transactional
    public void deleteTable(int tableNumber, Long restaurantId) {
        Tables tables = tables_repo.findByTableNumber(tableNumber);
        if (tables != null) {
            if(!tables.getRestaurant().getId().equals(restaurantId)) {
                throw new RuntimeException("Restaurant Profile Could Not Found");
            }
            tables_repo.delete(tables);
        }
        else {
            throw new RuntimeException("Table not found");
        }
    }
}
