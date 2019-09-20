package app.ccb.services;

import app.ccb.domain.dtos.CardImportDto;
import app.ccb.domain.dtos.CardImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
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
public class CardServiceImpl implements CardService {
    private static final String CARD_PATH_FILE = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\11.Workshop – Project Spring MVC Spring Data\\WorkShop_2018-11-26\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";
    private final CardRepository cardRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, BankAccountRepository bankAccountRepository, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.bankAccountRepository = bankAccountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean cardsAreImported() {

        return this.cardRepository.count() != 0;

    }

    @Override
    public String readCardsXmlFile() throws IOException {
        String fileContent = this.fileUtil.readFile(CARD_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importCards() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        CardImportRootDto cardImportRootDto = this.xmlParser.parseXml(CardImportRootDto.class, CARD_PATH_FILE);
        for (CardImportDto cardImportDto : cardImportRootDto.getCardImportDtos()) {

            if (!validationUtil.isValid(cardImportDto)) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }
            BankAccount bankAccountEntity = this.bankAccountRepository.findByAccountNumber(cardImportDto.getAccountNumber()).orElse(null);
            if (bankAccountEntity == null) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }
            Card cardEntity = this.modelMapper.map(cardImportDto, Card.class);
            cardEntity.setBankAccount(bankAccountEntity);
            this.cardRepository.saveAndFlush(cardEntity);

            importResult.append(String.format("Successfully imported Card - %s", cardEntity.getCardNumber())).append(System.lineSeparator());


        }
        return importResult.toString().trim();
    }
}
