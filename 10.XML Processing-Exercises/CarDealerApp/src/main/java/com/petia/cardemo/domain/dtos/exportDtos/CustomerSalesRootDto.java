package com.petia.cardemo.domain.dtos.exportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSalesRootDto {
    @XmlElement(name = "customer")
    private List<CustomerSalesDto> customerSalesDtoList;

    public CustomerSalesRootDto() {
        this.customerSalesDtoList = new ArrayList<>();
    }

    public List<CustomerSalesDto> getCustomerSalesDtoList() {
        return customerSalesDtoList;
    }

    public void setCustomerSalesDtoList(CustomerSalesDto customerSalesDto) {
        this.customerSalesDtoList.add(customerSalesDto);
    }
}
