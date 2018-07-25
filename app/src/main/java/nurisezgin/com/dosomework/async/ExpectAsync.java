package nurisezgin.com.dosomework.async;

import nurisezgin.com.dosomework.DoSomeWork;

/**
 * Created by nuri on 25.07.2018
 */
public class ExpectAsync<T> {

    private DoSomeWork<T> executor;
    private PredicateAsync<T> condition;

    public ExpectAsync(DoSomeWork<T> executor, PredicateAsync<T> condition) {
        this.executor = executor;
        this.condition = condition;
    }

    public void is(T t) {
        condition.test(t);
    }
}
