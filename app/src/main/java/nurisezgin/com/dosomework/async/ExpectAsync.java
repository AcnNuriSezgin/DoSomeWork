package nurisezgin.com.dosomework.async;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nurisezgin.com.dosomework.AsyncExecutor;
import nurisezgin.com.dosomework.utils.CollectionsUtil;

/**
 * Created by nuri on 25.07.2018
 */
public class ExpectAsync<T> {

    private AsyncExecutor<T> executor;
    private AsyncPredicate<T> condition;
    private List<AsyncConsumer<T>> positiveActions = new ArrayList<>();
    private Queue<AsyncConsumer<T>> actionQueue;
    private ExpectAsync<T> or;
    private AsyncConsumer<T> negativeAction = new EmptyAsyncConsumer();

    public ExpectAsync(AsyncExecutor<T> executor, AsyncPredicate<T> condition) {
        this.executor = executor;
        this.condition = condition;
    }

    public void is(T t, AsyncPredicateListener listener) {
        condition.addListener(listener);
        condition.test(t);
    }

    public void removePredicateListener(AsyncPredicateListener listener) {
        condition.removeListener(listener);
    }

    public ExpectAsync<T> then(AsyncConsumer<T> then) {
        positiveActions.add(then);
        return this;
    }

    public void doThen(T value, AsyncConsumerListener listener) {
        if (CollectionsUtil.shouldNotEmpty(actionQueue)) {
            actionQueue.peek().addListener(listener);
            actionQueue.peek().accept(value);
        }
    }

    public void finishedAction(AsyncConsumerListener listener) {
        actionQueue.remove().removeListener(listener);
    }

    public ExpectAsync<T> or(AsyncPredicate<T> condition) {
        ExpectAsync<T> expectAsync = new ExpectAsync<>(executor, condition);
        this.or = expectAsync;
        return expectAsync;
    }

    public boolean hasOrCondition() {
        return or != null;
    }

    public ExpectAsync<T> or() {
        return or;
    }

    public void otherwise(AsyncConsumer<T> negativeAction) {
        this.negativeAction = negativeAction;
        done();
    }

    public void doOtherwise(T t, AsyncConsumerListener listener) {
        actionQueue.add(negativeAction);
        doThen(t, listener);
    }

    public void done() {
        actionQueue = new LinkedList<>(positiveActions);
        executor.done();
    }
}
