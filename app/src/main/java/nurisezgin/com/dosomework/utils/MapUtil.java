package nurisezgin.com.dosomework.utils;

import java.util.Map;

/**
 * Created by nuri on 25.07.2018
 */
public final class MapUtil {

    private MapUtil() { }

    public static <K, V> boolean shouldEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean shouldNotEmpty(Map<K, V> map) {
        return !shouldEmpty(map);
    }

    public static <K, V> int sizeOf(Map<K, V> map) {
        if (shouldNotEmpty(map)) {
            return map.size();
        }

        return 0;
    }

    public static <K,V> V optionalGet(Map<K,V> map, K key, V defaultValue) {
        if (shouldEmpty(map)) {
            return defaultValue;
        }

        if (map.containsKey(key)) {
            return map.get(key);
        }

        return defaultValue;
    }

}
