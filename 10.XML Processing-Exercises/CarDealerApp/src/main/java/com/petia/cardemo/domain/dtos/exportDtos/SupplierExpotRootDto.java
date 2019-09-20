package com.petia.cardemo.domain.dtos.exportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierExpotRootDto {
    @XmlElement(name = "supplier")
    private List<SupplierExportDto> supplierExportDtoList;

    public SupplierExpotRootDto() {
        this.supplierExportDtoList = new ArrayList<>();
    }

    public List<SupplierExportDto> getSupplierExportDtoList() {
        return supplierExportDtoList;
    }

    public void addSupplierExportDtoList(SupplierExportDto supplierExportDto) {
        this.supplierExportDtoList.add(supplierExportDto);
    }
}
