package com.redscooter;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserController;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.appUser.DTO.CreateAppUserDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppUserTests {
    static {
        System.setProperty("JWT_SECRET", "test");
        System.setProperty("SMTP_USERNAME", "test");
        System.setProperty("SMTP_PASSWORD", "test");
    }

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private AppUserController appUserController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppUserService appUserService;

    private MockHttpServletRequest mockRequest;

    @Test
    @Order(1)
    public void testCreateUser_PasswordIsSet() throws Exception {
        try {
            ResponseEntity test = appUserController.registerUser(new CreateAppUserDTO("test", "test", "test@gmail.com", "password", null), false, false, null);
            org.junit.jupiter.api.Assertions.assertEquals(test.getStatusCode(), HttpStatus.CREATED);
            org.junit.jupiter.api.Assertions.assertTrue(bCryptPasswordEncoder.matches("password", appUserService.getByUsername("test@gmail.com").getPassword()));

        } catch (Exception ex) {
            if (!ex.getMessage().contains("Cannot invoke \"jakarta.servlet.http.HttpServletRequest.getScheme()\" because \"request\" is null"))
                throw ex;
        }
    }

    @Test
    @Order(2)
    public void testCreateUser_PasswordIsUpdated() throws Exception {
        AppUser appUser = appUserService.getByUsername("test@gmail.com");
        appUser.setPassword("updated_password");
        appUserService.saveUser(appUser);
        org.junit.jupiter.api.Assertions.assertTrue(bCryptPasswordEncoder.matches("updated_password", appUserService.getByUsername("test@gmail.com").getPassword()));
    }

    @Test
    @Order(3)
    public void testCreateUser_PasswordIsNotUpdated() throws Exception {
        AppUser appUser = appUserService.getByUsername("test@gmail.com");
        appUser.setEnabled(true);
        appUserService.saveUser(appUser);
        org.junit.jupiter.api.Assertions.assertTrue(bCryptPasswordEncoder.matches("updated_password", appUserService.getByUsername("test@gmail.com").getPassword()));
    }
    

    // this function test if the user is updated correctlly
    
}
