package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.CustomerDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Service.CustomerService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.CustomerMapper;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        log.info("API Request - Create customer: {}", customer);
        return new ResponseEntity<>(
                CustomerMapper.toDTO(customerService.createCustomer(customer)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        log.info("API Request - Get all customers");
        return ResponseEntity.ok(
                customerService.getAllCustomers()
                        .stream()
                        .map(CustomerMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        log.info("API Request - Get customer ID: {}", id);
        Customer customer = customerService.getCustomerById(id);
        System.out.println(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        log.info("API Request - Update customer ID: {}", id);
        return ResponseEntity.ok(
                CustomerMapper.toDTO(customerService.updateCustomer(id, customer))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        log.info("API Request - Patch customer ID: {}", id);
        Customer existing = customerService.getCustomerById(id);

        Customer customer = CustomerMapper.toEntity(customerDTO);
        existing = EntityUtil.copyNonNullProperties(customer, existing);
        return ResponseEntity.ok(
                CustomerMapper.toDTO(customerService.patchCustomer(id, existing))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        log.info("API Request - Delete customer ID: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
