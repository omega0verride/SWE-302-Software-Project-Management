package com.redscooter.API.product;

import com.redscooter.API.common.localFileStore.LocalImageStore;
import com.redscooter.util.Utilities;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductImageStore extends LocalImageStore {
    public ProductImageStore() {
        super(Utilities.getResourcesPath_(), Utilities.joinPathsAsString("public", "products", "images"));
    }
}
