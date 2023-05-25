package com.redscooter.config;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.appUser.Role;
import com.redscooter.API.appUser.RoleService;
import com.redscooter.API.category.Category;
import com.redscooter.API.category.CategoryService;
import com.redscooter.API.product.Product;
import com.redscooter.API.product.ProductService;
import com.redscooter.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Bean
    CommandLineRunner createSampleData(CategoryService categoryService, ProductService productService) {
        return args -> {
            Category Skutera = new Category("skutera", null, null, null);
            try {
                categoryService.create(Skutera);
            } catch (Exception ignored) {
                Skutera = categoryService.getByName(Skutera.getName(), false);
            }
            Category Aksesore = new Category("aksesore", null, null, null);
            try {
                categoryService.create(Aksesore);
            } catch (Exception ignored) {
                Aksesore = categoryService.getByName(Aksesore.getName(), false);
            }

            List<Product> skutera = new ArrayList<>();
            List<Product> aksesore = new ArrayList<>();
            skutera.add(new Product("iPhone 11 Pro Max 256Gb Used", "Display Size: 6.5 inches\n" +
                    "RAM: 4GB\n" +
                    "Rear Camera: 12MP\n" +
                    "Front-facing Camera: 12MP\n" +
                    "Kapaciteti i baterisë: 3969 mAh\n" +
                    "Memoria: 256GB\n" +
                    "Garancia: 6 Muaj\n" +
                    "Procesori: Apple A13 Bionic (7 nm+)", 10, 72000.0, 0));
            skutera.add(new Product("iPhone 13 Pro Max 1T Used", "", 10, 150000.0, 0));
            skutera.add(new Product("Samsung Galaxy Note 8 64Gb Used", "", 10, 23000.0, 0));
            skutera.add(new Product("Samsung Galaxy Watch 4", "", 10, 27500.0, 0));
            skutera.add(new Product("Samsung Adapter 45W", "", 10, 3500.0, 0));
            skutera.add(new Product("Samsung Galaxy S22 Ultra 128Gb 5G NEW", "Display Size: 6.8 inches\n" +
                    "RAM: 8GB\n" +
                    "Rear Camera:108 MP\n" +
                    "Front-facing Camera: 40 MP\n" +
                    "Kapaciteti i baterisë: 5000mAh\n" +
                    "Memoria: 128GB\n" +
                    "Garancia: 12Muaj\n" +
                    "Procesori: Snapdragon,Exynos", 10, 113000.0, 0));
            skutera.add(new Product("Adaptor iPhone", "", 10, 1000.0, 0));

            aksesore.add(new Product("test test", "", 10, 1000.0, 0));

            for (Product p : aksesore) {
                productService.save(p);
                productService.addCategories(p, List.of(Aksesore.getId()));
            }

            for (Product p : skutera) {
                productService.save(p);
                productService.addCategories(p, List.of(Skutera.getId()));
            }
            Product p1 = new Product("asd", "test", 10, (double) new Random().nextInt(999), 0);
            productService.save(p1);
            Product a = new Product("a", "tëst asd", 10, (double) new Random().nextInt(999), 0);
            productService.save(a);
            Product b = new Product("bs", "test dfg", 10, (double) new Random().nextInt(999), 0);
            productService.save(b);
            Product c = new Product("asd", "test", 10, (double) new Random().nextInt(999), 0);
            productService.save(c);
            Product p11 = new Product("product11", "afasf asdfaf", 10, (double) new Random().nextInt(999), 0);
            productService.save(p11);
            Product p12 = new Product("product12", "teasfa asfst", 10, (double) new Random().nextInt(999), 0);
            productService.save(p12);
            Product p2 = new Product("product1", "test", 10, (double) new Random().nextInt(999), 0);
            productService.save(p2);

            productService.addCategory(p11.getId(), Skutera.getId());
            productService.addCategory(p12.getId(), Skutera.getId());
            productService.addCategory(a.getId(), Skutera.getId());
            productService.addCategory(a.getId(), Aksesore.getId());
        };
    }
}
