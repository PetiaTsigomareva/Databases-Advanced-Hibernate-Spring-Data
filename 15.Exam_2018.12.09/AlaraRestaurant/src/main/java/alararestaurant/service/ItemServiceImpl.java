package alararestaurant.service;

import alararestaurant.domain.dtos.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String ITEMS_JSON_PATH_FILE = "src/main/resources/files/items.json";
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        String fileContent = this.fileUtil.readFile(ITEMS_JSON_PATH_FILE);

        return fileContent;
    }

    @Override
    public String importItems(String items) {
        StringBuilder importResult = new StringBuilder();
        ItemImportDto[] itemImportDtos = this.gson.fromJson(items, ItemImportDto[].class);

        for (ItemImportDto itemImportDto : itemImportDtos) {
            if (!this.validationUtil.isValid(itemImportDto)) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }
            Item itemEntity = this.itemRepository.findByName(itemImportDto.getName()).orElse(null);
            if (itemEntity == null) {
                Category categoryEntity = this.categoryRepository.findByName(itemImportDto.getCategory()).orElse(null);
                if (categoryEntity == null) {
                    categoryEntity = new Category();
                    categoryEntity.setName(itemImportDto.getCategory());
                    this.categoryRepository.saveAndFlush(categoryEntity);
                }
                itemEntity = this.modelMapper.map(itemImportDto, Item.class);
                itemEntity.setCategory(categoryEntity);
                itemEntity.setOrderItems(null);

                this.itemRepository.saveAndFlush(itemEntity);
                importResult.append(String.format("Record %s successfully imported.", itemImportDto.getName())).append(System.lineSeparator());

            }

        }


        return importResult.toString().trim();
    }
}