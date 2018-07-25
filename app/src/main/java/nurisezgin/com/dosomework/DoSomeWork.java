package nurisezgin.com.dosomework;

import com.annimon.stream.function.Supplier;

import nurisezgin.com.dosomework.sync.SyncExecutor;

/**
 * Created by nuri on 25.07.2018
 */
public final class DoSomeWork<T> {

    public static <T> SyncExecutor<T> that(Supplier<T> supplier) {
        return new SyncExecutor<>(supplier);
    }

    public static <T> DoSomeWork<T> thatAsync(Supplier<T> supplier) {
        throw new RuntimeException("Not implemented yet!");
    }
}
