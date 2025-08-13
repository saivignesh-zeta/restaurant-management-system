package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.CustomerDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.Customer;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .contact(customer.getContact())
                .build();
    }

    public static Customer toEntity(CustomerDTO customerDTO){
        return Customer.builder()
                .customerId(customerDTO.getCustomerId())
                .name(customerDTO.getName())
                .contact(customerDTO.getContact())
                .build();

    }
}
