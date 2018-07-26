package nurisezgin.com.dosomework.async;

import org.junit.Before;
import org.junit.Test;

import nurisezgin.com.dosomework.testutils.TestAsyncConsumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 26.07.2018
 */
public class AsyncConsumerTest {

    private TestAsyncConsumer consumer;

    @Before
    public void setUp_() {
        consumer = new TestAsyncConsumer(System.out::println);
    }

    @Test
    public void should_CallOnFinishedCorrect() throws InterruptedException {
        AsyncConsumerListener mockListener = newMockListener();
        consumer.addListener(mockListener);
        consumer.accept("TestString");

        Thread.sleep(consumer.waitDuration() + 50);

        verify(mockListener).onFinished();
    }

    @Test
    public void should_AddCorrectCorrect() {
        final int expected = 1;

        consumer.addListener(newMockListener());

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NeverAddNullListenerCorrect() {
        final int expected = 0;

        consumer.addListener(null);

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RemoveCorrect() {
        final int expected = 0;

        AsyncConsumerListener mockListener = newMockListener();
        consumer.addListener(mockListener);
        consumer.removeListener(mockListener);

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NotRemoveCorrect() {
        final int expected = 1;

        AsyncConsumerListener mockListener = newMockListener();
        AsyncConsumerListener mockListener1 = newMockListener();
        consumer.addListener(mockListener1);
        consumer.removeListener(mockListener);

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_NeverRemoveNullListenerCorrect() {
        final int expected = 1;

        consumer.addListener(newMockListener());
        consumer.removeListener(null);

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_RemoveReleasedListenerCorrect() throws InterruptedException {
        final int expected = 0;

        AsyncConsumerListener mockListener = newMockListener();
        consumer.addListener(mockListener);

        mockListener = null;
        System.gc();

        consumer.accept("");
        Thread.sleep(consumer.waitDuration() + 50);

        int actual = consumer.listenerCount();

        assertThat(actual, is(expected));
    }

    private AsyncConsumerListener newMockListener() {
        AsyncConsumerListener mockListener = mock(AsyncConsumerListener.class);
        return mockListener;
    }
}