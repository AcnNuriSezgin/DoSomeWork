package nurisezgin.com.dosomework.async;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nurisezgin.com.dosomework.AsyncExecutor;

/**
 * Created by nuri on 25.07.2018
 */
public class ExpectAsync<T> {

    private AsyncExecutor<T> executor;
    public PredicateAsync<T> condition;
    public List<ConsumerAsync<T>> positiveActions = new ArrayList<>();
    public Queue<ConsumerAsync<T>> queuePositiveActions;
    public ExpectAsync<T> or;
    public ConsumerAsync<T> negativeAction;

    public ExpectAsync(AsyncExecutor<T> executor, PredicateAsync<T> condition) {
        this.executor = executor;
        this.condition = condition;
    }

    public void is(T t) {
        condition.test(t);
    }

    public ExpectAsync<T> then(ConsumerAsync<T> then) {
        positiveActions.add(then);
        return this;
    }

    public ExpectAsync<T> or(PredicateAsync<T> condition) {
        ExpectAsync<T> expectAsync = new ExpectAsync<>(executor, condition);
        this.or = expectAsync;
        return expectAsync;
    }

    public void otherwise(ConsumerAsync<T> negativeAction) {
        this.negativeAction = negativeAction;
        done();
    }

    public void done() {
        queuePositiveActions = new LinkedList<>(positiveActions);

        executor.done();
    }
}
