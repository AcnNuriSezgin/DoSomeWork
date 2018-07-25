package nurisezgin.com.dosomework.utils;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 25.07.2018
 */
@RunWith(JUnitParamsRunner.class)
public class CollectionsUtilTest {

    @Test
    @Parameters(method = "emptyValues")
    public void should_EmptyCorrect(List<Object> list, boolean expected) {
        boolean actual = CollectionsUtil.shouldEmpty(list);

        assertThat(actual, is(expected));
    }

    private Object[] emptyValues() {
        return new Object[]{
                new Object[]{newList(5), false},
                new Object[]{newList(0), true},
                new Object[]{null, true}
        };
    }

    @Test
    @Parameters(method = "nullValues")
    public void should_NullCorrect(List<Object> list, boolean expected) {
        boolean actual = CollectionsUtil.shouldNull(list);

        assertThat(actual, is(expected));
    }

    private Object[] nullValues() {
        return new Object[]{
                new Object[]{newList(5), false},
                new Object[]{newList(0), false},
                new Object[]{null, true}
        };
    }

    @Test
    @Parameters(method = "notEmptyValues")
    public void should_NotEmptyCorrect(List<Object> list, boolean expected) {
        boolean actual = CollectionsUtil.shouldNotEmpty(list);

        assertThat(actual, is(expected));
    }

    private Object[] notEmptyValues() {
        return new Object[]{
                new Object[]{newList(5), true},
                new Object[]{newList(0), false},
                new Object[]{null, false}
        };
    }

    @Test
    @Parameters(method = "hasIndexValues")
    public void should_HasIndexCorrect(List<Object> list, int index, boolean expected) {
        boolean actual = CollectionsUtil.hasIndex(list, index);

        assertThat(actual, is(expected));
    }

    private Object[] hasIndexValues() {
        return new Object[]{
                new Object[]{newList(5), 2, true},
                new Object[]{newList(1), 3, false},
                new Object[]{null, 5, false}
        };
    }

    @Test
    @Parameters(method = "normalizeValues")
    public void should_NormalizeCorrect(List<Object> list, int expected) {
        int actual = CollectionsUtil.normalize(list).size();

        assertThat(actual, is(expected));
    }

    private Object[] normalizeValues() {
        return new Object[]{
                new Object[]{newList(5), 5},
                new Object[]{newList(0), 0},
                new Object[]{null, 0}
        };
    }

    @Test
    @Parameters(method = "indexOfLastValues")
    public void should_IndexOfLastCorrect(List<Object> list, int expected) {
        int actual = CollectionsUtil.indexOfLast(list);

        assertThat(actual, is(expected));
    }

    private Object[] indexOfLastValues() {
        return new Object[]{
                new Object[]{newList(5), 4},
                new Object[]{newList(0), 0},
                new Object[]{null, 0}
        };
    }

    @Test
    @Parameters(method = "sizeOfValues")
    public void should_SizeOfCorrect(List<Object> list, int expected) {
        int actual = CollectionsUtil.sizeOf(list);

        assertThat(actual, is(expected));
    }

    private Object[] sizeOfValues() {
        return new Object[]{
                new Object[]{newList(5), 5},
                new Object[]{newList(0), 0},
                new Object[]{null, 0}
        };
    }

    @Test
    @Parameters(method = "sameSizeValues")
    public void should_SameSizeCorrect(List<Object> src, List<Object> dest, boolean expected) {
        boolean actual = CollectionsUtil.sameSize(src, dest);

        assertThat(actual, is(expected));
    }

    private Object[] sameSizeValues() {
        return new Object[]{
                new Object[]{newList(5), newList(5), true},
                new Object[]{newList(3), newList(2), false},
                new Object[]{newList(0), newList(0), false},
                new Object[]{null, null, false}
        };
    }

    @Test
    public void should_GetFirstItemCorrect() {
        List<Object> list = newList(5);

        String expected = "value";
        list.add(0, expected);

        Object actual = CollectionsUtil.getFirstItem(list);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_GetFirstItemWithEmptyListCorrect() {
        Object actual = CollectionsUtil.getFirstItem(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void should_GetOptionalItemCorrect() {
        String expected = "value";

        Object actual = CollectionsUtil.getFirstItem(null, expected);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_GetLastItemCorrect() {
        List<Object> list = newList(5);

        String expected = "value";
        list.add(expected);

        Object actual = CollectionsUtil.getLastItem(list);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_GetLastItemWithEmptyListCorrect() {
        Object actual = CollectionsUtil.getLastItem(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void should_RemoveLastItemCorrect() {
        List<Object> list = newList(5);

        String expected = "value";
        list.add(expected);

        CollectionsUtil.removeLastItem(list);
        Object actual = list.get(list.size() - 1);

        assertThat(actual, is(not(equalTo(expected))));
    }

    @Test
    public void should_RemoveLastItemWithEmptyListCorrect() {
        Object actual = CollectionsUtil.removeLastItem(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void should_ReverseOrderCorrect() {
        String value1 = "value1";
        String value2 = "value2";

        List<String> list = new ArrayList<>();
        list.add(value1);
        list.add(value2);

        List<String> newList = CollectionsUtil.reverseOrder(list);

        Collections.reverse(newList);

        boolean areEqual = list.stream()
                .allMatch(item -> newList.indexOf(item) == list.indexOf(item));

        assertThat(areEqual, is(true));
    }

    private List<Object> newList(int size) {
        List<Object> list = CollectionsUtil.emptyList();

        for (int i = 0; i < size; i++) {
            list.add(new Object());
        }

        return list;
    }

}