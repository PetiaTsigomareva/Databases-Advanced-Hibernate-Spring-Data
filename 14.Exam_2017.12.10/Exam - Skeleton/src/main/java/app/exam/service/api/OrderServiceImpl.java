package app.exam.service.api;

import app.exam.domain.dto.json.EmployeeOrdersJSONExportDTO;
import app.exam.domain.dto.xml.OrderItemXMLImportDTO;
import app.exam.domain.dto.xml.OrderXMLImportDTO;
import app.exam.domain.entities.*;
import app.exam.repository.EmployeeRepository;
import app.exam.repository.ItemsRepository;
import app.exam.repository.OrderItemRepository;
import app.exam.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {
    private final EmployeeRepository employeeRepository;
    private final ItemsRepository itemsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(EmployeeRepository employeeRepository, ItemsRepository itemsRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.itemsRepository = itemsRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(OrderXMLImportDTO dto) throws ParseException {
        //check employee is exist
        Employee employeeEntity = this.employeeRepository.findByName(dto.getEmployee()).orElse(null);
        if (employeeEntity != null) {
            //Map Order entity
            Order orderEntity = this.modelMapper.map(dto, Order.class);
            orderEntity.setEmployee(employeeEntity);
            //format datetime dd/MM/yyyy HH:mm
            LocalDateTime dateTime = LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            orderEntity.setDate(dateTime);
            if (!(dto.getType().equals(OrderType.ForHere.toString()))&&!(dto.getType().equals(OrderType.ToGo.toString()))) {
                orderEntity.setType(OrderType.ForHere);
            } else {
                orderEntity.setType(OrderType.valueOf(dto.getType()));
            }
            orderEntity.setItems(null);
            //Save order entity
            orderEntity = this.orderRepository.saveAndFlush(orderEntity);
            //check items is exist
            for (OrderItemXMLImportDTO item : dto.getItems()) {
                Item itemEntity = this.itemsRepository.findByName(item.getName()).orElse(null);
                if (itemEntity == null) {
                    break;
                }
                //Map and save order_item entity
                OrderItem orderItemEntity = new OrderItem();
                orderItemEntity.setItem(itemEntity);
                orderItemEntity.setOrder(orderEntity);
                orderItemEntity.setQuantity(item.getQuantity());

                this.orderItemRepository.saveAndFlush(orderItemEntity);

            }


        }

    }

    @Override
    public EmployeeOrdersJSONExportDTO exportOrdersByEmployeeAndOrderType(String employeeName, String orderType) {
        return null;
    }
}
