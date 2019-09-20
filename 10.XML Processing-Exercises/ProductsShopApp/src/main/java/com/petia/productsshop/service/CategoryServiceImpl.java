package com.petia.productsshop.service;

import com.petia.productsshop.domain.dtos.CategoriesByProductsDto;
import com.petia.productsshop.domain.dtos.CategorySeedDto;
import com.petia.productsshop.domain.entities.Category;
import com.petia.productsshop.repository.CategoryRepository;
import com.petia.productsshop.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!this.validatorUtil.isValid(categorySeedDto)) {
                this.validatorUtil.violations(categorySeedDto).forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            Category entity = this.modelMapper.map(categorySeedDto, Category.class);

            this.categoryRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<CategoriesByProductsDto> getCategoriesByProductsCount() {

        List<CategoriesByProductsDto> categoriesDto = new ArrayList<>();
        List<Category> categoriesEntity = this.categoryRepository.findAll();
        for (Category c : categoriesEntity) {

            CategoriesByProductsDto category = new CategoriesByProductsDto();
            category.setName(c.getName());
            category.setProductsNumber(c.getProducts().size());
            category.setAverageProductsPrice(c.getProducts().stream().mapToDouble(e -> e.getPrice().doubleValue()).average().orElse(Double.NaN));
            category.setTotalPriceSum(c.getProducts().stream().mapToDouble(e -> e.getPrice().doubleValue()).sum());


            categoriesDto.add(category);

        }
        categoriesDto.sort((CategoriesByProductsDto c1, CategoriesByProductsDto c2) -> c2.getProductsNumber() - c1.getProductsNumber());
        return categoriesDto;
    }


}
