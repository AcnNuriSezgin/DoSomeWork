package nurisezgin.com.dosomework.async;

/**
 * Created by nuri on 26.07.2018
 */
public interface IAsyncExecutor {

    void done();

    void doOnEnd(Runnable runnable);

    void onPipelineFinished();

}
