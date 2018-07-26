package nurisezgin.com.dosomework.async;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nurisezgin.com.dosomework.utils.CollectionsUtil;

/**
 * Created by nuri on 25.07.2018
 */
public class ExpectAsync<T> {

    private IAsyncExecutor executor;
    private AsyncPredicate<T> condition;
    private List<AsyncConsumer<T>> positiveActions = new ArrayList<>();
    private Queue<AsyncConsumer<T>> actionQueue;
    private ExpectAsync<T> or;
    private AsyncConsumer<T> negativeAction = new EmptyAsyncConsumer();

    public ExpectAsync(IAsyncExecutor executor, AsyncPredicate<T> condition) {
        this.executor = executor;
        this.condition = condition;
    }

    public AsyncPredicate<T> getCondition() {
        return condition;
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

    public List<AsyncConsumer<T>> getPositiveActions() {
        return positiveActions;
    }

    public void doThen(T value, AsyncConsumerListener listener) {
        if (CollectionsUtil.shouldNotEmpty(actionQueue)) {
            actionQueue.peek().addListener(listener);
            actionQueue.peek().accept(value);
        } else {
            executor.onPipelineFinished();
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

    public AsyncConsumer<T> getNegativeAction() {
        return negativeAction;
    }

    public void doOtherwise(T t, AsyncConsumerListener listener) {
        actionQueue.add(negativeAction);
        doThen(t, listener);
    }

    public Terminable otherwise(AsyncConsumer<T> negativeAction) {
        this.negativeAction = negativeAction;
        return done();
    }

    public Terminable done() {
        actionQueue = new LinkedList<>(positiveActions);
        executor.done();

        return executor;
    }

    public ExpectAsync<T> doOnEnd(Runnable r) {
        executor.doOnEnd(r);
        return this;
    }
}
