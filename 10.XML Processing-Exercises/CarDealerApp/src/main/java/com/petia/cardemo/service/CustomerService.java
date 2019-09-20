package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.CustomerExportRootDto;
import com.petia.cardemo.domain.dtos.exportDtos.CustomerSalesRootDto;
import com.petia.cardemo.domain.dtos.importDtos.CustomerImportRootDto;

public interface CustomerService {

    void importCustomers(CustomerImportRootDto customerImportRootDto);

    CustomerExportRootDto exportCustomer();

    CustomerSalesRootDto getCustomerSales();
}
