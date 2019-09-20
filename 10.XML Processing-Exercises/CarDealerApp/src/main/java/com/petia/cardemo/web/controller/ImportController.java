package com.petia.cardemo.web.controller;


import com.petia.cardemo.domain.dtos.importDtos.CarImportRootDto;
import com.petia.cardemo.domain.dtos.importDtos.CustomerImportRootDto;
import com.petia.cardemo.domain.dtos.importDtos.PartImportRootDto;
import com.petia.cardemo.domain.dtos.importDtos.SupplierImportRootDto;
import com.petia.cardemo.service.*;
import com.petia.cardemo.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Controller
public class ImportController {

    private final static String SUPPLIERS_XML_FILE_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\suppliers.xml";
    private final static String PARTS_XML_FILE_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\parts.xml";
    private final static String CARS_XML_FILE_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\cars.xml";
    private final static String CUSTOMERS_XML_FILE_PATH = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\10.XML Processing-Exercises\\CarDealerApp\\src\\main\\resources\\files\\customers.xml";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final XmlParser xmlParser;
    private final SaleService saleService;

    @Autowired
    public ImportController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, XmlParser xmlParser, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.xmlParser = xmlParser;
        this.saleService = saleService;
    }

    public String importSuppliers() throws JAXBException, FileNotFoundException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, SUPPLIERS_XML_FILE_PATH);

        this.supplierService.importSuppliers(supplierImportRootDto);
        return "Imported suppliers";
    }

    public String importParts() throws JAXBException, FileNotFoundException {
        PartImportRootDto partImportRootDto = this.xmlParser.parseXml(PartImportRootDto.class, PARTS_XML_FILE_PATH);

        this.partService.importParts(partImportRootDto);
        return "Imported parts";
    }

    public String importCars() throws JAXBException, FileNotFoundException {
        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, CARS_XML_FILE_PATH);

        this.carService.importCars(carImportRootDto);
        return "Imported cars";
    }

    public String importCustomers() throws JAXBException, FileNotFoundException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser.parseXml(CustomerImportRootDto.class, CUSTOMERS_XML_FILE_PATH);

        this.customerService.importCustomers(customerImportRootDto);
        return "Imported Customers";
    }

    public String importSales() {
        this.saleService.seedSales();
        return "Import Sales";
    }

}
