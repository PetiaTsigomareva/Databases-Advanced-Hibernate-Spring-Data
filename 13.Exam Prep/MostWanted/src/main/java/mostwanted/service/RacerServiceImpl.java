package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RacerServiceImpl implements RacerService {
    private static final String RACER_PATH_FILE = "src/main/resources/files/racers.json";
    private final FileUtil fileUtil;
    private final RacerRepository racerRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RacerServiceImpl(FileUtil fileUtil, RacerRepository racerRepository, Gson gson, ValidationUtil validationUtil, TownRepository townRepository, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.racerRepository = racerRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() != 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(RACER_PATH_FILE);

        return fileContent;
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder importResul = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);
        for (RacerImportDto racerImportDto : racerImportDtos) {

            Racer racerEntity = this.racerRepository.findByName(racerImportDto.getName()).orElse(null);
            if (racerEntity != null) {
                importResul.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Town townEntity = this.townRepository.findByName(racerImportDto.getHomeTown()).orElse(null);
            if (!this.validationUtil.isValid(racerImportDto) || townEntity == null) {
                importResul.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;

            }
            racerEntity = this.modelMapper.map(racerImportDto, Racer.class);
            racerEntity.setHomeTown(townEntity);

            this.racerRepository.saveAndFlush(racerEntity);

            importResul.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, racerEntity.getClass().getSimpleName(), racerEntity.getName())).append(System.lineSeparator());

        }

        return importResul.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder exportResult = new StringBuilder();
        List<Racer> getRacerCars = this.racerRepository.findRacerCars();
        for (Racer racer : getRacerCars) {
            exportResult.append("Name: " + racer.getName()).append(System.lineSeparator());
            exportResult.append("Cars:").append(System.lineSeparator());
            for (Car car : racer.getCars()) {

                exportResult.append("\t" + String.format("%s %s %d", car.getBrand(), car.getModel(), car.getYearOfProduction())).append(System.lineSeparator());
            }
            exportResult.append(System.lineSeparator());

        }
        return exportResult.toString();
    }
}
