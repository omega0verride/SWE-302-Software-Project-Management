package com.redscooter.config.configProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.io.File;

@ConfigurationProperties(prefix = "file-store")
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Data
public class FileStoreConfigProperties {

    @NotEmpty
    private String privateRootPath;

    @NotEmpty
    private String publicRootPath;

    @Min(100)
    private Integer maxNumberOfSubDirectories = 30000;

    public void setPrivateRootPath(String privateRootPath) {
        this.privateRootPath = new File(privateRootPath).getAbsolutePath();
    }

    public void setPublicRootPath(String publicRootPath) {
        this.publicRootPath = new File(publicRootPath).getAbsolutePath();
    }
}
