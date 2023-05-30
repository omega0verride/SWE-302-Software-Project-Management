package com.redscooter.config.configProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "FileStore")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileStoreConfigProperties {

    private String rootDirectory;

    private Long maxAge = null;
}
