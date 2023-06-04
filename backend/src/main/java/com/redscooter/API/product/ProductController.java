package com.redscooter.API.product;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.common.localFileStore.LocalImage;
import com.redscooter.API.common.responseFactory.PageResponse;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.API.product.DTO.*;
import com.redscooter.exceptions.api.forbidden.ResourceRequiresAdminPrivileges;
import com.redscooter.security.AuthorizationFacade;
import com.redscooter.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.PathFunctionArg;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.SortByFunction;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.SortOrder;
import org.restprocessors.DynamicRESTController.CriteriaParameters;
import org.restprocessors.DynamicRestMapping;
import org.restprocessors.FieldDetailsRegistry;
import org.restprocessors.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    private final AppUserService appUserService;

    private final JwtUtils jwtUtils;

    @Autowired
    public ProductController(ProductService productService, AppUserService appUserService, JwtUtils jwtUtils) {
        this.productService = productService;
        this.appUserService = appUserService;
        this.jwtUtils = jwtUtils;
        FieldDetailsRegistry.instance().bindField(Product.class, new SortByFunction<Float>("custom_ts_rank", Float.class, "searchBestMatch", 1, SortOrder.DESC));
        FieldDetailsRegistry.instance().bindField(Product.class, new SortByFunction<Float>("length", Long.class, "descriptionLength", 1, SortOrder.ASC, new PathFunctionArg(0, "description")));
    }

    @GetMapping("/{productId}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public GetProductDTO getProductByID(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @PathVariable(name = "productId", required = true) Long productId) {
        Product product = productService.getById(productId);
        if (!product.isVisible() && !AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            throw new ResourceRequiresAdminPrivileges("Product", "Id", productId.toString());
        return product.toGetProductDTO(productService);
    }

    @DynamicRestMapping(path = "", requestMethod = RequestMethod.GET, entity = Product.class)
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PageResponse<GetModerateProductDTO>> getAllProducts(CriteriaParameters cp, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestParam(name = "searchQuery", required = false) String searchQuery) {
        Page<Product> resultsPage = productService.getAllByCriteria(!AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService), searchQuery, cp);
        return ResponseFactory.buildPageResponse(resultsPage, product -> new GetModerateProductDTO(product, productService));
    }

    @DynamicRestMapping(path = "/searchSuggestions", requestMethod = RequestMethod.GET, entity = Product.class)
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PageResponse<GetMinimalProductDTO>> getProductsSearchSuggestion(CriteriaParameters cp, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestParam(name = "searchQuery", required = false) String searchQuery) {
        Page<Product> resultsPage = productService.getAllByCriteria(!AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService), searchQuery, cp);
        return ResponseFactory.buildPageResponse(resultsPage, product -> new GetMinimalProductDTO(product, productService));
    }

    @PostMapping("")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        AuthorizationFacade.ensureAdmin();
        Product product = new Product(createProductDTO);
        product = productService.addCategories(product, createProductDTO.getCategories()); // this will also save the new product
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("Product", "Id", product.getId());
    }

    @PatchMapping("/{productId}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "productId") Long productId, @Valid @RequestBody UpdateProductDTO updateProductDTO) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.updateProduct(productId, updateProductDTO);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("/{productId}/setCustomFields")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> setCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Map<String, Object> customFields) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.setCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("/{productId}/setCustomFieldsFromList")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> setCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody List<Map.Entry<String, Object>> customFields) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.setCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("/{productId}/addCustomFields")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> addCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Map<String, Object> customFields) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.addCustomFields(productId, customFields);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("/{productId}/removeCustomFields")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> removeCustomFields(@PathVariable(name = "productId") Long productId, @RequestBody Set<String> keys) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.removeCustomFields(productId, keys);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @PatchMapping("/{productId}/removeCustomField")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> removeCustomField(@PathVariable(name = "productId") Long productId, @RequestBody String key) {
        AuthorizationFacade.ensureAdmin();
        Product updatedProduct = productService.removeCustomField(productId, key);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Product", "productId", productId, updatedProduct.toGetProductDTO(productService));
    }

    @DeleteMapping("/{productId}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> deleteProduct(@PathVariable(name = "productId") Long productId) {
        AuthorizationFacade.ensureAdmin();
        productService.delete(productId);
        return ResponseFactory.buildResourceDeletedSuccessfullyResponse();
    }

    // THESE METHODS HANDLE IMAGE UPLOAD/DELETE/THUMBNAIL_CHANGE
    @PostMapping(path = "/uploadImage/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> uploadImage(@RequestParam(name = "file", required = true) MultipartFile file, @PathVariable(name = "productId", required = true) Long productId, @RequestParam(name = "isThumbnail", required = false, defaultValue = "false") boolean isThumbnail) throws IOException {
        AuthorizationFacade.ensureAdmin();
        LocalImage savedImage = productService.uploadImage(file, productId, isThumbnail);
        // TODO create response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successfully uploaded image! Uploaded image path: " + savedImage.getRelativeFilePath());
        response.put("filePath", savedImage.getRelativeFilePath());
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteImage")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> deleteImage(@RequestParam(name = "filePath", required = true) String filePath) throws IOException {
        AuthorizationFacade.ensureAdmin();
        productService.deleteImage(filePath);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successfully deleted image! Deleted image path: " + filePath);
        response.put("filePath", filePath);
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}