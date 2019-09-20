package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.SalesWithDiscountRootDto;

public interface SaleService {
    void seedSales();


    SalesWithDiscountRootDto getSalesWithDiscount();

}
