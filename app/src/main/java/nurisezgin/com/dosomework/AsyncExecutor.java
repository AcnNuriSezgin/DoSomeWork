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
    private ExpectAsync<T> cursor;
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
        if (cursor != null) {
            doOnEnd.run();
        }

        cursor = null;
    }

    private void work(ExpectAsync<T> expect) {
        cursor = expect;
        cursor.is(supplier.get(), this);
    }

    @Override
    public void onTrue() {
        if (cursor == null) {
            return;
        }

        cursor.removePredicateListener(this);
        cursor.doThen(supplier.get(), this);
    }

    @Override
    public void onFalse() {
        if (cursor == null) {
            return;
        }

        cursor.removePredicateListener(this);

        if (cursor.hasOrCondition()) {
            work(cursor.or());
        } else {
            cursor.doOtherwise(supplier.get(), this);
        }
    }

    @Override
    public void onFinished() {
        if (cursor == null) {
            return;
        }

        cursor.finishedAction(this);
        cursor.doThen(supplier.get(), this);
    }

    @Override
    public void terminate() {
        cursor = null;
    }
}
