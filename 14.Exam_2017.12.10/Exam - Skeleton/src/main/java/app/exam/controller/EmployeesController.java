package app.exam.controller;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;
import app.exam.service.api.EmployeeService;
import app.exam.util.FileUtil;
import app.exam.util.ValidationUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeesController {

    private final Gson gson;
    private final EmployeeService employeeService;
    private final ValidationUtil validationUtil;



    @Autowired
    public EmployeesController(FileUtil fileUtil, Gson gson, EmployeeService employeeService, ValidationUtil validationUtil) {
        this.gson = gson;
        this.employeeService = employeeService;
        this.validationUtil = validationUtil;
    }

    public String importDataFromJSON(String jsonContent) {
        StringBuilder importResult = new StringBuilder();
        //1. Create DTO [] object
        EmployeeJSONImportDTO[] employeeJSONImportDTOS = this.gson.fromJson(jsonContent, EmployeeJSONImportDTO[].class);
        //2.Check is data valid
        for (EmployeeJSONImportDTO employeeJSONImportDTO : employeeJSONImportDTOS) {
            if (!this.validationUtil.isValid(employeeJSONImportDTO)) {

                importResult.append("Error: Invalid data.").append(System.lineSeparator());
                continue;

            }
            //3.Call employee service method with DTO[]
            this.employeeService.create(employeeJSONImportDTO);
            importResult.append(String.format("Record %s successfully imported", employeeJSONImportDTO.getName())).append(System.lineSeparator());



        }

        return importResult.toString().trim();
    }
}
