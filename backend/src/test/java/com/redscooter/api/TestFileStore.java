package com.redscooter.api;

import com.redscooter.util.Utilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFileStore {
    @Test
    public void testPathNormalize() {
        Path rootAllowdPath = Paths.get("/root/filestore");
        Assertions.assertFalse(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "../schema.ini").normalize(), rootAllowdPath));
        if (File.separator.equals("\\"))
            Assertions.assertFalse(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "..\\schema.ini").normalize(), rootAllowdPath));
        else
            Assertions.assertTrue(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "..\\schema.ini").normalize(), rootAllowdPath));
        Assertions.assertFalse(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "../../schema.ini").normalize(), rootAllowdPath));
        Assertions.assertTrue(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "./schema.ini").normalize(), rootAllowdPath));
        Assertions.assertTrue(Utilities.validatePathPermissions(Paths.get(rootAllowdPath.toString(), "/schema.ini").normalize(), rootAllowdPath));
    }

}
