package com.petia.productsshop.service;

import com.petia.productsshop.domain.dtos.ProductInRangeDto;
import com.petia.productsshop.domain.dtos.ProductSeedDto;
import com.petia.productsshop.domain.dtos.UserSoldProductsDto;
import com.petia.productsshop.domain.dtos.UserAgeSoldProductsDto;


import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);

    List<UserSoldProductsDto> successfullySoldProducts();

    List<UserAgeSoldProductsDto> userSoldProducts();
}
