package com.redscooter.API.product;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.localFileStore.LocalImage;
import com.redscooter.API.common.responseFactory.PageResponse;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.API.product.DTO.*;
import com.redscooter.exceptions.api.forbidden.ResourceRequiresAdminPrivileges;
import com.redscooter.security.AuthenticationFacade;
import com.redscooter.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "api")
public class ProductController {
    private final ProductService productService;

    private final AppUserService appUserService;

    private final JwtUtils jwtUtils;

    @Autowired
    public ProductController(ProductService productService, AppUserService appUserService, JwtUtils jwtUtils) {
        this.productService = productService;
        this.appUserService = appUserService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("public/products/{productId}")
    public GetProductDTO getProductByID(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @PathVariable(name = "productId", required = true) Long productId) {
//        Long productId_ = Utilities.tryParseLong(productId, "productId");
        Product product = productService.getById(productId);
        if (!product.isVisible() && !AuthenticationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            throw new ResourceRequiresAdminPrivileges("Product", "Id", productId.toString());
        return product.toGetProductDTO(productService);
    }


    @GetMapping("public/products")
    public ResponseEntity<PageResponse<GetModerateProductDTO>> getAllProducts(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestParam(name = "pageSize", defaultValue = "30") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) { // todo create decorator to set available filter ops
//        List<Filter<?>> filters = new ArrayList<>();
//        filters.addAll(FilterFactory.getLocalDateTimeFiltersFromRHSColonExpression("createdAt", createdAtFilters));
//        filters.addAll(FilterFactory.getLocalDateTimeFiltersFromRHSColonExpression("updatedAt", updatedAtFilters));
//        filters.addAll(FilterFactory.getNumericFiltersFromRHSColonExpression(Long.class, "id", productIdFilters));
//        filters.addAll(FilterFactory.getNumericFiltersFromRHSColonExpression(Long.class, "price", priceFilters));
//        filters.addAll(FilterFactory.getLongJoinFiltersFromRHSColonExpression(Product.class, Category.class, "id", categoryFilters));

        Page<Product> resultsPage = productService.getAllByCriteria(true, null, pageNumber, pageSize);
        return ResponseFactory.buildPageResponse(resultsPage, product -> new GetModerateProductDTO(product, productService));
    }

    @GetMapping("public/products/searchSuggestions")
    public ResponseEntity<PageResponse<GetMinimalProductDTO>> getProductsSearchSuggestion(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestParam(name = "pageSize", defaultValue = "30") int pageSize, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) { // todo create decorator to set available filter ops
//        List<Filter<?>> filters = new ArrayList<>();
//        filters.addAll(FilterFactory.getLocalDateTimeFiltersFromRHSColonExpression("createdAt", createdAtFilters));
//        filters.addAll(FilterFactory.getLocalDateTimeFiltersFromRHSColonExpression("updatedAt", updatedAtFilters));
//        filters.addAll(FilterFactory.getNumericFiltersFromRHSColonExpression(Long.class, "id", productIdFilters));
//        filters.addAll(FilterFactory.getNumericFiltersFromRHSColonExpression(Long.class, "price", priceFilters));
//        filters.addAll(FilterFactory.getLongJoinFiltersFromRHSColonExpression(Product.class, Category.class, "id", categoryFilters));

        Page<Product> resultsPage = productService.getAllByCriteria(true, null, pageNumber, pageSize);
        return ResponseFactory.buildPageResponse(resultsPage, product -> new GetMinimalProductDTO(product, productService));
    }


    @PostMapping("admin/products")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        Product product = new Product(createProductDTO);
        product = productService.addCategories(product, createProductDTO.getCategories()); // this will also save the new product
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("Product", "Id", product.getId());
    }


    @PatchMapping("admin/products/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "productId") Long productId, @Valid @RequestBody UpdateProductDTO updateProductDTO) {
        Product updatedProduct = productService.updateProduct(productId, updateProductDTO);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("admin/products/{productId}/setCustomFields")
    public ResponseEntity<Object> setCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Map<String, Object> customFields) {
        Product updatedProduct = productService.setCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("admin/products/{productId}/setCustomFieldsFromList")
    public ResponseEntity<Object> setCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody List<Map.Entry<String, Object>> customFields) {
        Product updatedProduct = productService.setCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("admin/products/{productId}/addCustomFields")
    public ResponseEntity<Object> addCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Map<String, Object> customFields) {
        Product updatedProduct = productService.addCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("admin/products/{productId}/removeCustomFields")
    public ResponseEntity<Object> removeCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Set<String> keys) {
        Product updatedProduct = productService.removeCustomFields(productId, keys);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("admin/products/{productId}/removeCustomField")
    public ResponseEntity<Object> removeCustomField(@PathVariable(name = "productId") Long productId, @RequestBody String key) {
        Product updatedProduct = productService.removeCustomField(productId, key);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }


    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.delete(productId);
        return ResponseFactory.buildResourceDeletedSuccessfullyResponse();
    }

    // THESE METHODS HANDLE IMAGE UPLOAD/DELETE/THUMBNAIL_CHANGE
    @PostMapping("admin/products/uploadImage/{productId}")
    public ResponseEntity<Object> uploadImage(@RequestParam(name = "file", required = true) MultipartFile file, @PathVariable(name = "productId", required = true) Long productId, @RequestParam(name = "isThumbnail", required = false, defaultValue = "false") boolean isThumbnail) throws IOException {
        LocalImage savedImage = productService.uploadImage(file, productId, isThumbnail);
        // TODO create response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successfully uploaded image! Uploaded image path: " + savedImage.getRelativeFilePath());
        response.put("filePath", savedImage.getRelativeFilePath());
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("admin/products/deleteImage")
    public ResponseEntity<Object> deleteImage(@RequestParam(name = "filePath", required = true) String filePath) throws IOException {

        productService.deleteImage(filePath);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successfully deleted image! Deleted image path: " + filePath);
        response.put("filePath", filePath);
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
// NOTE this code is commented out since it was not used on production, we are instead using a separate upload for thumbnail
// not fully tested
// lets keep this here in case of future changes
//    @PostMapping("admin/products/setThumbnail/{productId}")
//    public ResponseEntity<Object> setThumbnail(@RequestParam(name = "filePath", required = true) String filePath, @PathVariable(name = "productId", required = true) Long productId) {
//        Product product = productService.getProductById(productId);
//
//        Path filePath_ = Paths.get(filePath);
//        if (!filePath_.isAbsolute())
//            filePath_ = Utilities.joinPaths(Utilities.getStaticFolderPath_(), filePath); // should match the path exposed in WebMVCConfig#ResourceHandlerRegistry
//        File file = filePath_.toFile();
//
//        if (!file.exists())
//            throw new FileNotFoundException(filePath);
//
//        // make sure we normalize paths and validatePathPermissions in case the client sends a malicious path i.e: /../README.md
//        if (Utilities.validatePathPermissions(filePath_, Utilities.getProductImagesAbsolutePath()))
//            productService.setThumbnailFilename(product, filePath_);
//        else
//            throw new PermissionDeniedToAccessPathException(filePath_, filePath, Utilities.getProductImagesAbsolutePath(), Utilities.getProductImagesSubPath_(), PermissionDeniedToAccessPathException.Operation.DELETE);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Successfully set new thumbnail! New thumbnail path: " + filePath_);
//        response.put("filePath", filePath_);
//        response.put("success", true);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}