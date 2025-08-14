package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.RestaurantTableRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    @Override
    public RestaurantTable createTable(RestaurantTable table) {
        log.info("Creating new table: {}", table);
        RestaurantTable savedTable = restaurantTableRepository.save(table);
        log.info("Table created with ID: {}", savedTable.getTableId());
        return savedTable;
    }

    @Override
    public RestaurantTable getTableById(Integer tableId) {
        log.info("Fetching table with ID: {}", tableId);
        return restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> {
                    log.warn("Table with ID {} not found", tableId);
                    return new ResourceNotFoundException("Table not found with ID: " + tableId);
                });
    }

    @Override
    public List<RestaurantTable> getAllTables() {
        log.info("Fetching all tables");
        List<RestaurantTable> tables = restaurantTableRepository.findAll();
        if (tables.isEmpty()) {
            log.warn("No tables found");
        } else {
            log.info("Total tables found: {}", tables.size());
        }
        return tables;
    }

    @Override
    public RestaurantTable updateTable(Integer tableId, RestaurantTable updatedTable){
        log.info("Fully updating table with ID: {}", tableId);
        RestaurantTable existingTable = getTableById(tableId);

        BeanUtils.copyProperties(updatedTable, existingTable, "tableId");

        RestaurantTable savedTable = restaurantTableRepository.save(existingTable);
        log.info("Table with ID {} successfully fully updated", tableId);

        return savedTable;
    }

    @Override
    public RestaurantTable patchTable(Integer tableId, RestaurantTable updatedTable) {
        log.info("Updating table with ID: {}", tableId);
        RestaurantTable existingTable = getTableById(tableId);

        existingTable = EntityUtil.copyNonNullProperties(updatedTable, existingTable);

        RestaurantTable savedTable = restaurantTableRepository.save(existingTable);
        log.info("Table with ID {} successfully updated", tableId);
        return savedTable;
    }

    @Override
    public boolean deleteTable(Integer tableId) {
        log.info("Attempting to delete table with ID: {}", tableId);
        if (!restaurantTableRepository.existsById(tableId)) {
            log.warn("Table with ID {} does not exist", tableId);
            throw new ResourceNotFoundException("Table not found with ID: " + tableId);
        }
        restaurantTableRepository.deleteById(tableId);
        log.info("Table with ID {} successfully deleted", tableId);
        return true;
    }

    @Override
    public boolean existsById(Integer tableId) {
        log.info("Checking if table exists with ID: {}", tableId);
        return restaurantTableRepository.existsById(tableId);
    }
}
