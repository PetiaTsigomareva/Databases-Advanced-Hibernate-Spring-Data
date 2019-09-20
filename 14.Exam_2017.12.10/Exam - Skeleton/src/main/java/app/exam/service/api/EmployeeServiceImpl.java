package app.exam.service.api;

import app.exam.domain.dto.json.EmployeeJSONImportDTO;
import app.exam.domain.entities.Employee;
import app.exam.domain.entities.Position;
import app.exam.repository.EmployeeRepository;
import app.exam.repository.PositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.modelMapper = modelMapper;

    }


    @Override
    public void create(EmployeeJSONImportDTO importDTO) {
        //check position unique
        Position positionEntity = this.positionRepository.findByName(importDTO.getPosition()).orElse(null);
        if (positionEntity == null) {
            //create new position record
            positionEntity = new Position();
            positionEntity.setName(importDTO.getPosition());
            positionEntity.setEmployees(null);
            this.positionRepository.saveAndFlush(positionEntity);
        }

        //mapping implementation
        Employee employeeEntity = this.modelMapper.map(importDTO, Employee.class);
        employeeEntity.setPosition(positionEntity);
        //Save and flush employee
        this.employeeRepository.saveAndFlush(employeeEntity);


    }

    @Override
    public void createMany(EmployeeJSONImportDTO[] importDTO) {


    }
}
