package nurisezgin.com.dosomework.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by nuri on 25.07.2018
 */
@RunWith(JUnitParamsRunner.class)
public class StringUtilTest {

    @Test
    @Parameters(method = "lenValues")
    public void should_LenCorrect(String value, int expected) {
        int actual = StringUtil.len(value);

        assertThat(actual, is(expected));
    }

    private Object[] lenValues() {
        return new Object[]{
                new Object[]{"value", 5},
                new Object[]{"", 0},
                new Object[]{null, 0}
        };
    }

    @Test
    @Parameters(method = "normalizeValues")
    public void should_NormalizeCorrect(String value, String expected) {
        String actual = StringUtil.normalize(value);

        assertThat(actual, is(equalTo(expected)));
    }

    private Object[] normalizeValues() {
        return new Object[]{
                new Object[]{"value", "value"},
                new Object[]{"", ""},
                new Object[]{null, ""}
        };
    }

    @Test
    @Parameters({"abc, opt, abc", ", opt, opt"})
    public void should_NormalizeWithOptionalCorrect(String value, String optional, String expected) {
        String actual = StringUtil.normalizeWith(value, optional);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @Parameters({"abc, suf, abcsuf", ", suf,"})
    public void should_IsNotEmptyAddSuffixCorrect(String value, String suffix, String expected) {
        String actual = StringUtil.isNotEmptyAddSuffix(value, suffix);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @Parameters({"abc, pre, preabc", ", pre,"})
    public void should_IsNotEmptyAddPrefixCorrect(String value, String suffix, String expected) {
        String actual = StringUtil.isNotEmptyAddPrefix(value, suffix);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @Parameters(method = "shouldEmptyValues")
    public void should_EmptyCorrect(String value, boolean expected) {
        boolean actual = StringUtil.shouldEmpty(value);

        assertThat(actual, is(expected));
    }

    private Object[] shouldEmptyValues() {
        return new Object[]{
                new Object[]{"value", false},
                new Object[]{"", true},
                new Object[]{null, true}
        };
    }

    @Test
    @Parameters(method = "shouldNotEmptyValues")
    public void should_NotEmptyCorrect(String value, boolean expected) {
        boolean actual = StringUtil.shouldNotEmpty(value);

        assertThat(actual, is(expected));
    }

    private Object[] shouldNotEmptyValues() {
        return new Object[]{
                new Object[]{"value", true},
                new Object[]{"", false},
                new Object[]{null, false}
        };
    }

    @Test
    @Parameters(method = "shouldRawStringEmptyValues")
    public void should_RawStringEmptyCorrect(String value, boolean expected) {
        boolean actual = StringUtil.shouldRawStringEmpty(value);

        assertThat(actual, is(expected));
    }

    private Object[] shouldRawStringEmptyValues() {
        return new Object[]{
                new Object[]{"value", false},
                new Object[]{"   ", true},
                new Object[]{"", true},
                new Object[]{null, true}
        };
    }

    @Test
    @Parameters(method = "shouldRawStringNotEmptyValues")
    public void should_RawStringNotEmptyCorrect(String value, boolean expected) {
        boolean actual = StringUtil.shouldRawStringNotEmpty(value);

        assertThat(actual, is(expected));
    }

    private Object[] shouldRawStringNotEmptyValues() {
        return new Object[]{
                new Object[]{"value", true},
                new Object[]{"   ", false},
                new Object[]{"", false},
                new Object[]{null, false}
        };
    }

    @Test
    @Parameters(method = "toStringValues")
    public void should_ToStringCorrect(Object value, String expected) {
        String actual = StringUtil.toString(value);

        assertThat(actual, is(equalTo(expected)));
    }

    private Object[] toStringValues() {
        return new Object[]{
                new Object[]{"value", "value"},
                new Object[]{null, ""}
        };
    }

    @Test
    @Parameters(method = "RemoveNonNumericsValues")
    public void should_RemoveNonNumeric(String value, String expected) {
        String actual = StringUtil.removeNonNumeric(value);

        assertThat(actual, is(equalTo(expected)));
    }

    private Object[] RemoveNonNumericsValues() {
        return new Object[]{
                new Object[]{"123", "123"},
                new Object[]{"abc123", "123"},
                new Object[]{null, ""}
        };
    }

    @Test
    @Parameters(method = "JoinValues")
    public void should_JoinCorrect(String delimiter, Iterable iterable, String expected) {
        String actual = StringUtil.join(delimiter, iterable);

        assertThat(actual, is(equalTo(expected)));
    }

    private Object[] JoinValues() {
        return new Object[] {
                new Object[]{",", Arrays.asList("a", "b", "c"), "a,b,c"},
                new Object[]{null, Arrays.asList("a", "b", "c"), "abc"},
                new Object[]{null, new ArrayList<String>(), ""},
                new Object[]{",", null, ""}
        };
    }

}