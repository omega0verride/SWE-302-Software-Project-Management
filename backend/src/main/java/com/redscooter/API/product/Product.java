package com.redscooter.API.product;

import com.redscooter.API.category.Category;
import com.redscooter.API.common.AuditData;
import com.redscooter.API.common.Auditable;
import com.redscooter.API.product.DTO.CreateProductDTO;
import com.redscooter.API.product.DTO.GetModerateProductDTO;
import com.redscooter.API.product.DTO.GetProductDTO;
import com.redscooter.util.DynamicQueryBuilder.DynamicSortBuilder.annotations.EmbeddedSortableFields;
import com.redscooter.util.DynamicQueryBuilder.DynamicSortBuilder.annotations.SortableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Repository;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.*;
import java.util.*;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@Repository
public class Product extends ProductBase implements Auditable {
    @Embedded
    @EmbeddedSortableFields(embeddedClass = AuditData.class)
    AuditData auditData = new AuditData();


    public static final int MAX_NUMBER_OF_SUB_DIRECTORIES = 30000;

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @Column(name = "product_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Include // limit the hash generator to PK
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(length = 2048)
    private String instagramPostURL;
    @Column(length = 2048)
    private String facebookPostURL;
    @JsonIgnore
    @Column(length = 2048)
    private String thumbnailFilename;

    @SortableField
    private boolean visible = true;

    @SortableField
    private Integer stock = 0;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> customFields = new HashMap<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "product_categories",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id",
                    foreignKey = @ForeignKey(
                            name = "FK_category_id",
                            foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(category_id) ON UPDATE CASCADE ON DELETE CASCADE"
                    ))})
    private List<Category> categories = new ArrayList<>();

    public Product(String title, String description, int stock, Double price, int discount, String instagramPostURL, String facebookPostURL) {
        setTitle(title);
        setDescription(description);
        setStock(stock);
        setPrice(price);
        setDiscount(discount);
    }

    public Product(String title, String description, int stock, Double price, int discount) {
        this(title, description, stock, price, discount, null, null);
    }

    public Product(CreateProductDTO productDTO) {
        if (productDTO.getTitle() != null) setTitle(productDTO.getTitle());
        if (productDTO.getPrice() != null) setPrice(productDTO.getPrice());
        if (productDTO.getDescription() != null) setDescription(productDTO.getDescription());
        if (productDTO.getVisible() != null) setVisible(productDTO.getVisible());

        if (productDTO.getDiscount() != null) setDiscount(productDTO.getDiscount());
        if (productDTO.getStock() != null) setStock(productDTO.getStock());
        if (productDTO.getCustomFields() != null) setCustomFields(productDTO.getCustomFields());
        if (productDTO.getUsed() != null) setUsed(productDTO.getUsed());
        if (productDTO.getInstagramPostURL() != null) setInstagramPostURL(productDTO.getInstagramPostURL());
        if (productDTO.getFacebookPostURL() != null) setFacebookPostURL(productDTO.getFacebookPostURL());
    }

    public static Product fromCreateProductDTO(CreateProductDTO createProductDTO) {
        return new Product(createProductDTO);
    }

    public static GetProductDTO toGetProductDTO(Product product, ProductService productService) {
        return new GetProductDTO(product, productService);
    }

    public static GetModerateProductDTO toGetModerateProductDTO(Product product, ProductService productService) {
        return new GetModerateProductDTO(product, productService);
    }

    public GetProductDTO toGetProductDTO(ProductService productService) {
        return new GetProductDTO(this, productService);
    }

    public GetModerateProductDTO toGetModerateProductDTO(ProductService productService) {
        return new GetModerateProductDTO(this, productService);
    }
    public void addCustomFields(Map<String, Object> customFields) {
        if (this.customFields==null)
            this.customFields=new HashMap<>();
        if (customFields!=null)
            this.customFields.putAll(customFields);
    }

    public void removeCustomField(String key) {
        if (this.customFields==null)
            return;
        if (key!=null)
            this.customFields.remove(key);
    }

    public void removeCustomFields(Set<String> keys) {
        if (this.customFields==null)
            return;
        for (String key:keys)
            this.customFields.remove(key);
    }

    public void addCategory(Category category) {
        this.categories.add(category); // make sure to pass object instead of referencing self to avoid a concurrency issue
    }

    public void removeCategory(long categoryId) {
        Category category = this.categories.stream().filter(t -> t.getId() == categoryId).findFirst().orElse(null);
        if (category != null) {
            this.categories.remove(category);
            category.getProducts().remove(this);
        }
    }

    public boolean isVisible() {
        if (visible)
            return true;
        return categories.size() == 0 || categories.stream().anyMatch(Category::isVisible);
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
