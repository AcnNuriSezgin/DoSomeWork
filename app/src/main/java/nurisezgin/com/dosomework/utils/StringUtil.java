package nurisezgin.com.dosomework.utils;

import java.util.Iterator;

/**
 * Created by nuri on 25.07.2018
 */
public final class StringUtil {

    public static final String EMPTY_STRING = "";

    private StringUtil() { }

    public static boolean shouldEmpty(String src) {
        return src == null || src.isEmpty();
    }

    public static boolean shouldNotEmpty(String src) {
        return !shouldEmpty(src);
    }

    public static int len(String src) {
        return normalize(src).length();
    }

    public static String normalize(String src) {
        return normalizeWith(src, "");
    }

    public static String normalizeWith(String src, String optional) {
        String result = src;

        if (shouldEmpty(src)) {
            result = optional;
        }

        return result;
    }

    public static String isNotEmptyAddSuffix(String src, String suffix) {
        if (shouldNotEmpty(src)) {
            StringBuilder builder = new StringBuilder();
            builder.append(src);
            builder.append(suffix);

            src = builder.toString();
        }

        return src;
    }

    public static String isNotEmptyAddPrefix(String src, String prefix) {
        if (shouldNotEmpty(src)) {
            StringBuilder builder = new StringBuilder();
            builder.append(prefix);
            builder.append(src);

            src = builder.toString();
        }

        return src;
    }

    public static boolean shouldRawStringEmpty(String src) {
        return shouldEmpty(src) || src.trim().isEmpty();
    }

    public static boolean shouldRawStringNotEmpty(String src) {
        return !shouldRawStringEmpty(src);
    }

    public static String toString(Object o) {
        if (o == null) {
            return EMPTY_STRING;
        }

        return o.toString();
    }

    public static String removeNonNumeric(String text) {
        if (shouldEmpty(text)) {
            return EMPTY_STRING;
        }

        return text.replaceAll("[^0-9]", "");
    }

    public static String join(String delimiter, Iterable iterable) {
        if (delimiter == null) {
            delimiter = normalize(delimiter);
        }

        if (iterable == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> it = iterable.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }

}
