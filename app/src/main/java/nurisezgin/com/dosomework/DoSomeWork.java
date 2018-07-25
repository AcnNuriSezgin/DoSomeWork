package nurisezgin.com.dosomework;

import com.annimon.stream.function.Predicate;
import com.annimon.stream.function.Supplier;

/**
 * Created by nuri on 25.07.2018
 */
public final class DoSomeWork<T> {

    private Supplier<T> supplier;
    private Expect<T> expect;

    private DoSomeWork(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> DoSomeWork<T> that(Supplier<T> supplier) {
        return new DoSomeWork<>(supplier);
    }

    public Expect<T> expect(Predicate<T> condition) {
        expect = new Expect<>(this, condition);
        return expect;
    }

    public void done() {
        work(expect);
    }

    private void work(Expect<T> expect) {
        if (expect.is(supplier.get())) {
            expect.doThen(supplier.get());
        } else if (expect.hasOrCondition()) {
            work(expect.or());
        } else {
            expect.doOtherwise(supplier.get());
        }
    }
}
