package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.CarSaleDto;
import com.petia.cardemo.domain.dtos.exportDtos.SalesWithDiscountDto;
import com.petia.cardemo.domain.dtos.exportDtos.SalesWithDiscountRootDto;
import com.petia.cardemo.domain.entities.Car;
import com.petia.cardemo.domain.entities.Customer;
import com.petia.cardemo.domain.entities.Sale;
import com.petia.cardemo.repository.CarRepository;
import com.petia.cardemo.repository.CustomerRepository;
import com.petia.cardemo.repository.SaleRepository;
import com.petia.cardemo.util.Tools;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        for (int i = 0; i <= 20; i++) {
            Car randomCar = Tools.getRandomIndex(this.carRepository);
            Customer randomCustomer = Tools.getRandomIndex(this.customerRepository);
            double randomDiscounts = getRandomDiscount(Tools.getRandomNumber(1, 8));
            Sale entity = new Sale();
            entity.setCar(randomCar);
            entity.setCustomer(randomCustomer);
            entity.setDiscount(randomDiscounts);

            this.saleRepository.saveAndFlush(entity);
        }


    }

    @Override
    public SalesWithDiscountRootDto getSalesWithDiscount() {
        SalesWithDiscountRootDto result = new SalesWithDiscountRootDto();
        List<Sale> saleEntity = this.saleRepository.findAll();

        for (Sale sale : saleEntity) {
            Car carEntity = this.carRepository.findById(sale.getCar().getId()).orElse(null);
            Customer customerEtity = this.customerRepository.findById(sale.getCustomer().getId()).orElse(null);
            SalesWithDiscountDto salesWithDiscountDto = new SalesWithDiscountDto();
            CarSaleDto carSaleDto = this.modelMapper.map(carEntity, CarSaleDto.class);

            salesWithDiscountDto.setCar(carSaleDto);
            salesWithDiscountDto.setCustomerName(customerEtity.getName());
            salesWithDiscountDto.setPrice(carEntity.getPrice());
            salesWithDiscountDto.setDiscount(sale.getDiscount());
            BigDecimal priceWithDiscount = carEntity.getPrice().subtract(carEntity.getPrice().multiply(BigDecimal.valueOf(sale.getDiscount())));
            salesWithDiscountDto.setPriceWithDiscount(priceWithDiscount);


            result.setSalesWithDiscountDtos(salesWithDiscountDto);

        }

        return result;
    }

    private double getRandomDiscount(int count) {
        double result = 0.0;

        switch (count) {
            case 1:
                result = 0.0;
                break;

            case 2:
                result = 0.05;
                break;
            case 3:
                result = 0.10;
                break;
            case 4:
                result = 0.15;
                break;
            case 5:
                result = 0.20;
                break;
            case 6:
                result = 0.30;
                break;
            case 7:
                result = 0.40;
                break;
            case 8:
                result = 0.50;
                break;
            default:
                break;
        }
        return result;
    }
}
