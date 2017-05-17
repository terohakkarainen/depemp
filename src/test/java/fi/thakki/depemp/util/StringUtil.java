package fi.thakki.depemp.util;

import org.apache.commons.lang.RandomStringUtils;

public final class StringUtil {

    private StringUtil() {
        // Nothing.
    }

    public static final String randomString() {
        return randomString(5);
    }

    public static final String randomString(
            int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
