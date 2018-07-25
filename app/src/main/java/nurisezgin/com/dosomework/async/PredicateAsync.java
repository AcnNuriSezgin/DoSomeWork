package nurisezgin.com.dosomework.async;

import com.annimon.stream.Stream;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nuri on 25.07.2018
 */
public abstract class PredicateAsync<T> {

    private CopyOnWriteArrayList<PredicateAsyncListener> listeners = new CopyOnWriteArrayList<>();

    public abstract void test(T t);

    protected final void onTrue() {
        Stream.of(listeners)
                .forEach(l -> l.onTrue());
    }

    protected final void onFalse() {
        Stream.of(listeners)
                .forEach(l -> l.onFalse());
    }

    public void addListener(PredicateAsyncListener l) {
        listeners.add(l);
    }

    public void removeListener(PredicateAsyncListener l) {
        listeners.remove(l);
    }

}
