package com.redscooter.config;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.appUser.Role;
import com.redscooter.API.appUser.RoleService;
import com.redscooter.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {

    private final RoleService roleService;
    private final AppUserService appUserService;

    @Getter
    private static Role USER_ROLE = new Role(AuthenticationFacade.USER_AUTHORITY.getAuthority());
    @Getter
    private static Role ADMIN_ROLE = new Role(AuthenticationFacade.ADMIN_AUTHORITY.getAuthority());
    @Getter
    private static AppUser ADMIN_USER = new AppUser("admin", "admin", "admin", "admin", "admin@gmail.com", "", new ArrayList<>());

    @Bean
    CommandLineRunner rolesConfig() {
        return args -> {
               if (roleService.existsByName(USER_ROLE.getName()))
                USER_ROLE = roleService.getByName(USER_ROLE.getName());
            else
                USER_ROLE = roleService.save(USER_ROLE);

            if (roleService.existsByName(ADMIN_ROLE.getName()))
                ADMIN_ROLE = roleService.getByName(ADMIN_ROLE.getName());
            else
                ADMIN_ROLE = roleService.save(ADMIN_ROLE);

            ADMIN_USER.setRoles(List.of(ADMIN_ROLE));
            ADMIN_USER.setEnabled(true);
            ADMIN_USER = appUserService.saveUser(ADMIN_USER);
        };
    }
}
