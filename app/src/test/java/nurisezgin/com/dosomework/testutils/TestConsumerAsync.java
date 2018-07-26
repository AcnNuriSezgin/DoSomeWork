package nurisezgin.com.dosomework.testutils;

import com.annimon.stream.function.Consumer;

import nurisezgin.com.dosomework.async.ConsumerAsync;

/**
 * Created by nuri on 26.07.2018
 */
public class TestConsumerAsync extends ConsumerAsync<String> {

    private static final long WAIT_DURATION = 350;
    private Consumer<String> consumer;

    public TestConsumerAsync(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void accept(String s) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                consumer.accept(s);
                onFinished();
            }
        }.start();
    }
}
