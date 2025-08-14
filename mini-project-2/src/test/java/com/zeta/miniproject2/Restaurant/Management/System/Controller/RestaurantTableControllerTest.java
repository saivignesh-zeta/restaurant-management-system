package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.RestaurantTableDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Service.RestaurantTableService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.RestaurantTableMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RestaurantTableControllerTest {

    @Mock
    private RestaurantTableService tableService;

    @InjectMocks
    private RestaurantTableController controller;

    private RestaurantTable table;
    private RestaurantTableDTO tableDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        table = new RestaurantTable();
        table.setTableId(1);
        table.setCapacity(4);

        tableDTO = RestaurantTableMapper.toDTO(table);
    }

    @Test
    void testCreateTable() {
        when(tableService.createTable(any(RestaurantTable.class))).thenReturn(table);

        ResponseEntity<RestaurantTableDTO> response = controller.createTable(table);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        RestaurantTableDTO actual = response.getBody();
        assertEquals(tableDTO.getTableId(), actual.getTableId());
        assertEquals(tableDTO.getCapacity(), actual.getCapacity());
        assertEquals(tableDTO.getStatus(), actual.getStatus());

        verify(tableService, times(1)).createTable(table);
    }


    @Test
    void testGetAllTables() {
        when(tableService.getAllTables()).thenReturn(Arrays.asList(table));

        ResponseEntity<List<RestaurantTableDTO>> response = controller.getAllTables();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        RestaurantTableDTO actual = response.getBody().get(0);
        assertEquals(tableDTO.getTableId(), actual.getTableId());
        assertEquals(tableDTO.getCapacity(), actual.getCapacity());
        assertEquals(tableDTO.getStatus(), actual.getStatus());

        verify(tableService, times(1)).getAllTables();
    }


    @Test
    void testGetTableById() {
        when(tableService.getTableById(1)).thenReturn(table);

        ResponseEntity<RestaurantTableDTO> response = controller.getTableById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestaurantTableDTO actual = response.getBody();
        assertNotNull(actual);

        assertEquals(tableDTO.getTableId(), actual.getTableId());
        assertEquals(tableDTO.getCapacity(), actual.getCapacity());
        assertEquals(tableDTO.getStatus(), actual.getStatus());

        verify(tableService, times(1)).getTableById(1);
    }

    @Test
    void testUpdateTable() {
        when(tableService.updateTable(eq(1), any(RestaurantTable.class))).thenReturn(table);

        ResponseEntity<RestaurantTableDTO> response = controller.updateTable(1, table);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestaurantTableDTO actual = response.getBody();
        assertNotNull(actual);
        assertEquals(tableDTO.getTableId(), actual.getTableId());
        assertEquals(tableDTO.getCapacity(), actual.getCapacity());
        assertEquals(tableDTO.getStatus(), actual.getStatus());

        verify(tableService, times(1)).updateTable(eq(1), any(RestaurantTable.class));
    }


    @Test
    void testPatchTable() {
        when(tableService.patchTable(eq(1), any(RestaurantTable.class))).thenReturn(table);

        ResponseEntity<RestaurantTableDTO> response = controller.patchTable(1, tableDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        RestaurantTableDTO actual = response.getBody();
        assertNotNull(actual);
        assertEquals(tableDTO.getTableId(), actual.getTableId());
        assertEquals(tableDTO.getCapacity(), actual.getCapacity());
        assertEquals(tableDTO.getStatus(), actual.getStatus());

        verify(tableService, times(1)).patchTable(eq(1), any(RestaurantTable.class));
    }


    @Test
    void testDeleteTable() {
        when(tableService.deleteTable(1)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteTable(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tableService, times(1)).deleteTable(1);
    }

}
