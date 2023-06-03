package com.redscooter.API.order.DTO;

import com.redscooter.API.OrderLine.DTO.GetOrderLineDTO;
import com.redscooter.API.OrderLine.OrderLine;
import com.redscooter.API.common.AuditBaseDTO;
import com.redscooter.API.order.Order;
import com.redscooter.API.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class GetOrderDTO extends AuditBaseDTO {
    private Long id;
    private OrderStatus orderStatus;
    private List<GetOrderLineDTO> orderLines;

    private GetOrderBillingDTO orderBilling;

    public GetOrderDTO(Order o) {
        id = o.getId();
        orderStatus = o.getOrderStatus();
        orderLines = o.getOrderLines().stream().map(ol -> OrderLine.toGetOrderLineDTO(ol)).collect(Collectors.toList());
        orderBilling = o.getOrderBilling().toGetOrderBillingDTO();

        this.createdAt = o.getCreatedAt();
        this.updatedAt = o.getUpdatedAt();
    }
}
