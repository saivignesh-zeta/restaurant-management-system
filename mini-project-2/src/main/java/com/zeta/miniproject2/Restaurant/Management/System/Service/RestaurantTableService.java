package com.zeta.miniproject2.Restaurant.Management.System.Service;


import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;

import java.util.List;

public interface RestaurantTableService {

    RestaurantTable createTable(RestaurantTable table);

    RestaurantTable getTableById(Integer tableId);

    List<RestaurantTable> getAllTables();

    RestaurantTable updateTable(Integer tableId, RestaurantTable updatedTable);

    RestaurantTable patchTable(Integer tableId, RestaurantTable updatedTable);

    boolean deleteTable(Integer tableId);

    boolean existsById(Integer tableId);
}
