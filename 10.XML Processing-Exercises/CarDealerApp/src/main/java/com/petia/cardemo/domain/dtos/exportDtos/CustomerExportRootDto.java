package com.petia.cardemo.domain.dtos.exportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportRootDto {
    @XmlElement(name = "customer")
    private List<CustomerExportDto> customerExportDtos;

    public CustomerExportRootDto() {

    }

    public List<CustomerExportDto> getCustomerExportDtos() {

        return customerExportDtos;
    }

    public void setCustomerExportDtos(List<CustomerExportDto> customerExportDtos) {
        this.customerExportDtos = customerExportDtos;
    }
}
