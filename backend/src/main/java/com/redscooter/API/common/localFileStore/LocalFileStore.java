package com.redscooter.API.common.localFileStore;

import com.redscooter.config.configProperties.FileStoreConfigProperties;
import com.redscooter.exceptions.api.LocalFileStore.FileNotFoundException;
import com.redscooter.exceptions.api.LocalFileStore.UnableToCreateDirectory;
import com.redscooter.exceptions.api.forbidden.PermissionDeniedToAccessPathException;
import com.redscooter.exceptions.generic.FlowControlException;
import com.redscooter.util.Utilities;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//     ex3 filesystem has a limit of 32000 links per node, ex4 has a limit of 64000, ntfs has a limit of 4,294,967,295
//     to play it safe we will divide the product images in directories of 30'000 sub dirs for each dir
//     we could use hashes but this is easier to reference
//     i.e: root/0-30'000/
//                       /0
//                       /1
//                       ...
//                       /29'999
//              /30'000-60'000/
//              /60'000-90'000 /

@Getter
public class LocalFileStore {
    public static final int DEFAULT_MAX_NUMBER_OF_SUB_DIRECTORIES = 30000;
    public int maxNumberOfSubDirectories;
    private String absoluteParentRootPath;
    private String fileStoreRelativePath;

    public LocalFileStore(String absoluteParentRootPath, String fileStoreRelativePath, Integer maxNumberOfSubDirectories) {
        setAbsoluteParentRootPath(absoluteParentRootPath);
        setFileStoreRelativePath(fileStoreRelativePath);
        if (maxNumberOfSubDirectories != null) setMaxNumberOfSubDirectories(DEFAULT_MAX_NUMBER_OF_SUB_DIRECTORIES);
    }

    public LocalFileStore(String absoluteParentRootPath, String fileStoreRelativePath) {
        this(absoluteParentRootPath, fileStoreRelativePath, null);
    }

    public LocalFileStore(String fileStoreRelativePath, FileStoreConfigProperties fileStoreConfigProperties) {
        this(fileStoreConfigProperties.getPublicRootPath(), fileStoreRelativePath, fileStoreConfigProperties.getMaxNumberOfSubDirectories());
    }

    public static String generateUniqueRelativePath(Long id, String subPath, int maxNumberOfSubDirectories) {
        if (id == null)
            throw new NullPointerException("Id cannot be null!");
        long index = id / maxNumberOfSubDirectories;
        // TODO get path from config
        return Utilities.joinPathsAsString(subPath, (index * maxNumberOfSubDirectories) + "-" + (maxNumberOfSubDirectories + index * maxNumberOfSubDirectories), id.toString());
    }

    public static String generateUniqueRelativePath(Long id, String subPath) {
        return generateUniqueRelativePath(id, subPath, DEFAULT_MAX_NUMBER_OF_SUB_DIRECTORIES);
    }

    public String generateUniqueRelativePath(Long id) {
        return generateUniqueRelativePath(id, getFileStoreRelativePath(), maxNumberOfSubDirectories);
    }

    public Path generateUniqueRelativePathWithFilename(Long id, String fileName) {
        if (fileName == null)
            return null;
        return Utilities.joinPaths(generateUniqueRelativePath(id, getFileStoreRelativePath(), maxNumberOfSubDirectories), fileName);
    }

    public String generateUniqueRelativePathWithFilenameAsString(Long id, String fileName) {
        if (fileName == null)
            return null;
        return Utilities.joinPathsAsString(generateUniqueRelativePath(id, getFileStoreRelativePath(), maxNumberOfSubDirectories), fileName);
    }

    public LocalFile getFileByFilename(Long id, String fileName) {
        if (fileName == null)
            return null;
        return new LocalFile(generateUniqueAbsolutePath(id), generateUniqueRelativePath(id), fileName);
    }

    public String generateUniqueAbsolutePath(Long id) {
        return Utilities.joinPathsAsString(getAbsoluteParentRootPath(), generateUniqueRelativePath(id));
    }

    public Path generateUniqueAbsolutePathWithFilename(Long id, String fileName) {
        if (fileName == null)
            return null;
        return Utilities.joinPaths(generateUniqueAbsolutePath(id), fileName);
    }

    public String generateUniqueAbsolutePathWithFilenameAsString(Long id, String fileName) {
        if (fileName == null)
            return null;
        return Utilities.joinPathsAsString(generateUniqueAbsolutePath(id), fileName);
    }

    public Path getFileStoreAbsolutePath() {
        return Utilities.joinPaths(getAbsoluteParentRootPath(), fileStoreRelativePath);
    }

    public String getFileStoreAbsolutePathAsString() {
        return Utilities.joinPathsAsString(getAbsoluteParentRootPath(), fileStoreRelativePath);
    }

    public Path getAbsolutePathFromRelativePath(String relativePath) {
        return Utilities.joinPaths(getAbsoluteParentRootPath(), relativePath);
    }

    public String getAbsolutePathFromRelativePathAsString(String relativePath) {
        return Utilities.joinPathsAsString(getAbsoluteParentRootPath(), relativePath);
    }

    public void setMaxNumberOfSubDirectories(Integer maxNumberOfSubDirectories) {
        if (maxNumberOfSubDirectories != null) {
            if (maxNumberOfSubDirectories > 32000)
                System.out.println("[WARNING|SEVERE] It is recommended to use maxNumberOfSubDirectories<32000 to avoid ex3 filesystem limitations.");
            this.maxNumberOfSubDirectories = maxNumberOfSubDirectories;
        }
    }

    public List<File> getAllFiles(Long id) {
        return Utilities.getFilesFromFolder(new File(generateUniqueAbsolutePath(id)));
    }

    public List<LocalFile> getAllLocalFilesSorted(Long id) {
        return getAllLocalFiles(id).stream().sorted(Comparator.comparingInt(LocalFile::getIndex)).collect(Collectors.toList());
    }

    public List<LocalFile> getAllLocalFiles(Long id) {
        String relativeRootPath = generateUniqueRelativePath(id);
        String absoluteRootPath = generateUniqueAbsolutePath(id);
        List<File> files_ = Utilities.getFilesFromFolder(new File(generateUniqueAbsolutePath(id)));
        List<LocalFile> fileStoreLocalFiles = new ArrayList<>();
        for (File f : files_) {
            try {
                String[] details = f.getName().split("__", 2); // get index and actual filename
                if (details.length != 2)
                    throw new FlowControlException();
                fileStoreLocalFiles.add(new LocalFile(absoluteRootPath, relativeRootPath, f.getName(), details[1], Integer.parseInt(details[0])));
            } catch (Exception ignored) {
                fileStoreLocalFiles.add(new LocalFile(absoluteRootPath, relativeRootPath, f.getName(), Integer.MAX_VALUE));
            }
        }
        return fileStoreLocalFiles;
    }

    public List<File> getAllFilesSorted(Long id) {
        return getAllLocalFiles(id).stream().sorted(Comparator.comparingInt(LocalFile::getIndex)).map(f -> new File(Utilities.joinPathsAsString(generateUniqueRelativePath(id), f.getFileName()), f.getFileName())).toList();
    }

    public void generateRootFolders(Long id) {
        Path path = Paths.get(generateUniqueAbsolutePath(id));
        File rootDir = path.toFile(); // local root path is safe, no need to validatePathPermissions();
        if (!rootDir.mkdirs()) // it is important to check if exists afterwards to avid concurrent access since mkdirs() returns false if exists
            if (!rootDir.exists())
                throw new UnableToCreateDirectory(path);
    }

    public LocalFile saveFile(MultipartFile file, Long id, String fileName) throws IOException {
        return saveFile(file, id, fileName, Integer.MAX_VALUE);
    }

    public LocalFile saveFile(MultipartFile file, Long id, String fileName, Integer fileIndex) throws IOException {
        fileName = sanitizeFileName(fileName); // TODO should this be default behavior?
        String fileName_ = fileName;
        if (fileIndex != null)
            fileName_ = fileIndex + "__" + fileName;
        Path fileFullDestinationPath = Utilities.generateUniqueFilePathIfExists(generateUniqueAbsolutePath(id), fileName_);
        String generatedUniqueFilename = fileFullDestinationPath.getFileName().toString();
        LocalFile savedFile = new LocalFile(generateUniqueAbsolutePath(id), generateUniqueRelativePath(id), generatedUniqueFilename, fileName, fileIndex);
        generateRootFolders(id);
        if (Utilities.validatePathPermissions(fileFullDestinationPath, getFileStoreAbsolutePath()))
            Files.copy(file.getInputStream(), fileFullDestinationPath.normalize(), StandardCopyOption.REPLACE_EXISTING);
        else
            throw new PermissionDeniedToAccessPathException(fileFullDestinationPath, file.getOriginalFilename(), getFileStoreAbsolutePath(), getFileStoreRelativePath(), FileOperation.WRITE);
        return savedFile;
    }

    public void deleteFile(LocalFile localFile, boolean checkIfExists) throws IOException {
        if (localFile == null)
            return;
        deleteFile(localFile.getRelativeFilePath(), checkIfExists);
    }

    public void deleteFile(LocalFile localFile) throws IOException {
        deleteFile(localFile, true);
    }

    public void deleteFile(String relativeFilePath) throws IOException {
        deleteFile(relativeFilePath, true);
    }

    public void deleteFile(String relativeFilePath, boolean checkIfExists) throws IOException {
        if (!checkIfExists && relativeFilePath == null)
            return;

        Path filePath_ = Paths.get(relativeFilePath);
        if (!filePath_.isAbsolute())
            filePath_ = getAbsolutePathFromRelativePath(relativeFilePath); // should match the path exposed in WebMVCConfig#ResourceHandlerRegistry
        File file = filePath_.toFile();

        if (!file.exists()) {
            if (!checkIfExists)
                return;
            throw new FileNotFoundException(relativeFilePath);
        }
        // make sure we normalize paths and validatePathPermissions in case the client sends a malicious path i.e: /../README.md
        // to do so we check if the relative path is inside the FileStore absolute path
        if (Utilities.validatePathPermissions(filePath_, getFileStoreAbsolutePath()))
            Files.delete(filePath_);
        else
            throw new PermissionDeniedToAccessPathException(filePath_, relativeFilePath, getFileStoreAbsolutePath(), getFileStoreRelativePath(), FileOperation.DELETE);
    }

    public void deleteAllFiles(Long id) throws IOException {
        FileUtils.deleteDirectory(new File(generateUniqueAbsolutePath(id)));
        ;
    }

    public String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^A-Za-z0-9._-]+", "_");
    }
//    public String checkIfFileIsUploadedByFilename(Path fullFilePath, Long id) {
//        return checkIfFileIsUploadedByFilename(fullFilePath, generateUniqueAbsolutePath(id), generateUniqueRelativePath(id), getFileStoreAbsolutePathAsString(), getFileStoreRelativePath());
//    }
//
//    public static String checkIfFileIsUploadedByFilename(Path fullFilePath, String absolutePath, String relativePath, String absoluteRootBasePath, String relativeBasePath) {
//        // get filename
//        String fileName = fullFilePath.getFileName().toString();
//
//        // generate full path and normalize the path to avoid directory traversal
//        Path generatedFileFullPath = Utilities.joinPaths(absolutePath, fileName).normalize();
//
//        // check if file exists
//        if (!generatedFileFullPath.toFile().exists())
//            throw new FileNotFoundException(Utilities.joinPathsAsString(relativePath, fileName));
//
//        // check if the file is under exposed directory to avoid any directory traversal attack
//        Path path = Path.of(absoluteRootBasePath);
//        if (!Utilities.validatePathPermissions(generatedFileFullPath, path))
//            throw new PermissionDeniedToAccessPathException(generatedFileFullPath, Utilities.joinPathsAsString(relativePath, fileName), path, relativeBasePath, PermissionDeniedToAccessPathException.Operation.READ);
//        return fileName;
//    }


    void setMaxNumberOfSubDirectories(int maxNumberOfSubDirectories) {
        this.maxNumberOfSubDirectories = maxNumberOfSubDirectories;
    }

    void setAbsoluteParentRootPath(String absoluteParentRootPath) {
        this.absoluteParentRootPath = absoluteParentRootPath;
    }

    void setFileStoreRelativePath(String fileStoreRelativePath) {
        this.fileStoreRelativePath = fileStoreRelativePath;
    }

    //
//    public List<Image> getImages() {
//        String imagesRoot = getImagesRootPath();
//        List<File> imageFiles = Utilities.getFilesFromFolder(new File(getImagesLocalRootPath()));
//        List<LocalImage> localImages = new ArrayList<>();
//        for (File f : imageFiles) {
//            try {
//                String[] details = f.getName().split("__", 2);
//                localImages.add(new LocalImage(f.toString(), f.getName(), details[1], Integer.parseInt(details[0])));
//            } catch (Exception ignored) {
//                localImages.add(new LocalImage(f.toString(), f.getName(), f.getName(), Integer.MAX_VALUE));
//            }
//        }
//        List<Image> images = localImages.stream().sorted(Comparator.comparingInt(LocalImage::getOrder)).map(i -> new Image(Utilities.joinPathsAsString(imagesRoot, i.getFileName()), i.getName())).collect(Collectors.toList());
//        if (images.size() == 0)
//            images.add(Image.getDefaultNoImage());
//        return images;
//    }
//
//    @JsonIgnore
//    public String getImagesRootPath() {
//        return getPathForId(getId());
//    }
//
//    public static String getPathForId(Long id) {
//        if (id == null)
//            throw new NullPointerException("Id cannot be null!");
//        long index = id / MAX_NUMBER_OF_SUB_DIRECTORIES;
//        // TODO get path from config
//        return Utilities.joinPathsAsString(Utilities.PRODUCT_IMAGES_SUB_PATH, (index * MAX_NUMBER_OF_SUB_DIRECTORIES) + "-" + (MAX_NUMBER_OF_SUB_DIRECTORIES + index * MAX_NUMBER_OF_SUB_DIRECTORIES), id.toString());
//    }
//
//    public static String getLocalPathForId(Long id) {
//        return Utilities.joinPathsAsString(Utilities.STATIC_FOLDER_DIR, getPathForId(id));
//    }
//    @JsonIgnore
//    public String getImagesLocalRootPath() {
//        return getLocalPathForId(getId());
//    }
}
