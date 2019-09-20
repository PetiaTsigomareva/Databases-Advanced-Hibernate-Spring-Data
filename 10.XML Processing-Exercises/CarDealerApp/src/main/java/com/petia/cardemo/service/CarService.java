package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.CarExportRootDto;
import com.petia.cardemo.domain.dtos.importDtos.CarImportRootDto;

public interface CarService {

    void importCars(CarImportRootDto carImportRootDto);

    CarExportRootDto exportCars();

    CarExportRootDto getCarsByMake(String make_cars);
}
