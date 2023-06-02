package com.redscooter.API.appUser.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppUserDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private Boolean isAdmin;
    private Boolean isEnabled;
}
