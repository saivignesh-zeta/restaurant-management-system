package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.RestaurantTableDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;

public class RestaurantTableMapper {

    public static RestaurantTableDTO toDTO(RestaurantTable table) {
        return RestaurantTableDTO.builder()
                .tableId(table.getTableId())
                .capacity(table.getCapacity())
                .status(table.getStatus())
                .build();
    }

    public static RestaurantTable toEntity(RestaurantTableDTO tableDTO) {
        if (tableDTO == null) return null;

        RestaurantTable.RestaurantTableBuilder builder = RestaurantTable.builder();

        if (tableDTO.getTableId() != null) {
            builder.tableId(tableDTO.getTableId());
        }

        return builder
                .capacity(tableDTO.getCapacity())
                .status(tableDTO.getStatus())
                .build();
    }
}
