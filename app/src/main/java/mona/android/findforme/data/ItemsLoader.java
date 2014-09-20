package mona.android.findforme.data;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import hugo.weaving.DebugLog;
import mona.android.findforme.data.api.FindForMeService;
import mona.android.findforme.data.api.Type;
import mona.android.findforme.data.api.model.FindItem;
import mona.android.findforme.data.api.model.ResponseToFindItemList;
import mona.android.findforme.data.api.model.Sort;
import mona.android.findforme.data.rx.EndObserver;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by cheikhna on 18/08/2014.
 */
@Singleton
public class ItemsLoader {

    private final FindForMeService mFindForMeService;

    private final Map<Type, List<FindItem>> mItemsCache = new LinkedHashMap<Type, List<FindItem>>();
    private final Map<Type, PublishSubject<List<FindItem>>> mItemsRequests = new LinkedHashMap<Type, PublishSubject<List<FindItem>>>();

    //@Inject
    public ItemsLoader(FindForMeService service){
        mFindForMeService = service;
    }

    @DebugLog
    public Subscription loadItems(final Type type, final Sort sort, Observer<List<FindItem>> observer){
        Log.i("TEST", " ItemsLoader's loadItems loadItems is called ");
        List<FindItem> items = mItemsCache.get(type);
        if(items != null){
            observer.onNext(items); //emit what's cached
        }

        PublishSubject<List<FindItem>> itemRequest = mItemsRequests.get(type);
        if(itemRequest != null){
            return itemRequest.subscribe(observer);
        }

        itemRequest = PublishSubject.create();
        mItemsRequests.put(type, itemRequest);

        Subscription subscription = itemRequest.subscribe(observer);
        itemRequest.subscribe(new EndObserver<List<FindItem>>() {
            @DebugLog
            @Override
            public void onEnd(){
                mItemsRequests.remove(type);
            }
            @DebugLog
            @Override
            public void onNext(List<FindItem> findItems) {
                mItemsCache.put(type, findItems);
            }
        });

        mFindForMeService.listItems(type, sort, 1)
                .map(new ResponseToFindItemList())
                .subscribeOn(Schedulers.immediate()) //temp deleting .io
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemRequest);

        return subscription;
    }


}