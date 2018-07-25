package nurisezgin.com.dosomework.sync;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuri on 25.07.2018
 */
public final class Expect<T> {

    private SyncExecutor<T> executor;
    private Predicate<T> condition;
    private List<Consumer<T>> positiveActions = new ArrayList<>();
    private Expect<T> or;
    private Consumer<T> negativeAction = o -> {};

    public Expect(SyncExecutor<T> executor, Predicate<T> condition) {
        this.executor = executor;
        this.condition = condition;
    }

    public boolean is(T t) {
        return condition.test(t);
    }

    public void doThen(T t) {
        Stream.of(positiveActions)
                .forEach(action -> action.accept(t));
    }

    public void doOtherwise(T t) {
        negativeAction.accept(t);
    }

    public Expect<T> or() {
        return or;
    }

    public boolean hasOrCondition() {
        return or != null;
    }

    public Expect<T> then(Consumer<T> then) {
        positiveActions.add(then);
        return this;
    }

    public Expect<T> or(Predicate<T> condition) {
        Expect<T> expect = new Expect<>(executor, condition);
        this.or = expect;
        return expect;
    }

    public void otherwise(Consumer<T> negativeAction) {
        this.negativeAction = negativeAction;
        done();
    }

    public void done() {
        executor.done();
    }

}
