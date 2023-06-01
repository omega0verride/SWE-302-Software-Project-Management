package com.redscooter.API.order;

import com.redscooter.API.common.confirmationToken.ConfirmationToken;
import com.redscooter.API.common.confirmationToken.InMemoryConfirmationTokenStore;
import com.redscooter.API.common.responseFactory.PageResponse;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.API.order.DTO.CreateOrderDTO;
import com.redscooter.API.order.DTO.GetOrderDTO;
import com.redscooter.API.order.DTO.OrderConfirmationTokenDTO;
import jakarta.validation.Valid;
import org.restprocessors.DynamicRESTController.CriteriaParameters;
import org.restprocessors.DynamicRestMapping;
import org.restprocessors.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api")
public class OrderController extends com.redscooter.API.order.OrderControllerBase {
    private final OrderService orderService;
    private final InMemoryConfirmationTokenStore<Order> inMemoryConfirmationTokenStore;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        inMemoryConfirmationTokenStore = new InMemoryConfirmationTokenStore<Order>(150);
    }

    @GetMapping("orders/{orderId}")
    public GetOrderDTO getOrderByID(@PathVariable(name = "orderId", required = true) Long orderId) {
        Order order = orderService.getById(orderId);
        return order.toGetOrderDTO();
    }


    @DynamicRestMapping(path = "orders", requestMethod = RequestMethod.GET, entity = Order.class)
//    @Parameters({@Parameter(name = "createdAt_", in = ParameterIn.QUERY, required = false, example = "propertyName:<asc|desc>"), @Parameter(name = "sortBy", in = ParameterIn.QUERY, required = false, schema = @Schema(description = "var 1", type = "string", allowableValues = {"1", "2"}))})
//    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Page<GetOrderDTO>.))})
    public ResponseEntity<PageResponse<GetOrderDTO>> getAllOrders(CriteriaParameters cp) {
        Page<Order> resultsPage = orderService.getAllByCriteria(cp);
        return ResponseFactory.buildPageResponse(resultsPage, GetOrderDTO::new);
    }

    @PostMapping("orders")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO, @RequestParam(name = "skipVerification", defaultValue = "false") boolean skipVerification) {
        Order order = orderService.create(createOrderDTO, skipVerification);
        if (skipVerification)
            return ResponseFactory.buildResourceCreatedSuccessfullyResponse("Order", "Id", order.getId());
        ConfirmationToken<Order> verificationToken = inMemoryConfirmationTokenStore.generateNewToken(order);
        return new ResponseEntity<>(new OrderConfirmationTokenDTO(verificationToken), HttpStatus.OK);
    }

    @PatchMapping("orders/confirm/{orderConfirmationToken}")
    public ResponseEntity<Object> confirmOrder(@PathVariable(name = "orderConfirmationToken", required = true) String orderConfirmationToken) {
        Order orderToBeConfirmed = inMemoryConfirmationTokenStore.getObjectAndRemoveToken(orderConfirmationToken);
        Order order = orderService.confirmOrder(orderToBeConfirmed);
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("Order", "Id", order.getId());
    }

    @PatchMapping("orders/{orderId}/changeStatus/{orderStatus}")
    public ResponseEntity<Object> changeOrderStatus(@PathVariable(name = "orderId", required = true) Long orderId, @PathVariable(name = "orderStatus", required = true) OrderStatus orderStatus, @RequestParam(name = "adminNotesOnStatusChange", required = false) String adminNotesOnStatusChange) {
        Order updatedOrder = orderService.changeOrderStatus(orderId, orderStatus, adminNotesOnStatusChange);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Order", "orderId", orderId, updatedOrder.toGetOrderDTO());
    }
}