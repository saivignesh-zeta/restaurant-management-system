package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .customerId(1)
                .name("Alice")
                .contact("1234567890")
                .build();
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        assertEquals("Alice", result.getName());
        assertEquals("1234567890", result.getContact());

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomerById_found() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void testGetCustomerById_notFound_throws() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(99));
        verify(customerRepository, times(1)).findById(99);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer() {
        Customer updated = Customer.builder()
                .name("Bob")
                .contact("9876543210")
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.updateCustomer(1, updated);

        assertNotNull(result);
        assertEquals("Bob", result.getName());
        assertEquals("9876543210", result.getContact());
        verify(customerRepository).save(customer);
    }

    @Test
    void testPatchCustomer() {
        Customer patch = Customer.builder()
                .contact("5555555555")
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.patchCustomer(1, patch);

        assertEquals("Alice", result.getName()); // unchanged
        assertEquals("5555555555", result.getContact()); // updated
        verify(customerRepository).save(customer);
    }

    @Test
    void testDeleteCustomer_exists() {
        when(customerRepository.existsById(1)).thenReturn(true);

        boolean result = customerService.deleteCustomer(1);

        assertTrue(result);
        verify(customerRepository).deleteById(1);
    }

    @Test
    void testDeleteCustomer_notExists_throws() {
        when(customerRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(99));
        verify(customerRepository, never()).deleteById(anyInt());
    }

    @Test
    void testExistsById_true() {
        when(customerRepository.existsById(1)).thenReturn(true);

        boolean exists = customerService.existsById(1);

        assertTrue(exists);
        verify(customerRepository).existsById(1);
    }

    @Test
    void testExistsById_false() {
        when(customerRepository.existsById(99)).thenReturn(false);

        boolean exists = customerService.existsById(99);

        assertFalse(exists);
        verify(customerRepository).existsById(99);
    }
}
