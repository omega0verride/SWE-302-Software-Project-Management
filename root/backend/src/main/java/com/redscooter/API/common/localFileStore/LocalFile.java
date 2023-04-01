package com.redscooter.API.common.localFileStore;

import com.redscooter.util.Utilities;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class LocalFile {
    String absoluteDirPath;
    String absoluteFilePath;
    String relativeDirPath;
    String relativeFilePath;

    String generatedUniqueFilename;
    String fileName;

    int index = Integer.MAX_VALUE;

    public LocalFile(String absoluteDirPath, String relativeDirPath, String fileName, int index) {
        this(absoluteDirPath, relativeDirPath, fileName, fileName, index);
    }

    public LocalFile(String absoluteDirPath, String relativeDirPath, String generatedUniqueFilename, String fileName, Integer index) {
        setAbsoluteDirPath(absoluteDirPath);
        setAbsoluteFilePath(Utilities.joinPathsAsString(absoluteDirPath, generatedUniqueFilename));
        setRelativeDirPath(relativeDirPath);
        setRelativeFilePath(Utilities.joinPathsAsString(relativeDirPath, generatedUniqueFilename));
        setGeneratedUniqueFilename(generatedUniqueFilename);
        setFileName(fileName);
        if (index!=null) setIndex(index);
    }

    public LocalFile(String absoluteDirPath, String relativeDirPath, String fileName) {
        this(absoluteDirPath, relativeDirPath, fileName, fileName);
    }

    public LocalFile(String absoluteDirPath, String relativeDirPath, String generatedUniqueFilename, String fileName) {
        this(absoluteDirPath, relativeDirPath, generatedUniqueFilename, fileName, Integer.MAX_VALUE);
    }

    // TODO:[-1] add invalid file name exception in upload file context
//    public static boolean isValidFilename(String filename) {
//        if (filename == null || filename.length() == 0) {
//            return false;
//        }
//
//        // Check if the filename contains any invalid characters
//        String invalidChars = "/\\?%*:|\"<>";
//        for (int i = 0; i < invalidChars.length(); i++) {
//            if (filename.indexOf(invalidChars.charAt(i)) != -1) {
//                return false;
//            }
//        }
//
//        return true;
//    }
    @Deprecated
    public String getEncodedRelativeFilePath() {
        // we should not send an encoded url since the user will use this to delete the file, and it will not match with the path
        // the client should encode this itself
        if (getRelativeFilePath() == null)
            return null;
        // #
        // &
        // % is rejected as filename TODO exception on upload, The request was rejected because the URL contained a potentially malicious String "%25"
        // ; is rejected as filename TODO exception on upload, The request was rejected because the URL contained a potentially malicious String "%25"
        //        &
        //        a ~!@$^_+()[]{}-='b.,_0`
//        return getRelativeFilePath().replaceAll("%", "%25").replaceAll(" ", "%20").replaceAll("#", "%23").replaceAll("'", "%27").replaceAll(";", "%3B").replaceAll("\\(", "%28").replaceAll("\\)", "%29");
        return getRelativeFilePath();
    }

    public String getEncodedAbsoluteFilePath() {
        return getAbsoluteFilePath().replaceAll(" ", "%20");
    }

    public boolean exists() {
        return new File(absoluteDirPath).exists();
    }

    @Override
    public String toString() {
        return getRelativeDirPath();
    }

    public FileDTO toFileDTO() {
        return new FileDTO(this);
    }

}
