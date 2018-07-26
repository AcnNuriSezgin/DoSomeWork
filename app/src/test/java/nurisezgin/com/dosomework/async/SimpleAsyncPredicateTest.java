package nurisezgin.com.dosomework.async;

import org.junit.Before;
import org.junit.Test;

import nurisezgin.com.dosomework.utils.StringUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 26.07.2018
 */
public class SimpleAsyncPredicateTest {

    private SimpleAsyncPredicate<String> asyncPredicate;

    @Before
    public void setUp_() {
        asyncPredicate = new SimpleAsyncPredicate<>(StringUtil::shouldNotEmpty);
    }

    @Test
    public void should_OnTrueCorrect() {
        final String value = "value";

        AsyncPredicateListener mockListener = mock(AsyncPredicateListener.class);

        asyncPredicate.addListener(mockListener);
        asyncPredicate.test(value);

        verify(mockListener).onTrue();
    }

    @Test
    public void should_OnFalseCorrect() {
        final String value = "";

        AsyncPredicateListener mockListener = mock(AsyncPredicateListener.class);

        asyncPredicate.addListener(mockListener);
        asyncPredicate.test(value);

        verify(mockListener).onFalse();
    }

}