package com.redscooter.API.product.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    List<Long> categories;
    @NotNull(message = "Missing field: title")
    @NotBlank(message = "title cannot be blank!")
    private String title;
    @NotNull(message = "Missing field: description")
    @NotBlank(message = "description cannot be blank!")
    private String description;
    private Boolean visible = null;
    @NotNull(message = "Missing field: price")
    private Double price;
    private Double range;
    private Integer discount;
    private Boolean used = null; // if set to true the product is marked as "used" otherwise it is "new"
    private Integer stock;
    private Map<String, Object> customFields;
    private String instagramPostURL;
    private String facebookPostURL;
}
