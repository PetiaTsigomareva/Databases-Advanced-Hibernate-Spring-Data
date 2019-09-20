package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {
    private static final String DISTRICT_PATH_FILE = "src/main/resources/files/districts.json";
    private final FileUtil fileUtil;
    private final DistrictRepository districtRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DistrictServiceImpl(FileUtil fileUtil, DistrictRepository districtRepository, Gson gson, ValidationUtil validationUtil, TownRepository townRepository, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.districtRepository = districtRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() != 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(DISTRICT_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder importResult = new StringBuilder();
        //1.Convert JSON file to Dto Array
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);
        for (DistrictImportDto districtImportDto : districtImportDtos) {
            //2.Check if exist district into DB - print duplicated data message
            District districtEntity = this.districtRepository.findByName(districtImportDto.getName()).orElse(null);
            if (districtEntity != null) {
                importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            //3. Check if exist town in DB- print incorrect data message
            //3.1 Check is input data are validated-print incorrect data message
            Town townEntity = this.townRepository.findByName(districtImportDto.getTownName()).orElse(null);
            if (!this.validationUtil.isValid(districtImportDto) || townEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            //4. Map input Dto to Entity
            districtEntity = this.modelMapper.map(districtImportDto, District.class);
            districtEntity.setTown(townEntity);
            //5. Save and Flush Entity to DB
            this.districtRepository.saveAndFlush(districtEntity);
            //6. Print result
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, districtEntity.getClass().getSimpleName(), districtEntity.getName())).append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
