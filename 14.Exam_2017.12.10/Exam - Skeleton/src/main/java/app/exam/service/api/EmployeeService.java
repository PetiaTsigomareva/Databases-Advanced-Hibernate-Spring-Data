package app.exam.service.api;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;

public interface EmployeeService {
    //TODO
    void create(EmployeeJSONImportDTO importDTO);
    //TODO
    void createMany(EmployeeJSONImportDTO[] importDTO);

}
