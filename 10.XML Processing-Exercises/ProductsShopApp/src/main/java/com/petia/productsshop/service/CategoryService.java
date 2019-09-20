package com.petia.productsshop.service;

import com.petia.productsshop.domain.dtos.CategoriesByProductsDto;
import com.petia.productsshop.domain.dtos.CategorySeedDto;
import com.petia.productsshop.domain.dtos.UserSoldProductsDto;

import java.util.List;


public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<CategoriesByProductsDto> getCategoriesByProductsCount();
}
