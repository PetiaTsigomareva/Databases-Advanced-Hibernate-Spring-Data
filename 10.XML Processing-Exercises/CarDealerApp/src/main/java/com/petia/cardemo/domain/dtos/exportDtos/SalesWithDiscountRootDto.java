package com.petia.cardemo.domain.dtos.exportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesWithDiscountRootDto {
    @XmlElement(name = "sale")
    private List<SalesWithDiscountDto> salesWithDiscountDtos;

    public SalesWithDiscountRootDto() {
        this.salesWithDiscountDtos = new ArrayList<>();
    }

    public List<SalesWithDiscountDto> getSalesWithDiscountDtos() {
        return salesWithDiscountDtos;
    }

    public void setSalesWithDiscountDtos(SalesWithDiscountDto salesWithDiscountDto) {
        this.salesWithDiscountDtos.add(salesWithDiscountDto);
    }
}
