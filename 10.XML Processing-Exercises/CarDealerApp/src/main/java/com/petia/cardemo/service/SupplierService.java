package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.importDtos.SupplierImportRootDto;
import com.petia.cardemo.domain.dtos.exportDtos.SupplierExpotRootDto;

public interface SupplierService {

    void importSuppliers(SupplierImportRootDto supplierImportRootDto);

    SupplierExpotRootDto getLocalSuppliers();
}
