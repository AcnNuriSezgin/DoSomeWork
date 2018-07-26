package nurisezgin.com.dosomework.testutils;

import com.annimon.stream.function.Predicate;

import nurisezgin.com.dosomework.async.AsyncPredicate;

/**
 * Created by nuri on 26.07.2018
 */
public class TestAsyncPredicate extends AsyncPredicate<String> {

    private static final long WAIT_DURATION = 150;
    private Predicate<String> predicate;

    public TestAsyncPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void test(String str) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean isSuccess = predicate.test(str);

                if (isSuccess) {
                    System.out.println("onTrue");
                    onTrue();
                } else {
                    System.out.println("onFalse");
                    onFalse();
                }
            }
        }.start();
    }

    public long waitDuration() {
        return WAIT_DURATION;
    }

}
