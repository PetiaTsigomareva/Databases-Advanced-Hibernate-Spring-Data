package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.CarDto;
import com.petia.cardemo.domain.dtos.CarSeedDto;

import java.util.List;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    List<CarDto> getCarsByMake();
}
