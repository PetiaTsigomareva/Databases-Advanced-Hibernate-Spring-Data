package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.importDtos.SupplierImportDto;
import com.petia.cardemo.domain.dtos.importDtos.SupplierImportRootDto;
import com.petia.cardemo.domain.dtos.exportDtos.SupplierExportDto;
import com.petia.cardemo.domain.dtos.exportDtos.SupplierExpotRootDto;
import com.petia.cardemo.domain.entities.Supplier;
import com.petia.cardemo.repository.SupplierRepository;
import com.petia.cardemo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importSuppliers(SupplierImportRootDto supplierImportRootDto) {
        for (SupplierImportDto supplierImportDto : supplierImportRootDto.getSupplierImportDtos()) {
            if (!this.validationUtil.isValid(supplierImportDto)) {
                System.out.println("Something went wrong");

                continue;
            }

            Supplier supplierEntity = this.modelMapper.map(supplierImportDto, Supplier.class);

            this.supplierRepository.saveAndFlush(supplierEntity);
        }
    }

    @Override
    public SupplierExpotRootDto getLocalSuppliers() {

        SupplierExpotRootDto result = new SupplierExpotRootDto();
        List<Supplier> supplierEntity = this.supplierRepository.findAllByImporterIsFalse();
        List<SupplierExportDto> supplierExportDtos = result.getSupplierExportDtoList();

        for (Supplier s : supplierEntity) {
            SupplierExportDto supplierExportDto = this.modelMapper.map(s, SupplierExportDto.class);
            supplierExportDto.setPartsCount(s.getParts().size());

            supplierExportDtos.add(supplierExportDto);


        }
        supplierExportDtos.sort((SupplierExportDto s1, SupplierExportDto s2) -> s2.getPartsCount() - s1.getPartsCount());

        return result;
    }
}
