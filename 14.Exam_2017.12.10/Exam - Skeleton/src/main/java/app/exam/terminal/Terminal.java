package app.exam.terminal;

import app.exam.config.Config;
import app.exam.controller.CategoryController;
import app.exam.controller.EmployeesController;
import app.exam.controller.ItemsController;
import app.exam.controller.OrdersController;
import app.exam.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.Scanner;

@Component
public class Terminal implements CommandLineRunner {
    private final CategoryController categoryController;
    private final EmployeesController employeesController;
    private final ItemsController itemsController;
    private final OrdersController ordersController;
    private final Scanner scanner;
    private final FileUtil fileUtil;

    @Autowired
    public Terminal(CategoryController categoryController, EmployeesController employeesController, ItemsController itemsController, OrdersController ordersController, Scanner scanner, FileUtil fileUtil) {
        this.categoryController = categoryController;
        this.employeesController = employeesController;
        this.itemsController = itemsController;
        this.ordersController = ordersController;
        this.scanner = scanner;
        this.fileUtil = fileUtil;
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
                System.out.println(this.employeesController.importDataFromJSON(this.getFileContent(Config.EMPLOYEES_IMPORT_JSON)));
                break;
            case 2:
                System.out.println(this.itemsController.importDataFromJSON(this.getFileContent(Config.ITEMS_IMPORT_JSON)));
                break;
            case 3:
                System.out.println(this.ordersController.importDataFromXML(Config.ORDERS_IMPORT_XML));
                break;
            case 4:
                //TODO
                break;
            case 5:
                //TODO
                break;

            case 0:
                System.out.println("Product shop App was terminated!");
                break;
        }
    }


    private String startAppInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enter number:\n").append("It is mandatory numbers 1,2,3,4 to be executed at least once!\n").append("1.Import Employees to DB\n").append("2.Import Items to DB\n").append("3. Import Orders to DB.\n").append("//TODO\n").append("//TODO\n").append("0. Terminate App!\n").append("Check query results into resources/files/output... Path.If exist that path... ");

        return sb.toString();
    }

    private String getFileContent(String path) throws IOException {
        //1.Read Json file
        String result = this.fileUtil.readFile(path);
        return result;
    }
}
