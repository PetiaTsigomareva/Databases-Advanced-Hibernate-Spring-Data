package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder exportResult = new StringBuilder();
        List<Category> categoryByItemsCount = this.categoryRepository.findCategoryByItems();
        for (Category category : categoryByItemsCount) {
            exportResult.append("Category: " + category.getName() + " - Item Size: " + category.getItems().size()).append(System.lineSeparator());
            for (Item categoryItem : category.getItems()) {
                exportResult.append("--- Item Name: " + categoryItem.getName()).append(System.lineSeparator());
                exportResult.append("--- Item Price: " + categoryItem.getPrice()).append(System.lineSeparator());
                exportResult.append(System.lineSeparator());
            }
            }

        return exportResult.toString().trim();
    }
}
