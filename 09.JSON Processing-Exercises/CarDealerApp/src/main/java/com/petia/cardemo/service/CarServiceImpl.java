package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.CarDto;
import com.petia.cardemo.domain.dtos.CarSeedDto;
import com.petia.cardemo.domain.dtos.OrderCustomerDto;
import com.petia.cardemo.domain.entities.Car;
import com.petia.cardemo.domain.entities.Customer;
import com.petia.cardemo.repository.CarRepository;
import com.petia.cardemo.repository.PartRepository;
import com.petia.cardemo.util.Tools;
import com.petia.cardemo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final String MAKE = "Toyota";
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PartRepository partRepository;

    public CarServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CarRepository carRepository, PartRepository partRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }


    @Override
    public void seedCars(CarSeedDto[] carSeedDtos) {
        for (CarSeedDto carSeedDto : carSeedDtos) {
            if (!this.validatorUtil.isValid(carSeedDtos)) {
                this.validatorUtil.violations(carSeedDtos).forEach(violation -> {
                    System.out.println(violation.getMessage());
                });

                continue;
            }

            Car entity = this.modelMapper.map(carSeedDto, Car.class);
            int count =Tools.getRandomNumber(10,20);
            entity.setParts(Tools.getRandomList(this.partRepository,count));

            this.carRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<CarDto> getCarsByMake() {
        List<CarDto> result = new ArrayList<>();
        List<Car> entity = this.carRepository.findAllByMake(MAKE);
        for (Car c : entity) {
            CarDto carDto = this.modelMapper.map(c, CarDto.class);



            result.add(carDto);
        }
       result.sort(Comparator.comparing(CarDto::getModel).thenComparing((CarDto c1,CarDto c2)->(int)(c2.getTravelledDistance()-c1.getTravelledDistance())));

        return result;

    }
}
