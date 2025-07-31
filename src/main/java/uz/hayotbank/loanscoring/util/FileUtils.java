package uz.hayotbank.loanscoring.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * File utilities.
 */

public class FileUtils {

    /**
     * Convert base64 string to input stream.
     *
     * @param base64 Base64 string.
     * @return Input stream.
     */
    public static InputStream convert(String base64) {
        return new ByteArrayInputStream(Base64.getDecoder().decode(base64));
    }
}
