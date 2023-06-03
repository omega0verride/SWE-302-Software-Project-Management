package com.redscooter.API.category;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.API.category.DTO.CreateCategoryDTO;
import com.redscooter.API.category.DTO.GetCategoryDTO;
import com.redscooter.API.category.DTO.UpdateCategoryDTO;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.exceptions.api.forbidden.ResourceRequiresAdminPrivileges;
import com.redscooter.security.AuthorizationFacade;
import com.redscooter.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final JwtUtils jwtUtils;

    private final AppUserService appUserService;

    @Autowired
    public CategoryController(CategoryService categoryService, JwtUtils jwtUtils, AppUserService appUserService) {
        this.categoryService = categoryService;
        this.jwtUtils = jwtUtils;
        this.appUserService = appUserService;
    }

    @GetMapping("")
    public List<GetCategoryDTO> getAllCategories(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        List<Category> categories;
        if (!AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            categories = categoryService.getCategoriesIfVisible();
        else
            categories = categoryService.getCategories();
        return categories.stream().map(c -> c.toGetCategoryDTO()).collect(Collectors.toList());
    }

    @GetMapping("/{categoryId}")
    public GetCategoryDTO getCategoryById(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @PathVariable(name = "categoryId", required = true) Long categoryId) {
        Category category = categoryService.getById(categoryId);
        if (!category.isVisible() && !AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            throw new ResourceRequiresAdminPrivileges("Category", "Id", categoryId.toString());
        return category.toGetCategoryDTO();
    }

    @PostMapping("")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        AuthorizationFacade.ensureAdmin();
        Category insertedCategory = categoryService.create(createCategoryDTO);
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("Category", "categoryId", insertedCategory.getId());
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable(name = "categoryId", required = true) Long categoryId, @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        AuthorizationFacade.ensureAdmin();
        Category updatedCategory = categoryService.update(categoryId, updateCategoryDTO);
        return ResponseFactory.buildResourceUpdatedSuccessfullyResponse("Category", "categoryId", categoryId, updatedCategory.toGetCategoryDTO());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(name = "categoryId", required = true) Long categoryId) {
        AuthorizationFacade.ensureAdmin();
        categoryService.delete(categoryId);
        return ResponseFactory.buildResourceDeletedSuccessfullyResponse();
    }
    // TODO [0]: if we have time we can add an endpoint for batch deletes

}
