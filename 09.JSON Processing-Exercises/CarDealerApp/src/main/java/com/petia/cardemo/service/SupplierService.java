package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.SupplierDto;
import com.petia.cardemo.domain.dtos.SupplierSeedDto;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);

    List<SupplierDto> getLocalSuppliers();
}
