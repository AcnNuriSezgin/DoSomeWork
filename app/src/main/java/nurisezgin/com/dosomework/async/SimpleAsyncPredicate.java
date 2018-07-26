package nurisezgin.com.dosomework.async;

import com.annimon.stream.function.Predicate;

/**
 * Created by nuri on 26.07.2018
 */
public final class SimpleAsyncPredicate<T> extends AsyncPredicate<T> {

    private final Predicate<T> predicate;

    public SimpleAsyncPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void test(T t) {
        boolean isTrue = predicate.test(t);
        if (isTrue) {
            onTrue();
        } else {
            onFalse();
        }
    }
}
