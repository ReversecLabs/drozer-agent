package com.reversec.jsolar.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VerifyTest {
    @Rule
    public TemporaryFolder files = new TemporaryFolder();

    @Test
    public void preservesLeadingZeros() throws Exception {
        File file = files.newFile();
        Files.write(file.toPath(), "a".getBytes(StandardCharsets.US_ASCII));
        assertEquals("0cc175b9c0f1b6a831c399e269772661", Verify.md5sum(file));
    }

    @Test
    public void hashesEmptyFiles() throws Exception {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", Verify.md5sum(files.newFile()));
    }

    @Test
    public void hashesMultipleBuffersAndClosesTheFile() throws Exception {
        File file = files.newFile();
        byte[] contents = new byte[1000000];
        Arrays.fill(contents, (byte) 'a');
        Files.write(file.toPath(), contents);
        assertEquals("7707d6ae4e027c70eea2a935c2296f21", Verify.md5sum(file));
        assertTrue(file.delete());
    }

    @Test(expected = IOException.class)
    public void propagatesMissingFileErrors() throws Exception {
        Verify.md5sum(new File(files.getRoot(), "missing"));
    }
}
