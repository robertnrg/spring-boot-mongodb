package com.robert.mongodb.util;

public final class StringUtil implements java.io.Serializable {

    /**
     * Generated serialVersionUID
     **/
    private static final long serialVersionUID = 6946705801910652733L;

    /**
     * Check to ensure that a {@code String} is not null or empty (after optional
     * trimming of leading and trailing whitespace). Usually used with
     * assertions, as in
     * <p>
     * <pre>
     * assert StringUtils.notNullOrEmpty(cipherXform, true) : "Cipher transformation may not be null or empty!";
     * </pre>
     *
     * @param str  The {@code String} to be checked.
     * @param trim If {@code true}, the string is first trimmed before checking to
     *             see if it is empty, otherwise it is not.
     * @return True if the string is null or empty (after possible trimming);
     * otherwise false.
     * @since 2.0
     */
    public static boolean notNullOrEmpty(String str, boolean trim) {
        if (trim) {
            return !(str == null || str.trim().equals(""));
        } else {
            return !(str == null || str.equals(""));
        }
    }

    /**
     * Returns true if String is empty ("") or null.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * Returns the replace value if the value of test is null, "null", or ""
     *
     * @param test    The value to test
     * @param replace The replacement value
     * @return The correct value
     */
    public static String replaceNull(String test, String replace) {
        return (test == null || "null".equalsIgnoreCase(test.trim()) || "".equals(test.trim())) ? replace : test;
    }

    public static boolean startsWith(final String data, final String[] pattern) {

        boolean startsWith = false;
        for (final String p : pattern) {
            if (data.startsWith(p)) {
                startsWith = true;
                break;
            }
        }
        return startsWith;

    }

    /**
     * The caller should be prevented from constructing objects of this class, by
     * declaring this private constructor.
     */
    private StringUtil() {
        // This prevents even the native class from calling this.
        throw new AssertionError();
    }

}