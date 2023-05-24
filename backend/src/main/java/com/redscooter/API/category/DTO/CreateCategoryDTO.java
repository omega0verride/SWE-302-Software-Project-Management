package com.redscooter.API.category.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDTO {
    private Boolean visible;

    @NotNull(message = "Missing field: name")
    @NotBlank(message = "Category name cannot be blank!")
    @Size(min = 1, message = "Category name too short!")
    @Size(max = 50, message = "Category name too long!")
    private String name;

    @NotNull(message = "Missing field: defaultColor")
    @NotBlank(message = "defaultColor cannot be blank!")
    private String defaultColor;

    private String backgroundColor;

    @NotNull(message = "Missing field: textColor")
    @NotBlank(message = "textColor cannot be blank!")
    private String textColor;
}
