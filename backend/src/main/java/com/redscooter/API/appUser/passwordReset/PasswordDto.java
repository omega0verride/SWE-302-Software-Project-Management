package com.redscooter.API.appUser.passwordReset;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotEmpty
    @NotNull
    private String username;
    private String oldPassword;
    private String token;
    @NotEmpty
    @NotNull
    private String newPassword;
}