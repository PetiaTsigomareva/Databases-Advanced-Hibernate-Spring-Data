package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.CustomerSeedDto;
import com.petia.cardemo.domain.dtos.OrderCustomerDto;

import java.util.List;

public interface CustomerService {

    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    List<OrderCustomerDto> getOrderCustomers();
}
