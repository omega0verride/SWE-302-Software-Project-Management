package com.redscooter.API.OrderLine.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderLineDTO {
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
