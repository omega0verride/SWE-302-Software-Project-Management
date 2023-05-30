package com.redscooter.API.product;

import com.redscooter.API.common.localFileStore.LocalImageStore;
import com.redscooter.config.configProperties.FileStoreConfigProperties;
import com.redscooter.util.Utilities;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductImageStore extends LocalImageStore {
    public ProductImageStore(FileStoreConfigProperties fileStoreConfigProperties) {
        super(Utilities.joinPathsAsString("products", "images"), fileStoreConfigProperties);
    }
}
