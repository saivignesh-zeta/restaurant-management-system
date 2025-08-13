package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.RestaurantTableDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import com.zeta.miniproject2.Restaurant.Management.System.Util.RestaurantTableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
@Slf4j
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    @PostMapping
    public ResponseEntity<RestaurantTableDTO> createTable(@RequestBody RestaurantTable table) {
        log.info("API Request - Create table: {}", table);
        return new ResponseEntity<>(
                RestaurantTableMapper.toDTO(tableService.createTable(table)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTableDTO>> getAllTables() {
        log.info("API Request - Get all tables");
        return ResponseEntity.ok(
                tableService.getAllTables()
                        .stream()
                        .map(RestaurantTableMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableDTO> getTableById(@PathVariable Integer id) {
        log.info("API Request - Get table ID: {}", id);
        return ResponseEntity.ok(
                RestaurantTableMapper.toDTO(tableService.getTableById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableDTO> updateTable(@PathVariable Integer id, @RequestBody RestaurantTable table) {
        log.info("API Request - Update table ID: {}", id);
        return ResponseEntity.ok(
                RestaurantTableMapper.toDTO(tableService.updateTable(id, table))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantTableDTO> patchTable(@PathVariable Integer id, @RequestBody RestaurantTableDTO tableDTO) {
        log.info("API Request - Patch table ID: {}", id);
        RestaurantTable existing = tableService.getTableById(id);
        RestaurantTable table = RestaurantTableMapper.toEntity(tableDTO);
        existing = EntityUtil.copyNonNullProperties(table, existing);
        return ResponseEntity.ok(
                RestaurantTableMapper.toDTO(tableService.patchTable(id, existing))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id) {
        log.info("API Request - Delete table ID: {}", id);
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
