package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.RaceEntryImportDto;
import mostwanted.domain.dtos.RaceEntryRootImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class RaceEntryServceImpl implements RaceEntryService {
    private static final String RACE_ENTRY_FILE_PAHT = "src/main/resources/files/race-entries.xml";
    private final FileUtil fileUtil;
    private final RaceEntryRepository raceEntryRepository;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceEntryServceImpl(FileUtil fileUtil, RaceEntryRepository raceEntryRepository, XmlParser xmlParser, CarRepository carRepository, RacerRepository racerRepository, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.raceEntryRepository = raceEntryRepository;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean raceEntriesAreImported() {

        return this.raceEntryRepository.count() != 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        String fileContent = this.fileUtil.readFile(RACE_ENTRY_FILE_PAHT);
        return fileContent;
    }

    @Override
    public String importRaceEntries() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        RaceEntryRootImportDto raceEntryRootImportDto = this.xmlParser.parseXml(RaceEntryRootImportDto.class, RACE_ENTRY_FILE_PAHT);
        for (RaceEntryImportDto raceEntryImportDto : raceEntryRootImportDto.getRaceEntryImportDtos()) {
            Car carEntity = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
            Racer racerEntity = this.racerRepository.findByName(raceEntryImportDto.getRacerName()).orElse(null);

            if (carEntity == null || racerEntity == null) {

                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            RaceEntry raceEntryEntity = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
            raceEntryEntity.setCar(carEntity);
            raceEntryEntity.setRacer(racerEntity);
            raceEntryEntity.setRace(null);

            this.raceEntryRepository.saveAndFlush(raceEntryEntity);

            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, raceEntryEntity.getClass().getSimpleName(), raceEntryEntity.getId())).append(System.lineSeparator());


        }

        return importResult.toString().trim();
    }
}
