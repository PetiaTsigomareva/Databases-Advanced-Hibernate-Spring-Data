package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.EntryDto;
import mostwanted.domain.dtos.RaceImportDto;
import mostwanted.domain.dtos.RaceRootImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {
    private static final String RACE_PATH_FILE = "src/main/resources/files/races.xml";
    private final FileUtil fileUtil;
    private final RaceRepository raceRepository;
    private final XmlParser xmlParser;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceServiceImpl(FileUtil fileUtil, RaceRepository raceRepository, XmlParser xmlParser, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.raceRepository = raceRepository;
        this.xmlParser = xmlParser;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() != 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        String fileContent = this.fileUtil.readFile(RACE_PATH_FILE);

        return fileContent;
    }

    @Override
    public String importRaces() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        //1.Convert Xml file to Root Dto object
        RaceRootImportDto raceRootImportDto = this.xmlParser.parseXml(RaceRootImportDto.class, RACE_PATH_FILE);
        for (RaceImportDto raceImportDto : raceRootImportDto.getRaceImportDtos()) {
            //2.Check is valided district-name and entry id
            District districtEntity = this.districtRepository.findByName(raceImportDto.getDistrictName()).orElse(null);
            if (!this.validationUtil.isValid(raceImportDto) || districtEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE + "- districtEntity is null").append(System.lineSeparator());
                continue;
            }
            Race raceEntity = this.modelMapper.map(raceImportDto, Race.class);
            raceEntity.setDistrict(districtEntity);
            List<RaceEntry> raceEntries = raceEntity.getEntries();


            for (EntryDto entryDto : raceImportDto.getEntryRootDto().getEtriEntryDtoList()) {
                RaceEntry entryEntity = this.raceEntryRepository.findById(entryDto.getId()).orElse(null);
                if (entryEntity == null) {
                    importResult.append(Constants.INCORRECT_DATA_MESSAGE + "- entryEntity id is null").append(System.lineSeparator());
                    continue;
                }
                entryEntity.setRace(raceEntity);
                raceEntries.add(entryEntity);


            }
            raceEntity = this.raceRepository.saveAndFlush(raceEntity);
            this.raceEntryRepository.saveAll(raceEntries);

            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, raceEntity.getClass().getSimpleName(), raceEntity.getId())).append(System.lineSeparator());


        }

        return importResult.toString().trim();
    }
}
