package mona.android.findforme.data.rx;

import rx.Observer;

public abstract class EndlessObserver<T> implements Observer<T> {
  @Override public void onCompleted() {
  }

  @Override public void onError(Throwable throwable) {
  }
}
