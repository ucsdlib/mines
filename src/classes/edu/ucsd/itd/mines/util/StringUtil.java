package edu.ucsd.itd.mines.util;

/**
 * Utility class for Strings.
 */
public class StringUtil {

    /**
     * Convenience method to determine if a String is null or empty.
     * @param str String to test.
     * @return true if non-null and nonempty, false otherwise
     */
    public static boolean isDefined(String str) {
        if (str == null || str.length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
