package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.importDtos.PartImportDto;
import com.petia.cardemo.domain.dtos.importDtos.PartImportRootDto;
import com.petia.cardemo.domain.entities.Part;
import com.petia.cardemo.domain.entities.Supplier;
import com.petia.cardemo.repository.PartRepository;
import com.petia.cardemo.repository.SupplierRepository;
import com.petia.cardemo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void importParts(PartImportRootDto partImportRootDto) {
        for (PartImportDto partImportDto : partImportRootDto.getPartImportDtos()) {
            if (!this.validationUtil.isValid(partImportDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            Part partEntity = this.modelMapper.map(partImportDto, Part.class);
            partEntity.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(partEntity);
        }
    }

    private Supplier getRandomSupplier() {
        Random random = new Random();
        int randomIndex = random.nextInt((int) (this.supplierRepository.count() - 1)) + 1;

        return this.supplierRepository.findAll().get(randomIndex);
    }
}
