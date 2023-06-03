package com.redscooter.API.order.DTO;

import com.redscooter.API.order.PaymentOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderBillingDTO {
    @NotEmpty
    @NotNull
    private String clientName;

    @NotEmpty
    @NotNull
    private String clientSurname;

    @NotEmpty
    @NotNull
    private String clientPhoneNumber;

    @NotEmpty
    @NotNull
    private String clientEmail;

    @NotEmpty
    @NotNull
    private String clientAddressLine1;

    private String clientNotes;

    @NotNull
    private PaymentOption paymentOption;
}
