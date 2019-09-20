package app.exam.controller;

import app.exam.domain.dto.xml.OrderItemXMLImportDTO;
import app.exam.domain.dto.xml.OrderWrapperXMLImportDTO;
import app.exam.domain.dto.xml.OrderXMLImportDTO;
import app.exam.service.api.OrderService;
import app.exam.util.ValidationUtil;
import app.exam.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class OrdersController {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final OrderService orderService;

    @Autowired
    public OrdersController(XmlParser xmlParser, ValidationUtil validationUtil, OrderService orderService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.orderService = orderService;
    }

    //TODO
    public String importDataFromXML(String xmlContent) throws JAXBException, FileNotFoundException, ParseException {
        StringBuilder importResult = new StringBuilder();
        LocalDateTime dateTime = null;
        OrderWrapperXMLImportDTO orderWrapperXMLImportDTO = this.xmlParser.parseXml(OrderWrapperXMLImportDTO.class, xmlContent);
        for (OrderXMLImportDTO order : orderWrapperXMLImportDTO.getOrders()) {
            try {
                dateTime = LocalDateTime.parse(order.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            } catch (DateTimeException ex) {
                importResult.append("Error: Invalid data.").append(System.lineSeparator());
            }
            if (!this.validationUtil.isValid(order)) {
                importResult.append("Error: Invalid data.").append(System.lineSeparator());

                continue;
            } else {
                if (!this.validationUtil.isValid(order)) {
                    importResult.append("Error: Invalid data.").append(System.lineSeparator());
                } else {
                    this.orderService.create(order);
                    importResult.append(String.format("Order for %s on %s added", order.getCustomer(), dateTime));
                }
            }
        }


        return importResult.toString().

                trim();

    }

    //TODO
    public String exportOrdersByEmployeeAndOrderType(String employeeName, String orderType) {
        return null;
    }
}
