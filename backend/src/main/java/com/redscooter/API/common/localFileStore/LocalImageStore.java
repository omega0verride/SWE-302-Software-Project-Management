package com.redscooter.API.common.localFileStore;

import com.redscooter.config.configProperties.FileStoreConfigProperties;
import com.redscooter.exceptions.api.LocalFileStore.InvalidUploadFileType;
import com.redscooter.util.Utilities;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LocalImageStore extends LocalFileStore {
    private LocalImage defaultNoImageLocalImage;

    public LocalImageStore(String absoluteParentRootPath, String fileStoreRelativePath, Integer maxNumberOfSubDirectories, LocalImage defaultNoImageLocalImage) {
        super(absoluteParentRootPath, fileStoreRelativePath, maxNumberOfSubDirectories);
        if (defaultNoImageLocalImage == null)
            buildDefaultNoLocalImage(Utilities.joinPathsAsString("images", "no_image.jpg"));
        else
            this.defaultNoImageLocalImage=defaultNoImageLocalImage;
    }

    public LocalImageStore(String absoluteParentRootPath, String fileStoreRelativePath, Integer maxNumberOfSubDirectories) {
        this(absoluteParentRootPath, fileStoreRelativePath, maxNumberOfSubDirectories, null);
    }

    public LocalImageStore(String fileStoreRelativePath, FileStoreConfigProperties fileStoreConfigProperties) {
        super(fileStoreRelativePath, fileStoreConfigProperties);
    }

    private void buildDefaultNoLocalImage(String defaultNoImageRelativePath) {
        defaultNoImageLocalImage = new LocalImage(Utilities.joinPathsAsString(this.getAbsoluteParentRootPath(), defaultNoImageRelativePath), defaultNoImageRelativePath, Utilities.getFilenameFromPath(defaultNoImageRelativePath));
    }

    public List<LocalImage> getImages(Long id) {
        return getImages(id, true);
    }

    public List<LocalImage> getImages(Long id, boolean returnDefaultNoImage) {
        List<LocalFile> files = getAllLocalFilesSorted(id);
        List<LocalImage> images = files.stream().map(LocalImage::new).collect(Collectors.toList());
        if (images.size() == 0 && returnDefaultNoImage)
            images.add(getDefaultNoImage());
        return images;
    }

    public LocalImage getImage(Long id, String filename, ImageNotFoundBehavior imageNotFoundBehavior) {
        LocalImage image = LocalImage.fromLocalFile(getFileByFilename(id, filename));
        if (image != null && image.exists())
            return image;
        if (imageNotFoundBehavior == ImageNotFoundBehavior.GET_FIRST || imageNotFoundBehavior == ImageNotFoundBehavior.GET_FIRST_OR_DEFAULT) {
            List<LocalImage> images = getImages(id, false);
            if (images.size() > 0)
                return getImages(id).get(0);
        }
        if (imageNotFoundBehavior == ImageNotFoundBehavior.GET_DEFAULT || imageNotFoundBehavior == ImageNotFoundBehavior.GET_FIRST_OR_DEFAULT)
            return getDefaultNoImage();
        return null;
    }

    public LocalImage getImage(Long id, String filename) {
        return getImage(id, filename, ImageNotFoundBehavior.GET_DEFAULT);
    }

    public LocalImage getImageByFilename(Long id, String filename) {
        return LocalImage.fromLocalFile(getFileByFilename(id, filename));
    }

    public LocalImage saveImage(MultipartFile file, Long id) throws IOException {
        return saveImage(file, id, null);
    }

    public LocalImage saveImage(MultipartFile file, Long id, Integer imageIndex) throws IOException {
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        String fileType = file.getContentType();
        if (fileType == null)
            throw new InvalidUploadFileType(fileName, "image/.*", "null. Could not get file type!");
        if (!fileType.startsWith("image/"))
            throw new InvalidUploadFileType(fileName, "image/.*", fileType);
        try {
            return LocalImage.fromLocalFile(saveFile(file, id, fileName, imageIndex));
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    public void deleteImage(String relativeImagePath) throws IOException {
        deleteFile(relativeImagePath);
    }

    public void deleteImageIfExists(String relativeImagePath) throws IOException {
        deleteFile(relativeImagePath, false);
    }

    public void deleteImageIfExists(LocalImage localImage) throws IOException {
        deleteFile(localImage, false);
    }

    public void deleteAllImages(Long id) throws IOException {
        deleteAllFiles(id);
    }

    public LocalImage getDefaultNoImage() {
        return new LocalImage(defaultNoImageLocalImage);
    }

    public enum ImageNotFoundBehavior {
        GET_DEFAULT,
        GET_FIRST,
        GET_FIRST_OR_DEFAULT,
        NONE
    }

}
