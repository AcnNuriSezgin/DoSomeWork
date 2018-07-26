package nurisezgin.com.dosomework.async;

import com.annimon.stream.Stream;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nuri on 25.07.2018
 */
public abstract class AsyncConsumer<T> {

    private CopyOnWriteArrayList<WeakReference<AsyncConsumerListener>>
            listeners = new CopyOnWriteArrayList<>();

    public abstract void accept(T t);

    protected final void onFinished() {
        Stream.of(listeners)
                .forEach(this::onListener);
    }

    private void onListener(WeakReference<AsyncConsumerListener> weakListener) {
        AsyncConsumerListener l = weakListener.get();
        if (l != null) {
            synchronized (l) {
                l.onFinished();
            }
        } else {
            listeners.remove(weakListener);
        }
    }

    public final void addListener(AsyncConsumerListener l) {
        if (l == null) {
            return;
        }

        listeners.add(new WeakReference<>(l));
    }

    public final void removeListener(AsyncConsumerListener l) {
        if (l == null) {
            return;
        }

        Stream.of(listeners)
                .filter(weakListener -> weakListener.get() != null)
                .filter(weakListener -> weakListener.get().equals(l))
                .forEach(weakListener -> listeners.remove(weakListener));
    }

    public final int listenerCount() {
        return listeners.size();
    }

}
