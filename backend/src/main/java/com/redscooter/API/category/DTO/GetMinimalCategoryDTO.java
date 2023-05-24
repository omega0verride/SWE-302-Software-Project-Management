package com.redscooter.API.category.DTO;

import com.redscooter.API.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GetMinimalCategoryDTO {
    private Long id;
    private String name;

    public GetMinimalCategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static GetMinimalCategoryDTO fromCategory(Category category) {
        return new GetMinimalCategoryDTO(category);
    }
}
