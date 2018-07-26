package nurisezgin.com.dosomework.async;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nuri on 25.07.2018
 */
public abstract class AsyncPredicate<T> {

    private CopyOnWriteArrayList<WeakReference<AsyncPredicateListener>>
            listeners = new CopyOnWriteArrayList<>();

    public abstract void test(T t);

    protected final void onTrue() {
        Stream.of(listeners)
                .forEach(weakListener -> onListener(weakListener, l -> l.onTrue()));
    }

    protected final void onFalse() {
        Stream.of(listeners)
                .forEach(weakListener -> onListener(weakListener, l -> l.onFalse()));
    }

    private void onListener(WeakReference<AsyncPredicateListener> weakListener,
                            Consumer<AsyncPredicateListener> consumer) {

        AsyncPredicateListener l = weakListener.get();
        if (l != null) {
            synchronized (l) {
                consumer.accept(l);
            }
        } else {
            listeners.remove(weakListener);
        }
    }

    public final void addListener(AsyncPredicateListener l) {
        if (l == null) {
            return;
        }

        listeners.add(new WeakReference<>(l));
    }

    public final void removeListener(AsyncPredicateListener l) {
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
