package com.redscooter.API.product.DTO;

import com.redscooter.API.common.localFileStore.LocalImage;
import com.redscooter.API.product.Product;
import com.redscooter.API.product.ProductService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMinimalProductDTO {
    private Long id;
    private String title;
    private Double price;
    private Double range;
    private String thumbnail;

    public GetMinimalProductDTO(Product p, ProductService productService) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.range = p.getRange();
        LocalImage thumbnail = productService.getProductThumbnail(p);
        this.thumbnail = thumbnail == null ? null : thumbnail.getRelativeFilePath();
    }
}
