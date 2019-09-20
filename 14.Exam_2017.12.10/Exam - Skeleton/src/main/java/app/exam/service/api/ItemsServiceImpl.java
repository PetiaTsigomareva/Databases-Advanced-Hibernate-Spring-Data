package app.exam.service.api;

import app.exam.domain.dto.json.ItemJSONImportDTO;
import app.exam.domain.entities.Category;
import app.exam.domain.entities.Item;
import app.exam.repository.CategoryRepository;
import app.exam.repository.ItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl implements ItemsService {
    private final ItemsRepository itemsRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemsServiceImpl(ItemsRepository itemsRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.itemsRepository = itemsRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(ItemJSONImportDTO itemJSONImportDTO) {
        Item itemEntity = this.itemsRepository.findByName(itemJSONImportDTO.getName()).orElse(null);
        if (itemEntity == null) {
            Category categoryEntity = this.categoryRepository.findByName(itemJSONImportDTO.getCategory()).orElse(null);
            if (categoryEntity == null) {
                categoryEntity = new Category();
                categoryEntity.setName(itemJSONImportDTO.getCategory());
                this.categoryRepository.saveAndFlush(categoryEntity);
            }
            itemEntity = this.modelMapper.map(itemJSONImportDTO, Item.class);
            itemEntity.setCategory(categoryEntity);
            itemEntity.setOrderItems(null);

            this.itemsRepository.saveAndFlush(itemEntity);

        }

    }
}
