package com.redscooter.API.category.DTO;

import com.redscooter.API.category.Category;
import com.redscooter.API.common.AuditBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryDTO extends AuditBaseDTO {
    private Long id;
    private boolean visible;
    private String name;
    private String defaultColor;
    private String backgroundColor;
    private String textColor;

    public GetCategoryDTO(Category category) {
        this.id = category.getId();
        this.visible = category.isVisible();
        this.name = category.getName();
        this.defaultColor = category.getDefaultColor();
        this.backgroundColor = category.getBackgroundColor();
        this.textColor = category.getTextColor();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }

    public static GetCategoryDTO fromCategory(Category category) {
        return new GetCategoryDTO(category);
    }
}
