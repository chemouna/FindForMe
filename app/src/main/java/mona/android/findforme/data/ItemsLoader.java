package mona.android.findforme.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    @Inject
    public ItemsLoader(FindForMeService service){
        mFindForMeService = service;
    }

    public Subscription loadItems(final Type type, Observer<List<FindItem>> observer){
        List<FindItem> items = mItemsCache.get(type);
        if(items != null){
            observer.onNext(items); //emit what's cached
        }

        PublishSubject<List<FindItem>> itemRequest = mItemsRequests.get(type);
        if(itemRequest !=null){
            return itemRequest.subscribe(observer);
        }

        itemRequest = PublishSubject.create();
        mItemsRequests.put(type, itemRequest);

        Subscription subscription = itemRequest.subscribe(observer);
        itemRequest.subscribe(new EndObserver<List<FindItem>>() {
            @Override
            public void onEnd(){
                mItemsRequests.remove(type);
            }

            @Override
            public void onNext(List<FindItem> findItems) {
                mItemsCache.put(type, findItems);
            }
        });

        //To test this part we need some response from server
        mFindForMeService.listItems(type, Sort.POPULAR, 1)
                .map(new ResponseToFindItemList())
                .flatMap(new Func1<List<FindItem>, Observable<FindItem>>() {
                    @Override
                    public Observable<FindItem> call(List<FindItem> items) {
                        return Observable.from(items);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemRequest);

        return subscription;
    }

}

//repository pattern to use : https://gist.github.com/pieces029/5e92f9003fa1a4ebc59b