package com.redscooter.API.order;

import com.redscooter.API.OrderLine.OrderLine;
import com.redscooter.API.OrderLine.OrderLineService;
import com.redscooter.API.common.BaseService;
import com.redscooter.API.order.DTO.CreateOrderDTO;
import com.redscooter.API.order.OrderStatusChanged.OnOrderStatusChangedEvent;
import com.redscooter.API.order.OrderStatusChanged.OrderStatusHTMLBuilder;
import com.redscooter.API.order.OrderVerifcication.OnOrderConfirmedEvent;
import org.restprocessors.DynamicRESTController.CriteriaParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService extends BaseService<Order> {

    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderLineService orderLineService, ApplicationEventPublisher eventPublisher) {
        super(orderRepository, "Order");
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.eventPublisher = eventPublisher;
    }

    public Page<Order> getAllByCriteria(CriteriaParameters cp) {
        return orderRepository.findAllByCriteria(cp);
    }

    public Order create(Order order, List<OrderLine> orderLines, OrderBilling orderBilling) {
        for (OrderLine o : orderLines)
            orderLineService.save(o);
        order.addOrderLines(orderLines);
        order.setOrderBilling(orderBilling);
        return orderRepository.save(order);
    }

    public Order create(CreateOrderDTO createOrderDTO, boolean persist) {
        Order order = new Order();
        order.setOrderBilling(new OrderBilling(createOrderDTO.getOrderBilling()));
        if (createOrderDTO.getUserId() != null) order.setUserId(createOrderDTO.getUserId());
        order.setOrderLines(orderLineService.create(createOrderDTO.getOrderLines(), persist));
        if (!persist)
            return order;
        order = save(order);
        eventPublisher.publishEvent(new OnOrderConfirmedEvent(order));
        return order;
    }

    public Order confirmOrder(Order order) {
        for (OrderLine o : order.getOrderLines())
            orderLineService.save(o);
        order = save(order);
        eventPublisher.publishEvent(new OnOrderConfirmedEvent(order));
        return order;
    }

    public Order changeOrderStatus(Long orderId, OrderStatus orderStatus, String adminNotesOnStatusChange) {
        Order order = getById(orderId);
        order.setOrderStatus(orderStatus);
        order = save(order);
         eventPublisher.publishEvent(new OnOrderStatusChangedEvent(order.getOrderBilling().getClientEmail(), "Porosia juaj #" + order.getId() + " u kalua ne statusin [" + order.getOrderStatus() + "]", OrderStatusHTMLBuilder.buildOrderStatusHTML(order, adminNotesOnStatusChange)));
        return order;
    }
}
