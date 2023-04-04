package com.redscooter.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicCredentialsDTO {
    String username;
    String password;
}
