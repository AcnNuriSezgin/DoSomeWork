package nurisezgin.com.dosomework.async;

import com.annimon.stream.Stream;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nuri on 25.07.2018
 */
public abstract class ConsumerAsync<T> {

    private CopyOnWriteArrayList<ConsumerAsyncListener> listeners = new CopyOnWriteArrayList<>();

    public abstract void accept(T t);

    protected final void onFinished() {
        Stream.of(listeners)
                .forEach(l -> l.onFinished());
    }

    public void addListener(ConsumerAsyncListener l) {
        listeners.add(l);
    }

    public void removeListener(ConsumerAsyncListener l) {
        listeners.remove(l);
    }

}
