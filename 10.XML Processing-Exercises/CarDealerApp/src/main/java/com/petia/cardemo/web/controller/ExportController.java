package com.petia.cardemo.web.controller;


import com.petia.cardemo.domain.dtos.exportDtos.*;
import com.petia.cardemo.service.CarService;
import com.petia.cardemo.service.CustomerService;
import com.petia.cardemo.service.SaleService;
import com.petia.cardemo.service.SupplierService;
import com.petia.cardemo.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;

@Controller
public class ExportController {

    private static final String CARS_PARTS_FILE_PAHT = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\cars-and-parts.xml";
    private static final String CUSTOMER_FILE_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\ordered-customers.xml";
    private static final String RESULT = "RESULT OF THE CALLED METHOD IS IN ...resources\\files\\output ";
    private final String MAKE_CARS = "Toyota";
    private final String TOYOTO_CARS_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\toyota_cars.xml";
    private final String LOCAL_SUPPLIERS_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\local_suppliers.xml";
    private final String CUSTOMER_SALES_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\customer-sales.xml";
    private final String SALES_WITH_DISCOUNT = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\output\\sales-with-discount.xml";
    private final CarService carService;
    private final XmlParser xmlParser;
    private final CustomerService customerService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    @Autowired
    public ExportController(CarService carService, XmlParser xmlParser, CustomerService customerService, SupplierService supplierService, SaleService saleService) {
        this.carService = carService;
        this.xmlParser = xmlParser;
        this.customerService = customerService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    public String exportCars() throws JAXBException {
        CarExportRootDto carExportRootDto = this.carService.exportCars();

        this.xmlParser.exportToXml(carExportRootDto, CarExportRootDto.class, CARS_PARTS_FILE_PAHT);
        return RESULT;
    }

    public String getOrderedCustomerts() throws JAXBException {
        CustomerExportRootDto customerExportRootDto = this.customerService.exportCustomer();
        this.xmlParser.exportToXml(customerExportRootDto, CustomerExportRootDto.class, CUSTOMER_FILE_PATH);
        return RESULT;
    }

    public String getToyotaCars() throws JAXBException {
        CarExportRootDto carExportRootDto = this.carService.getCarsByMake(MAKE_CARS);
        this.xmlParser.exportToXml(carExportRootDto, CarExportRootDto.class, TOYOTO_CARS_PATH);
        return RESULT;
    }

    public String getLocalSuppliers() throws JAXBException {
        SupplierExpotRootDto supplierExpotRootDto = this.supplierService.getLocalSuppliers();
        this.xmlParser.exportToXml(supplierExpotRootDto, SupplierExpotRootDto.class, LOCAL_SUPPLIERS_PATH);
        return RESULT;
    }

    public String totalSalesByCustomers() throws JAXBException {
        CustomerSalesRootDto customerSalesRootDto = this.customerService.getCustomerSales();
        this.xmlParser.exportToXml(customerSalesRootDto, CustomerSalesRootDto.class, CUSTOMER_SALES_PATH);

        return RESULT;
    }

    public String getSalesWithAppliedDiscount() throws JAXBException {
        SalesWithDiscountRootDto salesWithDiscountRootDto = this.saleService.getSalesWithDiscount();
        this.xmlParser.exportToXml(salesWithDiscountRootDto, SalesWithDiscountRootDto.class, SALES_WITH_DISCOUNT);
        return RESULT;
    }
}
