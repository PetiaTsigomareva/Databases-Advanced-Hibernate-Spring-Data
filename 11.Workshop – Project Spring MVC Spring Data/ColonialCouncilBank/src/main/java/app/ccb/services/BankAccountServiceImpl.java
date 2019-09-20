package app.ccb.services;

import app.ccb.domain.dtos.BankAccountImportDto;
import app.ccb.domain.dtos.BankAccountRootImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final static String BANK_ACCOUNT_PATH_FILE = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\11.Workshop – Project Spring MVC Spring Data\\WorkShop_2018-11-26\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";
    private final FileUtil fileUtil;
    private final BankAccountRepository bankAccountRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountServiceImpl(FileUtil fileUtil, BranchRepository bankBranchRepository, BankAccountRepository bankAccountRepository, XmlParser xmlParser, ValidationUtil validationUtil, ClientRepository clientRepository, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.bankAccountRepository = bankAccountRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean bankAccountsAreImported() {

        return this.bankAccountRepository.count() != 0;

    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        String fileContent = this.fileUtil.readFile(BANK_ACCOUNT_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importBankAccounts() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        BankAccountRootImportDto bankAccountRootImportDto = this.xmlParser.parseXml(BankAccountRootImportDto.class, BANK_ACCOUNT_PATH_FILE);
        for (BankAccountImportDto bankAccountImportDto : bankAccountRootImportDto.getBankAccountImportDtos()) {
            if (!validationUtil.isValid(bankAccountImportDto)) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            Client clientEntity = this.clientRepository.findByFullName(bankAccountImportDto.getClient()).orElse(null);

            if (clientEntity == null) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            BankAccount bankAccountEntity = this.modelMapper.map(bankAccountImportDto, BankAccount.class);
            bankAccountEntity.setClient(clientEntity);

            this.bankAccountRepository.saveAndFlush(bankAccountEntity);

            importResult.append(String.format("Successfully imported Bank Account - %s", bankAccountEntity.getAccountNumber())).append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
