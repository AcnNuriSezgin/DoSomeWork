package nurisezgin.com.dosomework;

import com.annimon.stream.function.Supplier;

import nurisezgin.com.dosomework.async.AsyncConsumerListener;
import nurisezgin.com.dosomework.async.AsyncPredicate;
import nurisezgin.com.dosomework.async.AsyncPredicateListener;
import nurisezgin.com.dosomework.async.ExpectAsync;
import nurisezgin.com.dosomework.async.IAsyncExecutor;

/**
 * Created by nuri on 25.07.2018
 */
public final class AsyncExecutor<T> implements IAsyncExecutor,
        AsyncPredicateListener, AsyncConsumerListener {

    private Supplier<T> supplier;
    private ExpectAsync<T> expect;
    private ExpectAsync<T> current;
    private Runnable doOnEnd = () -> { };

    public AsyncExecutor(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public ExpectAsync<T> expect(AsyncPredicate<T> condition) {
        expect = new ExpectAsync<>(this, condition);
        return expect;
    }

    @Override
    public void done() {
        work(expect);
    }

    @Override
    public void doOnEnd(Runnable r) {
        this.doOnEnd = r;
    }

    @Override
    public void onPipelineFinished() {
        current = null;
        doOnEnd.run();
    }

    private void work(ExpectAsync<T> expect) {
        current = expect;
        current.is(supplier.get(), this);
    }

    @Override
    public void onTrue() {
        current.removePredicateListener(this);
        current.doThen(supplier.get(), this);
    }

    @Override
    public void onFalse() {
        current.removePredicateListener(this);

        if (current.hasOrCondition()) {
            work(current.or());
        } else {
            current.doOtherwise(supplier.get(), this);
        }
    }

    @Override
    public void onFinished() {
        current.finishedAction(this);
        current.doThen(supplier.get(), this);
    }
}
