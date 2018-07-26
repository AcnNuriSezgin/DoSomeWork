package nurisezgin.com.dosomework.async;

import com.annimon.stream.function.Consumer;

/**
 * Created by nuri on 26.07.2018
 */
public final class SimpleAsyncConsumer<T> extends AsyncConsumer<T> {

    private final Consumer<T> consumer;

    public SimpleAsyncConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        consumer.accept(t);
        onFinished();
    }
}
