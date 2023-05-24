package com.redscooter.API.category;

import com.redscooter.API.category.DTO.CreateCategoryDTO;
import com.redscooter.API.category.DTO.GetCategoryDTO;
import com.redscooter.API.category.DTO.GetMinimalCategoryDTO;
import com.redscooter.API.common.AuditData;
import com.redscooter.API.common.Auditable;
import com.redscooter.API.product.Product;
import com.redscooter.util.DynamicQueryBuilder.DynamicSortBuilder.annotations.EmbeddedSortableFields;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "categories")
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Auditable {

    @Embedded
    @EmbeddedSortableFields(embeddedClass = AuditData.class)
    AuditData auditData = new AuditData();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    private boolean visible = true;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(unique = true, nullable = false)
    private String nameLowerCased;

    private String defaultColor;
    private String backgroundColor;
    private String textColor;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST},
            mappedBy = "categories")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public Category(String name, String defaultColor, String backgroundColor, String textColor) {
        setName(name);
        setDefaultColor(defaultColor);
        setBackgroundColor(backgroundColor);
        setTextColor(textColor);
    }

    public Category(CreateCategoryDTO createCategoryDTO) {
        // no need to check for null values, these are all mandatory fields, the DTO will be marked invalid otherwise
        this(createCategoryDTO.getName(), createCategoryDTO.getDefaultColor(), createCategoryDTO.getBackgroundColor(), createCategoryDTO.getTextColor());
        if (createCategoryDTO.getVisible() != null) setVisible(createCategoryDTO.getVisible());
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        this.nameLowerCased = name == null ? null : name.toLowerCase();
    }

    public static GetCategoryDTO toGetCategoryDTO(Category category) {
        return new GetCategoryDTO(category);
    }

    public static GetMinimalCategoryDTO toGetMinimalCategoryDTO(Category category) {
        return new GetMinimalCategoryDTO(category);
    }

    public GetCategoryDTO toGetCategoryDTO() {
        return new GetCategoryDTO(this);
    }

    public GetMinimalCategoryDTO toGetMinimalCategoryDTO() {
        return new GetMinimalCategoryDTO(this);
    }

    @Override
    public long getCreatedAt() {
        return auditData.getCreatedAt();
    }

    @Override
    public long getUpdatedAt() {
        return auditData.getUpdatedAt();
    }
}
