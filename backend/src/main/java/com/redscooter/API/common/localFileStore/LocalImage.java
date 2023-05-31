package com.redscooter.API.common.localFileStore;

import lombok.Getter;

@Getter
public class LocalImage extends LocalFile {

    public LocalImage(String absolutePath, String relativePath, String fileName, int index) {
        super(absolutePath, relativePath, fileName, index);
    }

    public LocalImage(String absoluteDirPath, String relativeDirPath, String originalFileName, String fileName, int index) {
        super(absoluteDirPath, relativeDirPath, originalFileName, fileName, index);
    }

    public LocalImage(String absolutePath, String relativePath, String fileName) {
        super(absolutePath, relativePath, fileName);
    }

    public LocalImage(String absoluteDirPath, String relativeDirPath, String originalFileName, String fileName) {
        super(absoluteDirPath, relativeDirPath, originalFileName, fileName);
    }

    // TODO careful on NullPointerExceptions here
    public LocalImage(LocalFile f) {
        super(f.absoluteDirPath, f.relativeDirPath, f.generatedUniqueFilename, f.fileName, f.index);
    }

    public LocalImage(LocalImage i) {
        super(i.absoluteDirPath, i.relativeDirPath, i.generatedUniqueFilename, i.fileName, i.index);
    }

    public static LocalImage fromLocalFile(LocalFile localFile) {
        if (localFile == null)
            return null;
        return new LocalImage(localFile);
    }

}
