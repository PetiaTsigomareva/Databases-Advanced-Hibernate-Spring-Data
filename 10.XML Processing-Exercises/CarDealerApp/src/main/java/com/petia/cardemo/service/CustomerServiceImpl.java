package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.exportDtos.CustomerExportDto;
import com.petia.cardemo.domain.dtos.exportDtos.CustomerExportRootDto;
import com.petia.cardemo.domain.dtos.exportDtos.CustomerSalesDto;
import com.petia.cardemo.domain.dtos.exportDtos.CustomerSalesRootDto;
import com.petia.cardemo.domain.dtos.importDtos.CustomerImportDto;
import com.petia.cardemo.domain.dtos.importDtos.CustomerImportRootDto;
import com.petia.cardemo.domain.entities.Customer;
import com.petia.cardemo.domain.entities.Sale;
import com.petia.cardemo.repository.CustomerRepository;
import com.petia.cardemo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importCustomers(CustomerImportRootDto customerImportRootDto) {
        for (CustomerImportDto customerImportDto : customerImportRootDto.getCustomerImportDtos()) {
            if (!this.validationUtil.isValid(customerImportDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            Customer customer = this.modelMapper.map(customerImportDto, Customer.class);
            customer.setBirthDate(LocalDate.parse(customerImportDto.getBirthDate()));

            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public CustomerExportRootDto exportCustomer() {
        CustomerExportRootDto result = new CustomerExportRootDto();
        List<Customer> customerEntities = this.customerRepository.findAll();
        List<CustomerExportDto> customerExportDtos = new ArrayList<>();

        for (Customer customerEntity : customerEntities) {
            CustomerExportDto customerExportDto = this.modelMapper.map(customerEntity, CustomerExportDto.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = customerEntity.getBirthDate().format(formatter);
            customerExportDto.setBirthDate(formatDateTime);


            customerExportDtos.add(customerExportDto);
        }

        customerExportDtos.sort((CustomerExportDto o1, CustomerExportDto o2) -> o1.getBirthDate().compareTo(o2.getBirthDate()));
        result.setCustomerExportDtos(customerExportDtos);
        return result;
    }

    @Override
    public CustomerSalesRootDto getCustomerSales() {
        CustomerSalesRootDto result = new CustomerSalesRootDto();
        List<Customer> customerEntity = this.customerRepository.findAll();

        for (Customer customer : customerEntity) {
            if (customer.getSales().size() != 0) {
                CustomerSalesDto customerSalesDto = new CustomerSalesDto();
                customerSalesDto.setName(customer.getName());
                customerSalesDto.setBoughtCars(customer.getSales().size());
                for (Sale s : customer.getSales()) {

                    customerSalesDto.setSpentMoney(s.getCar().getPrice());
                }


                result.setCustomerSalesDtoList(customerSalesDto);
            }

        }
        result.getCustomerSalesDtoList().sort(Comparator.comparing(CustomerSalesDto::getSpentMoney).thenComparing(CustomerSalesDto::getBoughtCars).reversed());

        return result;
    }
}
