package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.PartSeedDto;

public interface PartService {
    void seedParts(PartSeedDto[] partSeedDtos);
}
