package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {
    private static final String CAR_PATH_FILE = "src/main/resources/files/cars.json";
    private final FileUtil fileUtil;
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final RacerRepository racerRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CarServiceImpl(FileUtil fileUtil, CarRepository carRepository, Gson gson, ValidationUtil validationUtil, RacerRepository racerRepository, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.racerRepository = racerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean carsAreImported() {

        return this.carRepository.count() != 0;

    }

    @Override
    public String readCarsJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(CAR_PATH_FILE);

        return fileContent;
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder importResult = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {

            Racer racerEntity = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);
            if (!this.validationUtil.isValid(carImportDto) || racerEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Car carEntity = this.modelMapper.map(carImportDto, Car.class);
            carEntity.setRacer(racerEntity);

            this.carRepository.saveAndFlush(carEntity);
            String carImportResult = String.format("%s %s @ %d", carEntity.getBrand(), carEntity.getModel(), carEntity.getYearOfProduction());
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, carEntity.getClass().getSimpleName(), carImportResult)).append(System.lineSeparator());


        }
        return importResult.toString().trim();
    }
}
