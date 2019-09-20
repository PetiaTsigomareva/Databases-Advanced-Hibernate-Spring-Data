package app.ccb.services;

import app.ccb.domain.dtos.ClientImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final static String CLIENT_JSON_PATH_FILE = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\11.Workshop – Project Spring MVC Spring Data\\WorkShop_2018-11-26\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public ClientServiceImpl(ClientRepository clientRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Boolean clientsAreImported() {

        return this.clientRepository.count() != 0;

    }

    @Override
    public String readClientsJsonFile() throws IOException {
        //read file
        String fileContent = this.fileUtil.readFile(CLIENT_JSON_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importClients(String clients) {
        StringBuilder importResult = new StringBuilder();
        //convert json file to dto object
        ClientImportDto[] clientImportDtos = this.gson.fromJson(clients, ClientImportDto[].class);

        for (ClientImportDto clientImportDto : clientImportDtos) {
            //vaidate all dto elements objects
            if (!this.validationUtil.isValid(clientImportDto)) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());
                continue;
            }
            //get employee with given name
            Employee employeeEntity = this.employeeRepository.findByFullName(clientImportDto.getAppointedEmployee()).orElse(null);

            if (employeeEntity == null) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }
            //check is exist client with given name
            Client clientEntity = this.clientRepository.findByFullName(String.format("%s %s", clientImportDto.getFirstName(), clientImportDto.getLastName())).orElse(null);

            if (clientEntity != null) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }


            clientEntity = this.modelMapper.map(clientImportDto, Client.class);
            clientEntity.setFullName(String.format("%s %s", clientImportDto.getFirstName(), clientImportDto.getLastName()));
            clientEntity.getEmployees().add(employeeEntity);


            this.clientRepository.saveAndFlush(clientEntity);

            importResult.append(String.format("Successfully imported Client - %s", clientEntity.getFullName())).append(System.lineSeparator());


        }


        return importResult.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        StringBuilder exportResult = new StringBuilder();
        Client clientEntity = this.clientRepository.findFamilyGuy().stream().findFirst().orElse(null);
        exportResult.append("Full Name: ").append(clientEntity.getFullName()).append(System.lineSeparator());
        exportResult.append("Age: ").append(clientEntity.getAge()).append(System.lineSeparator());
        exportResult.append("Bank Account: ").append(clientEntity.getBankAccount().getAccountNumber()).append(System.lineSeparator());
        exportResult.append("Cards: ").append(System.lineSeparator());

        for (Card card : clientEntity.getBankAccount().getCards()) {
            exportResult.append("\tCard Number: ").append(card.getCardNumber()).append(System.lineSeparator());
            exportResult.append("\tStatus: ").append(card.getCardStatus()).append(System.lineSeparator());
            exportResult.append(System.lineSeparator());
        }


        return exportResult.toString().trim();
    }
}
