package nurisezgin.com.dosomework;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import nurisezgin.com.dosomework.testutils.TestAsyncConsumer;
import nurisezgin.com.dosomework.testutils.TestObject;
import nurisezgin.com.dosomework.testutils.TestAsyncPredicate;
import nurisezgin.com.dosomework.utils.StringUtil;

import static nurisezgin.com.dosomework.DoSomeWork.thatAsync;
import static nurisezgin.com.dosomework.utils.StringUtil.len;
import static org.awaitility.Awaitility.await;

/**
 * Created by nuri on 25.07.2018
 */
public class AsyncExecutorTest {

    @Test
    public void should_ThenActionPerformCorrect() {
        final int expected = 1;
        TestObject object = new TestObject();

        thatAsync(() -> "Value")
                .expect(new TestAsyncPredicate(StringUtil::shouldNotEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(expected)))
                .done();

        waitUntil(expected, object);
    }

    @Test
    public void should_AllThenActionsPerformCorrect() {
        final int expected = 3;
        TestObject object = new TestObject();

        thatAsync(() -> "Value")
                .expect(new TestAsyncPredicate(StringUtil::shouldNotEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(1)))
                .then(new TestAsyncConsumer(str -> object.setId(2)))
                .then(new TestAsyncConsumer(str -> object.setId(expected)))
                .done();

        waitUntil(expected, object);
    }

    @Test
    public void should_OtherwiseActionsPerformCorrect() {
        final int expected = -1;
        TestObject object = new TestObject();

        thatAsync(() -> "")
                .expect(new TestAsyncPredicate(StringUtil::shouldNotEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(1)))
                .otherwise(new TestAsyncConsumer(str -> object.setId(-1)));

        waitUntil(expected, object);
    }

    @Test
    public void should_OrThenActionPerformCorrect() {
        final int expected = 11;
        TestObject object = new TestObject();

        thatAsync(() -> "Value")
                .expect(new TestAsyncPredicate(StringUtil::shouldEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(1)))
                .or(new TestAsyncPredicate(StringUtil::shouldNotEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(expected)))
                .done();

        waitUntil(expected, object);
    }

    @Test
    public void should_OrOtherwiseActionPerformCorrect() {
        final int expected = -2;
        TestObject object = new TestObject();

        thatAsync(() -> "Value")
                .expect(new TestAsyncPredicate(StringUtil::shouldEmpty))
                .then(new TestAsyncConsumer(str -> object.setId(1)))
                .or(new TestAsyncPredicate(str -> len(str) < 3))
                .then(new TestAsyncConsumer(str -> object.setId(11)))
                .then(new TestAsyncConsumer(str -> object.setId(12)))
                .then(new TestAsyncConsumer(str -> object.setId(13)))
                .otherwise(new TestAsyncConsumer(str -> object.setId(expected)));

        waitUntil(expected, object);
    }

    private void waitUntil(int expected, TestObject object) {
        await().atMost(5, TimeUnit.SECONDS)
                .until(() -> object.getId() == expected);
    }

}