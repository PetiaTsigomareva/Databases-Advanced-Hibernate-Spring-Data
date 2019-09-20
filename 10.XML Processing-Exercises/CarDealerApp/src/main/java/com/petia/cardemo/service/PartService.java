package com.petia.cardemo.service;

import com.petia.cardemo.domain.dtos.importDtos.PartImportRootDto;

public interface PartService {

    void importParts(PartImportRootDto partImportRootDto);
}
