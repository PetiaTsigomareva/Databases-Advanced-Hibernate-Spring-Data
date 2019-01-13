package alararestaurant.service;

import alararestaurant.domain.dtos.OrderItemsImportXmlDto;
import alararestaurant.domain.dtos.OrderImportXmlDto;
import alararestaurant.domain.dtos.OrderRootImportXmlDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.*;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String POSITION_NAME = "Burger Flipper";
    private static final String ORDER_XML_PATH_FILE = "src/main/resources/files/orders.xml";
    private final OrderRepository orderRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final PositionRepository positionRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, FileUtil fileUtil, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper, EmployeeRepository employeeRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
        this.positionRepository = positionRepository;
    }


    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        String fileContent = this.fileUtil.readFile(ORDER_XML_PATH_FILE);
        return fileContent;
    }

    @Override
    public String importOrders() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        LocalDateTime dateTime = null;
        OrderRootImportXmlDto orderRootImportXmlDto = this.xmlParser.parseXml(OrderRootImportXmlDto.class, ORDER_XML_PATH_FILE);
        for (OrderImportXmlDto order : orderRootImportXmlDto.getOrders()) {

            try {
                dateTime = LocalDateTime.parse(order.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            } catch (DateTimeException ex) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
            }
            if (!this.validationUtil.isValid(order)) {
                importResult.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }


            Employee employeeEntity = this.employeeRepository.findByName(order.getEmployee()).orElse(null);
            if (employeeEntity != null) {

                Order orderEntity = this.modelMapper.map(order, Order.class);
                orderEntity.setEmployee(employeeEntity);
                orderEntity.setDateTime(dateTime);


                if (!(order.getType().equals(OrderType.ForHere.toString())) && !(order.getType().equals(OrderType.ToGo.toString()))) {
                    orderEntity.setType(OrderType.ForHere);
                } else {
                    orderEntity.setType(OrderType.valueOf(order.getType()));
                }
                orderEntity.setOrderItems(null);


                for (OrderItemsImportXmlDto item : order.getItems()) {
                    Item itemEntity = this.itemRepository.findByName(item.getName()).orElse(null);
                    if (itemEntity == null) {
                        break;
                    }
                    orderEntity = this.orderRepository.saveAndFlush(orderEntity);

                    OrderItem orderItemEntity = new OrderItem();
                    orderItemEntity.setItem(itemEntity);
                    orderItemEntity.setOrder(orderEntity);
                    orderItemEntity.setQuantity(item.getQuantity());

                    this.orderItemRepository.saveAndFlush(orderItemEntity);

                }
                importResult.append(String.format("Order for %s on %s added", order.getCustomer(), order.getDateTime())).append(System.lineSeparator());

            }
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder exportResult = new StringBuilder();
        Position positionEntity = this.positionRepository.findByName(POSITION_NAME).orElse(null);
        Set<Employee> burgerFlippersEmployees = this.employeeRepository.findBurgerFlippersOrders(positionEntity);
        //  Set<Employee> burgerFlippersEmployees = positionEntity.getEmployees();
        for (Employee employee : burgerFlippersEmployees) {
            exportResult.append("Name: " + employee.getName()).append(System.lineSeparator());
            exportResult.append("Orders:").append(System.lineSeparator());

            for (Order order : employee.getOrders()) {
                exportResult.append("\tCustomer: " + order.getCustomer()).append(System.lineSeparator());
                exportResult.append("\tItems: ").append(System.lineSeparator());
                for (OrderItem item : order.getOrderItems()) {
                    exportResult.append("\t\tName: " + item.getItem().getName()).append(System.lineSeparator());
                    exportResult.append("\t\tPrice: " + item.getItem().getPrice()).append(System.lineSeparator());
                    exportResult.append("\t\tQuantity: " + item.getQuantity()).append(System.lineSeparator());
                    exportResult.append(System.lineSeparator());
                }

            }

            }


            return exportResult.toString().trim();
        }
//Variant2
//    @Override
//    public String exportOrdersFinishedByTheBurgerFlippers() {
//        StringBuilder exportResult = new StringBuilder();
//
//        List<Order> orders = this.orderRepository.findBurgerFlippersOrders1();
//        for (Order order : orders) {
//            exportResult.append("Name: " + order.getEmployee().getName()).append(System.lineSeparator());
//            exportResult.append("Orders:").append(System.lineSeparator());
//            exportResult.append("\tCustomer: " + order.getCustomer()).append(System.lineSeparator());
//            exportResult.append("\tItems: ").append(System.lineSeparator());
//                for (OrderItem item : order.getOrderItems()) {
//                    exportResult.append("\t\tName: " + item.getItem().getName()).append(System.lineSeparator());
//                    exportResult.append("\t\tPrice: " + item.getItem().getPrice()).append(System.lineSeparator());
//                    exportResult.append("\t\tQuantity:: " + item.getQuantity()).append(System.lineSeparator());
//                    exportResult.append(System.lineSeparator());
//                }
//
//
//
//        }
//
//
//        return exportResult.toString().trim();
//    }
}
