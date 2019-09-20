package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.CarExportDto;
import com.petia.cardemo.domain.dtos.exportDtos.CarExportRootDto;
import com.petia.cardemo.domain.dtos.exportDtos.PartExportDto;
import com.petia.cardemo.domain.dtos.exportDtos.PartExportRootDto;
import com.petia.cardemo.domain.dtos.importDtos.CarImportDto;
import com.petia.cardemo.domain.dtos.importDtos.CarImportRootDto;
import com.petia.cardemo.domain.entities.Car;
import com.petia.cardemo.domain.entities.Part;
import com.petia.cardemo.repository.CarRepository;
import com.petia.cardemo.repository.PartRepository;
import com.petia.cardemo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importCars(CarImportRootDto carImportRootDto) {
        for (CarImportDto carImportDto : carImportRootDto.getCarImportDtos()) {
            if (!this.validationUtil.isValid(carImportDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            List<Part> randomParts = this.getRandomParts();
            Car carEntity = this.modelMapper.map(carImportDto, Car.class);
            carEntity.setParts(randomParts);
            carEntity.setPrice(getCarPrice(randomParts));


            this.carRepository.saveAndFlush(carEntity);
        }
    }

    @Override
    public CarExportRootDto exportCars() {
        List<Car> carEntities = this.carRepository.findAll();

        List<CarExportDto> carExportDtos = new ArrayList<>();
        for (Car carEntity : carEntities) {
            CarExportDto carExportDto = this.modelMapper.map(carEntity, CarExportDto.class);
            List<PartExportDto> partExportDtos = new ArrayList<>();

            for (Part part : carEntity.getParts()) {
                PartExportDto partExportDto = this.modelMapper.map(part, PartExportDto.class);

                partExportDtos.add(partExportDto);
            }

            PartExportRootDto partExportRootDto = new PartExportRootDto();
            partExportRootDto.setPartExportDtos(partExportDtos);
            carExportDto.setPartExportRootDto(partExportRootDto);

            carExportDtos.add(carExportDto);
        }

        CarExportRootDto carExportRootDto = new CarExportRootDto();
        carExportRootDto.setCarExportDtos(carExportDtos);

        return carExportRootDto;
    }

    @Override
    public CarExportRootDto getCarsByMake(String make_cars) {
        List<Car> carEntity = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make_cars);
        List<CarExportDto> exportCars = new ArrayList<>();
        for (Car c : carEntity) {
            CarExportDto carExportDto = this.modelMapper.map(c, CarExportDto.class);
            exportCars.add(carExportDto);
        }
        CarExportRootDto carExportRootDto = new CarExportRootDto();
        carExportRootDto.setCarExportDtos(exportCars);

        return carExportRootDto;
    }

    private List<Part> getRandomParts() {
        List<Part> parts = new ArrayList<>();
        Random rnd = new Random();

        List<Part> partEntities = this.partRepository.findAll();

        int length = rnd.nextInt(10) + 10;

        for (int i = 0; i < length; i++) {
            int partIndex = rnd.nextInt((int) (this.partRepository.count() - 1)) + 1;

            parts.add(partEntities.get(partIndex));
        }

        return parts;
    }

    private BigDecimal getCarPrice(List<Part> parts) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Part p : parts) {
           sum = sum.add(p.getPrice());
        }
        return sum;
    }
}
