package com.redscooter.util;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class Utilities {
    public static final String RESOURCES_ROOT_PATH = "/resources";
//    public static final String RESOURCES_ROOT_PATH = "C:\\Users\\indri\\SWE-302-Software-Project-Management\\backend\\src\\main\\resources";
    public static final String STATIC_FOLDER_DIR = Utilities.joinPathsAsString(RESOURCES_ROOT_PATH, "static");
    public static final String HTML_TEMPLATES_FOLDER_DIR = joinPathsAsString(STATIC_FOLDER_DIR, "htmlTemplates");
    public static String capitalizeFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static long parseHumanReadableFileSize(String text) {
        double d = Double.parseDouble(text.replaceAll("[GMK]B$", ""));
        long l = Math.round(d * 1024 * 1024 * 1024L);
        switch (text.charAt(Math.max(0, text.length() - 2))) {
            default:
                l /= 1024;
            case 'K':
                l /= 1024;
            case 'M':
                l /= 1024;
            case 'G':
                return l;
        }
    }

    public static String joinPathsAsString(String... paths) {
        StringBuilder path;
        if (paths.length == 0)
            path = new StringBuilder("/");
        else
            path = new StringBuilder(paths[0]);
        for (int i = 1; i < paths.length; i++)
            path.append("/").append(paths[i]);
        return path.toString();
    }

    public static Path joinPaths(String... paths) {
        return Paths.get(joinPathsAsString(paths));
    }

    public static String invariantPath(String path) {
        return path.replaceAll("\\\\", "/");
    }

    public static List<File> getFilesFromFolder(File folder) {
        ArrayList<File> files = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null)
            return files;
        for (File f : listOfFiles)
            if (f.isFile())
                files.add(f);
        return files;
    }

    public static Path generateUniqueFilePathIfExists(String rootDir, String filename) {
        return generateUniqueFilePathIfExists(rootDir, filename, -1);
    }

    public static Path generateUniqueFilePathIfExists(String rootDir, String filename, int duplicateId) {
        Path path = Paths.get(joinPathsAsString(rootDir, filename));
        if (path.toFile().exists()) {
            String filenameBase = filename.substring(0, filename.lastIndexOf("."));
            if (duplicateId != -1)
                filenameBase = filenameBase.substring(0, filename.lastIndexOf("_"));
            filename = filenameBase + "_" + ++duplicateId + filename.substring(filename.lastIndexOf("."));
            return generateUniqueFilePathIfExists(rootDir, filename, duplicateId);
        }
        return path;
    }

    // use this function to avoid path traversal
    public static boolean validatePathPermissions(Path targetPath, Path rootAllowedPath) {
        return targetPath.normalize().startsWith(rootAllowedPath.normalize());
    }


//    public static void main(String[] args) throws URISyntaxException {
//        System.out.println(ROOT_WORKING_DIR);
//        System.out.println(STATIC_FOLDER_DIR);
//        System.out.println(DEFAULT_NO_IMAGE_PATH);
////        System.out.println(getFilesFromFolder(new File(Product.getLocalPathForId(4L))));
//
////        System.out.println(generateUniqueFilePathIfExists("C:/Users/indri/Downloads", "schema.ini"));
//
////        testPathNormalize();
//
//    }

//    public static Long tryParseLong(String value, String parameterName) {
//        try {
//            return Long.parseLong(value);
//        } catch (NumberFormatException e) {
//            throw new InvalidValueForTypeException(value, parameterName, "Long");
//        }
//    }

    public static Stream<?> collectionAsStream(Collection<?> collection) {
        return collection == null
                ? Stream.empty()
                : collection.stream();
    }

    public static <T> Stream<T> arrayAsStream(T[] array) {
        return array == null
                ? Stream.empty()
                : Arrays.stream(array);
    }

    public static boolean notNullAndContains(Collection<?> collection, Object object) {
        return collection!=null && collection.contains(object);
    }

    public static boolean notNullOrEmpty(String str) {
        if (str==null)
            return false;
        return str.trim().length()>0;
    }

    public static String getFilenameFromPath(String path){
        return FilenameUtils.getName(path);
    }
}
