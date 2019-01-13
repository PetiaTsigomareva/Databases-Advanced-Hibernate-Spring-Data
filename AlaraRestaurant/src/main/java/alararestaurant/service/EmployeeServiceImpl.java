package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_JSON_PATH_FILE = "src/main/resources/files/employees.json";
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, PositionRepository positionRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.positionRepository = positionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(EMPLOYEE_JSON_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportDto[] employeeJSONImportDTOS = this.gson.fromJson(employees, EmployeeImportDto[].class);

        for (EmployeeImportDto employeeJSONImportDTO : employeeJSONImportDTOS) {
            if (!this.validationUtil.isValid(employeeJSONImportDTO)) {

                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;

            }

            Position positionEntity = this.positionRepository.findByName(employeeJSONImportDTO.getPosition()).orElse(null);
            if (positionEntity == null) {

                positionEntity = new Position();
                positionEntity.setName(employeeJSONImportDTO.getPosition());
                positionEntity.setEmployees(null);
                this.positionRepository.saveAndFlush(positionEntity);
            }


            Employee employeeEntity = this.modelMapper.map(employeeJSONImportDTO, Employee.class);
            employeeEntity.setPosition(positionEntity);

            this.employeeRepository.saveAndFlush(employeeEntity);
            importResult.append(String.format("Record %s successfully imported.", employeeJSONImportDTO.getName())).append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }


}
