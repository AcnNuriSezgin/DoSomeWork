package nurisezgin.com.dosomework.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nuri on 25.07.2018
 */
public final class CollectionsUtil {

    private CollectionsUtil() { }

    public static <T> ArrayList<T> emptyList() {
        return new ArrayList<>();
    }

    public static <T> boolean shouldNull(Collection<T> collection) {
        return collection == null;
    }

    public static <T> boolean shouldEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean shouldNotEmpty(Collection<T> collection) {
        return !shouldEmpty(collection);
    }

    public static <T> boolean hasIndex(List<T> collection, int index) {
        return index < sizeOf(collection);
    }

    public static <T> List<T> normalize(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    public static <T> int indexOfLast(List<T> list) {
        int lastIndex =  sizeOf(list) - 1;
        return Math.max(0, lastIndex);
    }

    public static <T> int sizeOf(List<T> list) {
        if (shouldNotEmpty(list)) {
            return list.size();
        }

        return 0;
    }

    public static <T> boolean sameSize(List<T> src, List<T> dest) {
        if (shouldNotEmpty(src) && shouldNotEmpty(dest)) {
            return sizeOf(src) == sizeOf(dest);
        }

        return false;
    }

    public static <T> T getFirstItem(List<T> list) {
        if (shouldNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    public static <T> T getFirstItem(List<T> list, T optional) {
        T firstItem = getFirstItem(list);

        return firstItem == null ? optional : firstItem;
    }

    public static <T> T getLastItem(List<T> list) {
        if (shouldNotEmpty(list)) {
            return list.get(indexOfLast(list));
        }

        return null;
    }

    public static <T> T removeLastItem(List<T> list) {
        if (shouldNotEmpty(list)) {
            return list.remove(sizeOf(list) - 1);
        }

        return null;
    }

    public static <T> List<T> reverseOrder(List<T> list) {
        List<T> copied = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            copied.add(list.get(i));
        }
        return copied;
    }
}
