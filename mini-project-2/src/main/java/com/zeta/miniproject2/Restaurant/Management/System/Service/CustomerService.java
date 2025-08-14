package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomerById(Integer id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Integer id, Customer customer);

    Customer patchCustomer(Integer id, Customer customer);

    boolean deleteCustomer(Integer id);

    boolean existsById(Integer id);
}
