package com.redscooter.API.OrderLine;

import com.redscooter.API.OrderLine.DTO.CreateOrderLineDTO;
import com.redscooter.API.common.BaseService;
import com.redscooter.API.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderLineService extends BaseService<OrderLine> {
    private final OrderLineRepository orderLineRepository;
    private final ProductService productService;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository, ProductService productService) {
        super(orderLineRepository, "OrderLine");
        this.orderLineRepository = orderLineRepository;
        this.productService = productService;
    }


    public List<OrderLine> create(List<CreateOrderLineDTO> createOrderLineDTOs, boolean persist) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (CreateOrderLineDTO dto : createOrderLineDTOs) {
            orderLines.add(new OrderLine(productService.getById(dto.getProductId()), dto.getQuantity()));
        }
        if (persist)
            orderLines.replaceAll(this::save);
        return orderLines;
    }
}
