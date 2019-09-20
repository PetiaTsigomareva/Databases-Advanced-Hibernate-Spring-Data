package com.petia.productsshop.service;

import com.petia.productsshop.domain.dtos.UserSeedDto;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);
}
