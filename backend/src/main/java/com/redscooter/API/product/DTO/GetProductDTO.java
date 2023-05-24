package com.redscooter.API.product.DTO;

import com.redscooter.API.category.DTO.GetMinimalCategoryDTO;
import com.redscooter.API.common.localFileStore.FileDTO;
import com.redscooter.API.common.localFileStore.LocalFile;
import com.redscooter.API.product.Product;
import com.redscooter.API.product.ProductService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class GetProductDTO extends GetModerateProductDTO {
    boolean visible;

    private Map<String, Object> customFields;
    private List<GetMinimalCategoryDTO> categories;
    private List<FileDTO> images;

    public GetProductDTO(Product p, ProductService productService) {
        super(p, productService);
        this.visible = p.isVisible();
        this.customFields =p.getCustomFields();
        this.categories = p.getCategories().stream().map(c -> c.toGetMinimalCategoryDTO()).collect(Collectors.toList());
        this.images = productService.getProductImages(p.getId()).stream().map(LocalFile::toFileDTO).toList();
    }
}
