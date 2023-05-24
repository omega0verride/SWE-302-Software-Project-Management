package com.redscooter.API.product.DTO;

import com.redscooter.API.common.AuditBaseDTO;
import com.redscooter.API.common.localFileStore.LocalImage;
import com.redscooter.API.product.Product;
import com.redscooter.API.product.ProductService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetModerateProductDTO extends AuditBaseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private int discount;
    private boolean used;
    private int stock;
    private String thumbnail;
    private String instagramPostURL;
    private String facebookPostURL;

    public GetModerateProductDTO(Product p, ProductService productService) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.discount = p.getDiscount();
        this.used = p.isUsed();
        this.stock = p.getStock();
        LocalImage thumbnail = productService.getProductThumbnail(p);
        this.thumbnail = thumbnail == null ? null : thumbnail.getRelativeFilePath();
        this.instagramPostURL = p.getInstagramPostURL();
        this.facebookPostURL = p.getFacebookPostURL();
        this.createdAt = p.getCreatedAt();
        this.updatedAt = p.getUpdatedAt();
    }
}
