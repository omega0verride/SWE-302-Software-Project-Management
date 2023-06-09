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

    public GetCategoryDTO(Category category) {
        this.id = category.getId();
        this.visible = category.isVisible();
        this.name = category.getName();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }

    public static GetCategoryDTO fromCategory(Category category) {
        return new GetCategoryDTO(category);
    }
}
