package com.redscooter;

import static org.assertj.core.api.Assertions.assertThat;

import com.redscooter.API.product.ProductController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    static {
        System.setProperty("JWT_SECRET", "test");
        System.setProperty("SMTP_USERNAME", "test");
        System.setProperty("SMTP_PASSWORD", "test");
    }
    @Autowired
    private ProductController controller;

//
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}