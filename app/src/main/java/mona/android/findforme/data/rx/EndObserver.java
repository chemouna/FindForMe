package mona.android.findforme.data.rx;

import rx.Observer;

/**
 * Created by cheikhna on 18/08/2014.
 */
public abstract class EndObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable e) {
        onEnd();
    }

    public abstract void onEnd();

}
