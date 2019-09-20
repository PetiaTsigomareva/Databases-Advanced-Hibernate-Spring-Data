package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.OrderCustomerDto;
import com.petia.cardemo.domain.dtos.SupplierDto;
import com.petia.cardemo.domain.dtos.SupplierSeedDto;
import com.petia.cardemo.domain.entities.Customer;
import com.petia.cardemo.domain.entities.Supplier;
import com.petia.cardemo.repository.SupplierRepository;
import com.petia.cardemo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!this.validatorUtil.isValid(supplierSeedDto)) {
                this.validatorUtil.violations(supplierSeedDto).forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            Supplier entity = this.modelMapper.map(supplierSeedDto, Supplier.class);

            this.supplierRepository.saveAndFlush(entity);

        }

    }

    @Override
    public List<SupplierDto> getLocalSuppliers() {
        List<SupplierDto> result = new ArrayList<>();
        List<Supplier> entity = this.supplierRepository.findAllByImporterIsFalse();
        for (Supplier s : entity) {
            SupplierDto localSupplier = this.modelMapper.map(s, SupplierDto.class);
            localSupplier.setPartsCount(s.getParts().size());


            result.add(localSupplier);
        }

        return result;
    }
}
