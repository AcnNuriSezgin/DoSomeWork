package nurisezgin.com.dosomework.async;

import com.annimon.stream.function.Consumer;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 26.07.2018
 */
public class SimpleAsyncConsumerTest {

    @Test
    public void should_OnFinishedCorrect() {
        final Consumer<String> consumer = (str) -> { };
        SimpleAsyncConsumer<String> asyncConsumer = new SimpleAsyncConsumer<>(consumer);

        AsyncConsumerListener mockListener = mock(AsyncConsumerListener.class);
        asyncConsumer.addListener(mockListener);

        asyncConsumer.accept(null);

        verify(mockListener).onFinished();
    }

}