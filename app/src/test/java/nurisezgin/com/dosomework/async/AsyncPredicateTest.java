package nurisezgin.com.dosomework.async;

import org.junit.Before;
import org.junit.Test;

import nurisezgin.com.dosomework.testutils.TestAsyncPredicate;
import nurisezgin.com.dosomework.utils.StringUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 26.07.2018
 */
public class AsyncPredicateTest {

    public static final String SAMPLE_VALUE = "Value";
    private TestAsyncPredicate predicate;

    @Before
    public void setUp_() {
        predicate = new TestAsyncPredicate(StringUtil::shouldNotEmpty);
    }

    @Test
    public void should_CallOnTrueCorrect() throws InterruptedException {
        AsyncPredicateListener mockListener = newMockListener();
        predicate.addListener(mockListener);
        predicate.test(SAMPLE_VALUE);

        Thread.sleep(predicate.waitDuration() + 50);

        verify(mockListener).onTrue();
    }

    @Test
    public void should_CallOnFalseCorrect() throws InterruptedException {
        final String str = "";

        AsyncPredicateListener mockListener = newMockListener();
        predicate.addListener(mockListener);
        predicate.test(str);

        Thread.sleep(predicate.waitDuration() + 50);

        verify(mockListener).onFalse();
    }

    @Test
    public void should_AddCorrect() {
        final int expected = 1;

        predicate.addListener(newMockListener());

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NeverAddNullListenerCorrect() {
        final int expected = 0;

        predicate.addListener(null);

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RemoveCorrect() {
        final int expected = 0;

        AsyncPredicateListener mockListener = newMockListener();
        predicate.addListener(mockListener);
        predicate.removeListener(mockListener);

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NotRemoveCorrect() {
        final int expected = 1;

        AsyncPredicateListener mockListener = newMockListener();
        AsyncPredicateListener mockListener1 = newMockListener();
        predicate.addListener(mockListener1);
        predicate.removeListener(mockListener);

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NeverRemoveNullListenerCorrect() {
        final int expected = 1;

        predicate.addListener(newMockListener());
        predicate.removeListener(null);

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RemoveReleasedListenerCorrect() throws InterruptedException {
        final int expected = 0;

        AsyncPredicateListener mockListener = newMockListener();
        predicate.addListener(mockListener);

        mockListener = null;
        System.gc();

        predicate.test(SAMPLE_VALUE);
        Thread.sleep(predicate.waitDuration() + 50);

        int actual = predicate.listenerCount();

        assertThat(actual, is(expected));
    }

    private AsyncPredicateListener newMockListener() {
        AsyncPredicateListener mockListener = mock(AsyncPredicateListener.class);
        return mockListener;
    }

}