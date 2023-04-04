package com.redscooter.API.appUser.DTO;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.common.AuditBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAppUserDTO extends AuditBaseDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean enabled;

    public GetAppUserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.name = appUser.getName();
        this.surname = appUser.getSurname();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.phoneNumber = appUser.getPhoneNumber();
        this.enabled = appUser.isEnabled();
        this.createdAt = appUser.getCreatedAt();
        this.updatedAt = appUser.getUpdatedAt();
    }

    public static GetAppUserDTO fromAppUser(AppUser appUser) {
        return new GetAppUserDTO(appUser);
    }
}
