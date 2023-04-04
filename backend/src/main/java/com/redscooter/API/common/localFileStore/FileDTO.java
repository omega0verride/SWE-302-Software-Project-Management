package com.redscooter.API.common.localFileStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    String relativePath;
    String fileName;

    public FileDTO(LocalFile file) {
        setRelativePath(file.getRelativeFilePath());
        setFileName(file.getFileName());
    }
}
