package com.redscooter.API.product.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO {
    List<Long> categories;
    @NotBlank(message = "title cannot be blank!")
    private String title;
    @NotBlank(message = "description cannot be blank!")
    private String description;
    private Boolean visible = null;
    private Double price;
    private Integer discount;
    private Boolean used = null; // if set to true the product is marked as "used" otherwise it is "new"
    private Integer stock;
    private Map<String, Object> customFields;
    private String instagramPostURL;
    private String facebookPostURL;
}
