package com.redscooter.API.order;

import com.redscooter.API.OrderLine.OrderLine;
import com.redscooter.API.common.AuditData;
import com.redscooter.API.common.Auditable;
import com.redscooter.API.order.DTO.GetOrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.restprocessors.JoinRESTField;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@Repository
public class Order implements Auditable {
    @Embedded
    AuditData auditData = new AuditData();

    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Column(name = "order_id", nullable = false)
    private Long id;

    private OrderStatus orderStatus = OrderStatus.NEW;

    @Nullable
    private Long userId; // a reference to the user if the order was made by a logged-in user


    @JoinRESTField(joinClass = OrderLine.class)
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "order_orderlines",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "orderline_id",
                    foreignKey = @ForeignKey(
                            name = "FK_orderline_id",
                            foreignKeyDefinition = "FOREIGN KEY (orderline_id) REFERENCES orderlines(orderline_id)"
                    ))})
    private List<OrderLine> orderLines = new ArrayList<>();

    @Transient
    public Double calculateTotal() {
        return orderLines.stream().map(ol -> {
            return ol.getPrice() * ol.getQuantity();
        }).reduce(Double::sum).orElse(0d);
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void addOrderLines(List<OrderLine> orderLines) {
        this.orderLines.addAll(orderLines);
    }

    @NotNull
    @Embedded
    private OrderBilling OrderBilling;

    // important note, we will directly add the attributes of user and product here
    // this may seem redundant but is important in order to save the details in case the admin deletes/edits a product
//    private List<Product> products_ = new ArrayList<>();

    public GetOrderDTO toGetOrderDTO() {
        return new GetOrderDTO(this);
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
