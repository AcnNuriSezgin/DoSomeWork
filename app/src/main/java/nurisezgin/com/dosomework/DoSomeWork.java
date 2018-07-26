package nurisezgin.com.dosomework;

import com.annimon.stream.function.Supplier;

/**
 * Created by nuri on 25.07.2018
 */
public final class DoSomeWork {

    private DoSomeWork() { }

    public static <T> SyncExecutor<T> that(Supplier<T> supplier) {
        return new SyncExecutor<>(supplier);
    }

    public static <T> AsyncExecutor<T> thatAsync(Supplier<T> supplier) {
        return new AsyncExecutor<>(supplier);
    }
}
