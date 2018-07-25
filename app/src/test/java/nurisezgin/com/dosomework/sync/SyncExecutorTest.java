package nurisezgin.com.dosomework.sync;

import org.junit.Test;

import nurisezgin.com.dosomework.utils.StringUtil;

import static nurisezgin.com.dosomework.DoSomeWork.that;
import static nurisezgin.com.dosomework.utils.StringUtil.len;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 25.07.2018
 */
public class SyncExecutorTest {

    @Test
    public void should_ThenActionPerformCorrect() {
        final int expected = 1;
        TestObject object = new TestObject();

        that(() -> "Value")
                .expect(StringUtil::shouldNotEmpty)
                .then(str -> object.setId(expected))
                .done();

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_AllThenActionsPerformCorrect() {
        final int expected = 3;
        TestObject object = new TestObject();

        that(() -> "Value")
                .expect(StringUtil::shouldNotEmpty)
                .then(str -> object.setId(1))
                .then(str -> object.setId(2))
                .then(str -> object.setId(expected))
                .done();

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_OtherwiseActionsPerformCorrect() {
        final int expected = -1;
        TestObject object = new TestObject();

        that(() -> "")
                .expect(StringUtil::shouldNotEmpty)
                .then(str -> object.setId(1))
                .otherwise(str -> object.setId(-1));

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_OrThenActionPerformCorrect() {
        final int expected = 11;
        TestObject object = new TestObject();

        that(() -> "Value")
                .expect(StringUtil::shouldEmpty)
                .then(str -> object.setId(1))
                .or(StringUtil::shouldNotEmpty)
                .then(str -> object.setId(expected))
                .done();

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_OrThenActionsPerformCorrect() {
        final int expected = 13;
        TestObject object = new TestObject();

        that(() -> "Value")
                .expect(StringUtil::shouldEmpty)
                .then(str -> object.setId(1))
                .or(StringUtil::shouldNotEmpty)
                .then(str -> object.setId(11))
                .then(str -> object.setId(12))
                .then(str -> object.setId(expected))
                .done();

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_OrOtherwiseActionPerformCorrect() {
        final int expected = -2;
        TestObject object = new TestObject();

        that(() -> "Value")
                .expect(StringUtil::shouldEmpty)
                .then(str -> object.setId(1))
                .or(str -> len(str) < 3)
                .then(str -> object.setId(11))
                .then(str -> object.setId(12))
                .then(str -> object.setId(13))
                .otherwise(str -> object.setId(expected));

        int actual = object.getId();

        assertThat(actual, is(expected));
    }

    private static class TestObject {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}