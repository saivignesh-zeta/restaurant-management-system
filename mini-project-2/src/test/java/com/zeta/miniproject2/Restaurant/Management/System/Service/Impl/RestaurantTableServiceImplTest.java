package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.RestaurantTable;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.TableStatus;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.RestaurantTableRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RestaurantTableServiceImplTest {

    @Mock
    private RestaurantTableRepository restaurantTableRepository;

    @InjectMocks
    private RestaurantTableServiceImpl restaurantTableService;

    private RestaurantTable sampleTable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTable = RestaurantTable.builder()
                .tableId(1)
                .capacity(4)
                .status(TableStatus.AVAILABLE)
                .build();
    }

    @Test
    void testCreateTable_Success() {
        when(restaurantTableRepository.save(any(RestaurantTable.class))).thenReturn(sampleTable);

        RestaurantTable result = restaurantTableService.createTable(sampleTable);

        assertNotNull(result);
        assertEquals(1, result.getTableId());
        verify(restaurantTableRepository, times(1)).save(sampleTable);
    }

    @Test
    void testGetTableById_Success() {
        when(restaurantTableRepository.findById(1)).thenReturn(Optional.of(sampleTable));

        RestaurantTable result = restaurantTableService.getTableById(1);

        assertNotNull(result);
        assertEquals(1, result.getTableId());
        verify(restaurantTableRepository, times(1)).findById(1);
    }

    @Test
    void testGetTableById_NotFound() {
        when(restaurantTableRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantTableService.getTableById(1));
        verify(restaurantTableRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllTables_NonEmpty() {
        when(restaurantTableRepository.findAll()).thenReturn(Arrays.asList(sampleTable));

        List<RestaurantTable> result = restaurantTableService.getAllTables();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(restaurantTableRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTables_Empty() {
        when(restaurantTableRepository.findAll()).thenReturn(Collections.emptyList());

        List<RestaurantTable> result = restaurantTableService.getAllTables();

        assertTrue(result.isEmpty());
        verify(restaurantTableRepository, times(1)).findAll();
    }

    @Test
    void testUpdateTable_Success() {
        RestaurantTable updatedTable = RestaurantTable.builder()
                .tableId(1)
                .capacity(6)
                .status(TableStatus.OCCUPIED)
                .build();

        when(restaurantTableRepository.findById(1)).thenReturn(Optional.of(sampleTable));
        when(restaurantTableRepository.save(any(RestaurantTable.class))).thenReturn(updatedTable);

        RestaurantTable result = restaurantTableService.updateTable(1, updatedTable);

        assertEquals(6, result.getCapacity());
        assertEquals(TableStatus.OCCUPIED, result.getStatus());
        verify(restaurantTableRepository, times(1)).save(any(RestaurantTable.class));
    }

    @Test
    void testPatchTable_Success() {
        RestaurantTable patchData = RestaurantTable.builder()
                .capacity(8) // Only update capacity
                .build();

        when(restaurantTableRepository.findById(1)).thenReturn(Optional.of(sampleTable));
        when(restaurantTableRepository.save(any(RestaurantTable.class))).thenReturn(sampleTable);

        RestaurantTable result = restaurantTableService.patchTable(1, patchData);

        assertNotNull(result);
        verify(restaurantTableRepository, times(1)).save(any(RestaurantTable.class));
    }

    @Test
    void testDeleteTable_Success() {
        when(restaurantTableRepository.existsById(1)).thenReturn(true);
        doNothing().when(restaurantTableRepository).deleteById(1);

        boolean result = restaurantTableService.deleteTable(1);

        assertTrue(result);
        verify(restaurantTableRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTable_NotFound() {
        when(restaurantTableRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> restaurantTableService.deleteTable(1));
        verify(restaurantTableRepository, never()).deleteById(anyInt());
    }

    @Test
    void testExistsById_True() {
        when(restaurantTableRepository.existsById(1)).thenReturn(true);

        assertTrue(restaurantTableService.existsById(1));
    }

    @Test
    void testExistsById_False() {
        when(restaurantTableRepository.existsById(1)).thenReturn(false);

        assertFalse(restaurantTableService.existsById(1));
    }
}
