package mona.android.findforme.state.persistence;

import hugo.weaving.DebugLog;
import mona.android.findforme.model.UserProfile;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by cheikhna on 31/08/2014.
 */
public class AsyncDatabaseHelperImpl implements AsyncDatabaseHelper {

    //private final BackgroundExecutor mExecutor;
    private final DatabaseHelper mDbHelper;

    public AsyncDatabaseHelperImpl(DatabaseHelper dbHelper){
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<UserProfile> getUserProfile(final String username) {
        return Observable.create(new Observable.OnSubscribe<UserProfile>(){
                    @Override
                    public void call(Subscriber<? super UserProfile> subscriber) {
                        subscriber.onNext(mDbHelper.getUserProfile(username));
                        subscriber.onCompleted();
                    }
                })
                //.observeOn(AndroidSchedulers.mainThread()) // may need this
                .subscribeOn(Schedulers.newThread())
                .first(); //not sure of this first ?
        /**lola
         may need to force subscription for immediate execution
         obs.subscribe();
         */
    }

    @DebugLog
    @Override
    public void put(final UserProfile profile) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @DebugLog
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    android.os.Debug.waitForDebugger();
                    mDbHelper.put(profile);
                    subscriber.onCompleted();
                }
                catch(Throwable t){
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onError(t);
                    }
                }
            }
        })
        .subscribeOn(Schedulers.newThread())
        .subscribe();
    }

    @Override
    public void delete(final UserProfile profile) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                mDbHelper.delete(profile);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.newThread())
        .subscribe();
    }

    @Override
    public void close() {

    }

}