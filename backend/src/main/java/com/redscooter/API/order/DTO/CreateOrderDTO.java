package com.redscooter.API.order.DTO;

import com.redscooter.API.OrderLine.DTO.CreateOrderLineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    @NotEmpty
    @Valid
    private List<CreateOrderLineDTO> orderLines;

    @Valid
    private CreateOrderBillingDTO orderBilling;

    private Long userId; // optional
}
