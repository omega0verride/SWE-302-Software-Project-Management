package com.redscooter.API.OrderLine.DTO;

import com.redscooter.API.OrderLine.OrderLine;
import com.redscooter.API.common.AuditBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderLineDTO extends AuditBaseDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private String title;
    private String description;
    private Double price;
    private int discount;
    private boolean used;

    public GetOrderLineDTO(OrderLine orderLine) {
        this.id = orderLine.getId();
        this.productId = orderLine.getProductId();
        this.quantity = orderLine.getQuantity();
        this.discount = orderLine.getDiscount();
        this.price = orderLine.getPrice();
        this.description = orderLine.getDescription();
        this.title = orderLine.getTitle();
        this.used = orderLine.isUsed();
    }

    public static GetOrderLineDTO fromCategory(OrderLine orderLine) {
        return new GetOrderLineDTO(orderLine);
    }

}
