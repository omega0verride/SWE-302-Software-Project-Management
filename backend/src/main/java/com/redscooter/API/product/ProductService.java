package com.redscooter.API.product;

import com.redscooter.API.category.Category;
import com.redscooter.API.category.CategoryService;
import com.redscooter.API.common.BaseService;
import com.redscooter.API.common.localFileStore.LocalImage;
import com.redscooter.API.common.localFileStore.LocalImageStore;
import com.redscooter.API.product.DTO.UpdateProductDTO;
import com.redscooter.util.Utilities;
import org.restprocessors.DynamicQueryBuilder.DynamicFilterBuilder.CriteriaOperator.CriteriaOperator;
import org.restprocessors.DynamicQueryBuilder.DynamicFilterBuilder.Filters.Filter;
import org.restprocessors.DynamicQueryBuilder.DynamicFilterBuilder.Filters.FullTextSearchFilter;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.FunctionArg;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.LiteralFunctionArg;
import org.restprocessors.DynamicQueryBuilder.DynamicSortBuilder.MultiColumnSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product> {

    private final com.redscooter.API.product.ProductRepository ProductRepository;
    private final CategoryService categoryService;
    private final ProductImageStore productImageStore;

    @Autowired
    public ProductService(com.redscooter.API.product.ProductRepository ProductRepository, CategoryService categoryService, ProductImageStore productImageStore) {
        super(ProductRepository, "Product");
        this.ProductRepository = ProductRepository;
        this.categoryService = categoryService;
        this.productImageStore = productImageStore;
    }

    public Collection<Product> getAllByIds(List<Long> ids) {
        return ProductRepository.findAllById(ids);
    }

    public Page<Product> getAllByCriteria(boolean isVisibleRequired, String searchQuery, int page, int size, MultiColumnSort sortBy, List<Filter<?>> filters) {
        Hashtable<String, FunctionArg[]> arguments = new Hashtable<>();
        if (isVisibleRequired)
            filters.add(new Filter<>("visible", CriteriaOperator.EQUAL, true));
        if (Utilities.notNullOrEmpty(searchQuery)) {
            filters.add(new FullTextSearchFilter(searchQuery));
            arguments.put("searchBestMatch", new FunctionArg[]{new LiteralFunctionArg(0, searchQuery)});
        }
        return ProductRepository.findAllByCriteria(page, size, sortBy, filters, arguments);
    }

    public List<Product> addAllProducts(ArrayList<Product> products) {
        return ProductRepository.saveAll(products);
    }


    public Product updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        Product existingProduct = getById(id);
        if (updateProductDTO.getTitle() != null) existingProduct.setTitle(updateProductDTO.getTitle());
        if (updateProductDTO.getDescription() != null)
            existingProduct.setDescription(updateProductDTO.getDescription());
        if (updateProductDTO.getVisible() != null) existingProduct.setVisible(updateProductDTO.getVisible());
        if (updateProductDTO.getPrice() != null) existingProduct.setPrice(updateProductDTO.getPrice());
        if (updateProductDTO.getDiscount() != null) existingProduct.setDiscount(updateProductDTO.getDiscount());
        if (updateProductDTO.getUsed() != null) existingProduct.setUsed(updateProductDTO.getUsed());
        if (updateProductDTO.getStock() != null) existingProduct.setStock(updateProductDTO.getStock());
        if (updateProductDTO.getRange() != null) existingProduct.setRange(updateProductDTO.getRange());
        if (updateProductDTO.getCategories() != null)
            existingProduct.setCustomFields(updateProductDTO.getCustomFields());
        if (updateProductDTO.getInstagramPostURL() != null)
            existingProduct.setInstagramPostURL(updateProductDTO.getInstagramPostURL());
        if (updateProductDTO.getFacebookPostURL() != null)
            existingProduct.setFacebookPostURL(updateProductDTO.getFacebookPostURL());
        if (updateProductDTO.getCategories() != null)
            return setCategories(existingProduct, updateProductDTO.getCategories());
        return save(existingProduct);
    }


    public Product setCustomFields(Long id, Map<String, Object> customFields) {
        Product product = getById(id);
        product.setCustomFields(customFields);
        return save(product);
    }

    public Product setCustomFields(Long id, List<Map.Entry<String, Object>> customFields) {
        Map<String, Object> customFields_ = new HashMap<>();
        for (Map.Entry<String, Object> entry : customFields)
            customFields_.put(entry.getKey(), entry.getValue());
        return setCustomFields(id, customFields_);
    }

    public Product addCustomFields(Long id, Map<String, Object> customFields) {
        Product product = getById(id);
        product.addCustomFields(customFields);
        return save(product);
    }

    public Product removeCustomField(Long id, String key) {
        Product product = getById(id);
        product.removeCustomField(key);
        return save(product);
    }

    public Product removeCustomFields(Long id, Set<String> keys) {
        Product product = getById(id);
        product.removeCustomFields(keys);
        return save(product);
    }

    public Product setCategories(Product product, List<Long> categoriesIds) {
        return setCategories(product, categoriesIds, true);
    }

    public Product setCategories(Product product, List<Long> categoriesIds, boolean persist) {
        List<Category> categories = categoryService.getCategories(categoriesIds);
        product.setCategories(categories);
        if (persist)
            return save(product);
        return product;
    }

    public void addCategory(Long productId, Long categoryId) {
        Category category = categoryService.getById(categoryId);
        Product product = ProductRepository.findProductById(productId);
        product.addCategory(category);
        ProductRepository.save(product);
    }

    public Product addCategories(Product product, List<Long> categoryIds) {
        return addCategories(product, categoryIds, true);
    }

    public Product addCategories(Product product, List<Long> categoryIds, boolean persist) {
        for (Long id : categoryIds)
            product.addCategory(categoryService.getById(id));
        if (persist)
            return ProductRepository.save(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
        try {
            productImageStore.deleteAllImages(id);
        } catch (IOException ignored) {
        }
    }

    public List<LocalImage> getProductImages(Long productId) {
        return getProductImages(productId, true);
    }

    public List<LocalImage> getProductImages(Long productId, boolean includeThumbnail) {
        Product product = getById(productId);
        LocalImage thumbnail;
        if (!includeThumbnail) {
            if (product.getThumbnailFilename() != null) {
                thumbnail = productImageStore.getImage(product.getId(), product.getThumbnailFilename(), LocalImageStore.ImageNotFoundBehavior.NONE);
                if (thumbnail != null)
                    return productImageStore.getImages(productId).stream().filter(i -> (!i.getFileName().matches(thumbnail.getFileName()))).collect(Collectors.toList());
            }
        }
        return productImageStore.getImages(productId);
    }

    public List<LocalImage> getProductImages(Product product) {
        return getProductImages(product.getId());
    }

    public LocalImage getProductThumbnail(Product product) {
        return productImageStore.getImage(product.getId(), product.getThumbnailFilename(), LocalImageStore.ImageNotFoundBehavior.GET_FIRST_OR_DEFAULT);
    }


    public LocalImage uploadImage(MultipartFile file, Long productId_, boolean isThumbnail) throws IOException {
        Product product = getById(productId_);
        LocalImage savedFile;
        if (isThumbnail) {
            savedFile = productImageStore.saveImage(file, product.getId(), 0);
            productImageStore.deleteImageIfExists(productImageStore.getImageByFilename(product.getId(), product.getThumbnailFilename()));
            product.setThumbnailFilename(savedFile.getGeneratedUniqueFilename());
        } else {
            savedFile = productImageStore.saveImage(file, product.getId());
        }
        save(product);
        return savedFile;
    }

    public void deleteImage(String relativeImagePath) throws IOException {
        productImageStore.deleteImage(relativeImagePath);
    }
}
