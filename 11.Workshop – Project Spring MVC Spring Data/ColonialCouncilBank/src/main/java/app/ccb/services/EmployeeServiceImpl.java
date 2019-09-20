package app.ccb.services;

import app.ccb.domain.dtos.EmployeeImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final static String EMPLOYEE_JSON_PATH_FILE = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\11.Workshop – Project Spring MVC Spring Data\\WorkShop_2018-11-26\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\employees.json";
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, BranchRepository branchRepository) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean employeesAreImported() {

        return this.employeeRepository.count() != 0;

    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(EMPLOYEE_JSON_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();
        EmployeeImportDto[] employeeImportDtos = this.gson.fromJson(employees, EmployeeImportDto[].class);
        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {

            if (!this.validationUtil.isValid(employeeImportDto)) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            Branch branchEntity = this.branchRepository.findByName(employeeImportDto.getBranchName()).orElse(null);
            Employee employeeEntity = this.modelMapper.map(employeeImportDto, Employee.class);
            String firstName = employeeImportDto.getFullName().split("\\s+")[0];
            String lastName = employeeImportDto.getFullName().split("\\s+")[1];
            employeeEntity.setFirstName(firstName);
            employeeEntity.setLastName(lastName);
            employeeEntity.setStartedOn(LocalDate.parse(employeeImportDto.getStartedOn()));
            employeeEntity.setBranch(branchEntity);


            this.employeeRepository.saveAndFlush(employeeEntity);
            importResult.append(String.format("Successfully imported Employee - %s %s", employeeEntity.getFirstName(), employeeEntity.getLastName())).append(System.lineSeparator());
        }


        return importResult.toString().trim();
    }

    @Override
    public String exportTopEmployees() {
        String fullName;
        StringBuilder exportResult = new StringBuilder();
        List<Employee> topEmployees = this.employeeRepository.findTopEmployees();
        for (Employee employee : topEmployees) {

            fullName = employee.getFirstName() + " " + employee.getLastName();
            exportResult.append("Full Name: ").append(fullName).append(System.lineSeparator());
            exportResult.append("Salary: ").append(String.format("%.2f", employee.getSalary())).append(System.lineSeparator());
            exportResult.append("Started On: ").append(String.valueOf(employee.getStartedOn())).append(System.lineSeparator());
            exportResult.append("Clients: ").append(System.lineSeparator());

            for (Client client : employee.getClients()) {

                exportResult.append("\t" + client.getFullName()).append(System.lineSeparator());

            }


        }

        return exportResult.toString().trim();
    }
}
