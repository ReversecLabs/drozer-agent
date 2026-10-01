package com.reversec.jsolar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

// No idea what this class is used for yet
public class Verify {
    private static final int BUFFER_SIZE = 4096;

    //I hope to god this is only being used for a checksum
    //Also pretty sure this is deserialise vuln vulnerable
    //TODO move away from MD5sum if it doesnt break functionality
    public static String md5sum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (FileInputStream file_stream = new FileInputStream(file)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int count;
            while((count = file_stream.read(buf, 0, BUFFER_SIZE)) != -1){
                digest.update(buf, 0, count);
            }
        }

        return String.format(Locale.ROOT, "%032x", new BigInteger(1, digest.digest()));
    }
}
