package com.redscooter.API.OrderLine;

import com.redscooter.API.OrderLine.DTO.GetOrderLineDTO;
import com.redscooter.API.common.AuditData;
import com.redscooter.API.common.Auditable;
import com.redscooter.API.product.Product;
import com.redscooter.API.product.ProductBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Table(name = "orderlines")
@Entity(name = "orderlines")
@NoArgsConstructor
@Repository
public class OrderLine extends ProductBase implements Auditable {
    @Embedded
    AuditData auditData = new AuditData();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderline_id")
    private Long id;
    private Long productId;
    private Integer quantity = 1;

    public OrderLine(Product p, int quantity) {
        super(p.getTitle(), p.getDescription(), p.getPrice(), p.getDiscount(), p.isUsed(), p.getRange());
        setProductId(p.getId());
        setQuantity(quantity);
    }

    public GetOrderLineDTO toGetOrderLineDTO() {
        return toGetOrderLineDTO(this);
    }

    public static GetOrderLineDTO toGetOrderLineDTO(OrderLine orderLine) {
        return new GetOrderLineDTO(orderLine);
    }

    @Override
    public long getCreatedAt() {
        return auditData.getCreatedAt();
    }

    @Override
    public long getUpdatedAt() {
        return auditData.getUpdatedAt();
    }
}
