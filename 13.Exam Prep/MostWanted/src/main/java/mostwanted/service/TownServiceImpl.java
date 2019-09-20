package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImpotDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private static final String TOWN_PATH_FILE = "src/main/resources/files/towns.json";
    private final FileUtil fileUtil;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(FileUtil fileUtil, TownRepository townRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() != 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(TOWN_PATH_FILE);

        return fileContent;
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder importResult = new StringBuilder();
        //1.Convert JSON file to Dto Array
        TownImpotDto[] townImpotDtos = this.gson.fromJson(townsFileContent, TownImpotDto[].class);
        for (TownImpotDto townImpotDto : townImpotDtos) {

            //2.Check if exist town into DB -print duplicated data message
            Town townEntity = this.townRepository.findByName(townImpotDto.getName()).orElse(null);
            if (townEntity != null) {
                importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            //3. Check is input data are validated-print incorrect data message
            if (!this.validationUtil.isValid(townImpotDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            //4. Map input Dto to Entity
            townEntity = this.modelMapper.map(townImpotDto, Town.class);
            //5. Save and Flush Entity to DB
            this.townRepository.saveAndFlush(townEntity);
            //6. Print result
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, townEntity.getClass().getSimpleName(), townEntity.getName())).append(System.lineSeparator());


        }

        return importResult.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder exportResult = new StringBuilder();
        List<Town> racingTowns = this.townRepository.findAllRasingTowns();
        for (Town t : racingTowns) {
            exportResult.append("Name: " + t.getName()).append(System.lineSeparator());
            exportResult.append("Racers: " + t.getRacers().size()).append(System.lineSeparator());
            exportResult.append(System.lineSeparator());
        }

        exportResult.append(System.lineSeparator());
        return exportResult.toString();
    }
}
