package com.redscooter.API.category.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {
    private Boolean visible;

    @NotBlank(message = "Category name cannot be blank!")
    @Size(min = 1, message = "Category name too short!")
    @Size(max = 50, message = "Category name too long!")
    private String name;

    @Nullable
    @NotBlank(message = "defaultColor cannot be blank!")
    private String defaultColor;

    private String backgroundColor;

    @Nullable
    @NotBlank(message = "textColor cannot be blank!")
    private String textColor;


}
