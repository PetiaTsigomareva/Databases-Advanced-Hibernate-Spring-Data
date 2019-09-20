package com.petia.cardemo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class BaseController implements CommandLineRunner {

    private final ImportController importController;
    private final ExportController exportController;
    private final Scanner scanner;

    @Autowired
    protected BaseController(ImportController importController, ExportController exportController, Scanner scanner) {
        this.importController = importController;
        this.exportController = exportController;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.startAppInfo());

        int input;
        do {
            input = scanner.nextInt();
            callMethods(input);
        } while (input != 0);


    }

    private void callMethods(int number) throws Exception {
        switch (number) {
            case 1:
                System.out.println(this.importController.importSuppliers());
                break;
            case 2:
                System.out.println(this.importController.importParts());
                break;
            case 3:
                System.out.println(this.importController.importCars());
                break;
            case 4:
                System.out.println(this.importController.importCustomers());
                break;
            case 5:
                System.out.println(this.importController.importSales());
                break;
            case 6:
                System.out.println(this.exportController.getOrderedCustomerts());
                break;
            case 7:
                System.out.println(this.exportController.getToyotaCars());
                break;
            case 8:
                System.out.println(this.exportController.getLocalSuppliers());
                break;
            case 9:
                System.out.println(this.exportController.exportCars());
                break;
            case 10:
                System.out.println(this.exportController.totalSalesByCustomers());
                break;
            case 11:
                System.out.println(this.exportController.getSalesWithAppliedDiscount());
                break;
            case 0:
                System.out.println("Product shop App was terminated!");
                break;
        }
    }


    private String startAppInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enter number:\n").append("It is mandatory numbers 1,2,3,4 and 5 to be executed at least once!\n").append("1.Import Suppliers.\n").append("2.Import Parts\n").append("3.Import Cars\n").append("4.Import Customers\n").append("5.Import Sales.\n").append("6.Ordered Customers.\n").append("7.Get Toyota Cars.\n").append("8.Get Local Suppliers\n").append("9.Export Cars\n").append("10.Get total Customer Sales\n").append("11.Get sales with applied discount\n").append("0. Terminate App!\n").append("Check query results into resources/files/output... Path.If exist that path... ");

        return sb.toString();
    }
}
