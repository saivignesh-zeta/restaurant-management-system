package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.CustomerDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Service.CustomerService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .customerId(1)
                .name("Alice")
                .contact("alice@example.com")
                .build();
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = controller.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());
        assertEquals(customer.getName(), response.getBody().getName());
        assertEquals(customer.getContact(), response.getBody().getContact());
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        ResponseEntity<List<CustomerDTO>> response = controller.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(customer.getCustomerId(), response.getBody().get(0).getCustomerId());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(1)).thenReturn(customer);

        ResponseEntity<?> response = controller.getCustomerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Customer);
        assertEquals(customer.getCustomerId(), ((Customer) response.getBody()).getCustomerId());
        verify(customerService, times(1)).getCustomerById(1);
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(1, customer)).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = controller.updateCustomer(1, customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());
        verify(customerService, times(1)).updateCustomer(1, customer);
    }

    @Test
    void testPatchCustomer() {
        CustomerDTO customerDTO = CustomerMapper.toDTO(customer);
        when(customerService.getCustomerById(1)).thenReturn(customer);
        when(customerService.patchCustomer(1, customer)).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = controller.patchCustomer(1, customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer.getCustomerId(), response.getBody().getCustomerId());
        verify(customerService, times(1)).getCustomerById(1);
        verify(customerService, times(1)).patchCustomer(1, customer);
    }

    @Test
    void testDeleteCustomer() {
        when(customerService.deleteCustomer(1)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteCustomer(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(1);
    }
}
