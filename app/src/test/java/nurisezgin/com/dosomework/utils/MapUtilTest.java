package nurisezgin.com.dosomework.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by nuri on 25.07.2018
 */
@RunWith(JUnitParamsRunner.class)
public class MapUtilTest {

    @Test
    @Parameters(method = "shouldEmptyValues")
    public void should_EmptyCorrect(Map<Object, Object> map, boolean expected) {
        boolean actual = MapUtil.shouldEmpty(map);

        assertThat(actual, is(expected));
    }

    private Object[] shouldEmptyValues() {
        return new Object[]{
                new Object[]{newMap(5), false},
                new Object[]{newMap(0), true},
                new Object[]{null, true}
        };
    }

    @Test
    @Parameters(method = "shouldNotEmptyValues")
    public void should_NotEmptyCorrect(Map<Object, Object> map, boolean expected) {
        boolean actual = MapUtil.shouldNotEmpty(map);

        assertThat(actual, is(expected));
    }

    private Object[] shouldNotEmptyValues() {
        return new Object[]{
                new Object[]{newMap(5), true},
                new Object[]{newMap(0), false},
                new Object[]{null, false}
        };
    }

    @Test
    @Parameters(method = "sizeOfValues")
    public void should_SizeOfCorrect(Map<Object, Object> map, int expected) {
        int actual = MapUtil.sizeOf(map);

        assertThat(actual, is(expected));
    }

    private Object[] sizeOfValues() {
        return new Object[]{
                new Object[]{newMap(5), 5},
                new Object[]{newMap(0), 0},
                new Object[]{null, 0}
        };
    }

    @Test
    public void should_OptionalGetCorrect() {
        final int key = 0;
        final String value = "value";
        final String optionalValue = "";

        Map<Integer, String> map = new HashMap<>();
        map.put(key, value);

        String actual = MapUtil.optionalGet(map, key, optionalValue);

        assertThat(actual, is(equalTo(value)));
    }

    @Test
    public void should_WhenMapNotContainsKeyOptionalGetCorrect() {
        final int key = 0;
        final int searchKey = 1;
        final String value = "value";
        final String optionalValue = "123";

        Map<Integer, String> map = new HashMap<>();
        map.put(key, value);

        String actual = MapUtil.optionalGet(map, searchKey, optionalValue);

        assertThat(actual, is(equalTo(optionalValue)));
    }

    @Test
    public void should_OptionalGetWithEmptyMapCorrect() {
        final int key = 0;
        final String value = "value";
        final String optionalValue = "123";

        Map<Integer, String> map = new HashMap<>();
        map.put(key, value);

        String actual = MapUtil.optionalGet(null, key, optionalValue);

        assertThat(actual, is(equalTo(optionalValue)));
    }

    private Map<Object, Object> newMap(int size) {
        Map<Object, Object> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            map.put(i, new Object());
        }

        return map;
    }

}