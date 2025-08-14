package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.CustomerRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.CustomerService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Creating new customer: {}", customer);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created with ID: {}", savedCustomer.getCustomerId());
        return savedCustomer;
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        log.info("Fetching customer with ID: {}", customerId);

        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("Customer with ID {} not found", customerId);
                    return new ResourceNotFoundException("Customer not found with ID: " + customerId);
                });
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("Fetching all customers");
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())log.warn("No customers found");
        else log.info("Total customers found: {}", customers.size());
        return customers;
    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer updatedCustomer){
        log.info("Fully updating customer with ID: {}", customerId);

        Customer existingCustomer = getCustomerById(customerId);

        BeanUtils.copyProperties(updatedCustomer, existingCustomer, "customerId");

        Customer savedCustomer = customerRepository.save(existingCustomer);

        log.info("Customer with ID {} successfully fully updated", customerId);

        return savedCustomer;
    }

    @Override
    public Customer patchCustomer(Integer customerId, Customer updatedCustomer) {
        log.info("Updating customer with ID: {}", customerId);
        Customer existingCustomer = getCustomerById(customerId);

        existingCustomer = EntityUtil.copyNonNullProperties(updatedCustomer, existingCustomer);

        Customer savedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer with ID {} successfully updated", customerId);
        return savedCustomer;
    }

    @Override
    public boolean deleteCustomer(Integer customerId) {
        log.info("Attempting to delete customer with ID: {}", customerId);
        if (!customerRepository.existsById(customerId)) {
            log.warn("Customer with ID {} does not exist", customerId);
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        customerRepository.deleteById(customerId);
        log.info("Customer with ID {} successfully deleted", customerId);
        return true;
    }

    @Override
    public boolean existsById(Integer id) {
        log.info("Checking existence of customer with id {}", id);
        boolean exists = customerRepository.existsById(id);
        if (exists) {
            log.info("Customer with id {} exists", id);
        } else {
            log.warn("Customer with id {} does not exist", id);
        }
        return exists;
    }
}
