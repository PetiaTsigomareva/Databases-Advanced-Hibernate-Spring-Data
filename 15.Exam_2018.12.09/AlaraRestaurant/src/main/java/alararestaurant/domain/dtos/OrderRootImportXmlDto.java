package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderRootImportXmlDto {
    @XmlElement(name = "order")
    private List<OrderImportXmlDto> orders;

    public OrderRootImportXmlDto() {
        this.orders = new ArrayList<>();
    }


    public List<OrderImportXmlDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderImportXmlDto> orders) {
        this.orders = orders;
    }
}
