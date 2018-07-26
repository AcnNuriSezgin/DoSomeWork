package nurisezgin.com.dosomework;

import com.annimon.stream.function.Supplier;

import nurisezgin.com.dosomework.async.ConsumerAsyncListener;
import nurisezgin.com.dosomework.async.ExpectAsync;
import nurisezgin.com.dosomework.async.PredicateAsync;
import nurisezgin.com.dosomework.async.PredicateAsyncListener;
import nurisezgin.com.dosomework.utils.CollectionsUtil;

/**
 * Created by nuri on 25.07.2018
 */
public final class AsyncExecutor<T> implements PredicateAsyncListener, ConsumerAsyncListener {

    private Supplier<T> supplier;
    private ExpectAsync<T> expect;
    private ExpectAsync<T> current;

    public AsyncExecutor(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public ExpectAsync<T> expect(PredicateAsync<T> condition) {
        expect = new ExpectAsync<>(this, condition);
        return expect;
    }

    public void done() {
        work(expect);
    }

    private void work(ExpectAsync<T> expect) {
        current = expect;

        current.condition.addListener(this);
        current.condition.test(supplier.get());
    }

    @Override
    public void onFinished() {
        current.queuePositiveActions.remove().removeListener(this);

        if (CollectionsUtil.shouldNotEmpty(current.queuePositiveActions)) {
            execThen();
        }
    }

    private void execThen() {
        current.queuePositiveActions.peek().addListener(this);
        current.queuePositiveActions.peek().accept(supplier.get());
    }

    @Override
    public void onTrue() {
        current.condition.removeListener(this);
        execThen();
    }

    @Override
    public void onFalse() {
        current.condition.removeListener(this);

        if (current.or != null) {
            work(current.or);
        } else if (current.negativeAction != null){
            current.negativeAction.addListener(this);
            current.negativeAction.accept(supplier.get());
        }
    }
}
