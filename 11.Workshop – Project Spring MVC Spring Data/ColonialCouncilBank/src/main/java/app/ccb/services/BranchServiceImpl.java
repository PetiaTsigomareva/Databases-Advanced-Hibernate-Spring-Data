package app.ccb.services;

import app.ccb.domain.dtos.BranchImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {
    private final static String BRANCH_JSON_PATH_FILE = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\11.Workshop – Project Spring MVC Spring Data\\WorkShop_2018-11-26\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean branchesAreImported() {

        return this.branchRepository.count() != 0;

    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(BRANCH_JSON_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder result = new StringBuilder();
        BranchImportDto[] branchImportDtos = this.gson.fromJson(branchesJson, BranchImportDto[].class);
        for (BranchImportDto branchImportDto : branchImportDtos) {

            if (!this.validationUtil.isValid(branchImportDto)) {
                result.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }
            Branch branchEntity = this.modelMapper.map(branchImportDto, Branch.class);
            this.branchRepository.saveAndFlush(branchEntity);

            result.append(String.format("Successfully imported Branch – %s", branchEntity.getName())).append(System.lineSeparator());

        }

        return result.toString().trim();
    }
}
