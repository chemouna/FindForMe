package mona.android.findforme.test.unit;

import android.test.AndroidTestCase;

import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.Modules;
import mona.android.findforme.data.DataModule;
import mona.android.findforme.data.ItemsLoader;
import mona.android.findforme.data.api.FindForMeService;
import mona.android.findforme.data.api.Type;
import mona.android.findforme.data.api.model.FindItem;
import mona.android.findforme.data.api.model.Sort;
import mona.android.findforme.test.Injector;
import mona.android.findforme.test.unit.mock.MockFindForMeService;
import retrofit.Endpoint;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.client.Client;
import rx.Observer;
import rx.Subscription;
import rx.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by cheikhna on 14/09/2014.
 */
public class ItemsLoaderTest extends AndroidTestCase {

    /*@Inject */
    ItemsLoader itemsLoader;

    @Inject
    FindForMeService mockFindForMeService;
    //this is injected in loader tests -> how to specify it as the one being injected here

    /*@Captor
    ArgumentCaptor<Observer<List<FindItem>>> observerCaptor;*/

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ensureDexmakerCacheDir();

        Object[] arrObjects = Modules.list(FindForMeApplication.get(getContext()));
        ArrayList<Object> modules = new ArrayList<Object>(Arrays.asList(arrObjects));
        modules.add(new TestModule());
        Injector.init(modules.toArray());
        Injector.inject(this);

        MockitoAnnotations.initMocks(this);
        itemsLoader = new ItemsLoader(mockFindForMeService); //or maybe use a dagger test module
    }

    private void ensureDexmakerCacheDir() {
        File cacheDir = getContext().getCacheDir();
        System.setProperty("dexmaker.dexcache", cacheDir.toString());
    }

    /*public void testThatAllItemsAreLoaded() {
        //Observer<List<FindItem>> mockObserver =  mock(Observer.class);
        //TestObserver<List<FindItem>> observer = new TestObserver<List<FindItem>>(mockObserver);
        final AtomicReference<List<FindItem>> testResult = new AtomicReference<List<FindItem>>();
        Subscription subscription = itemsLoader.loadItems(Type.CLOTHING, Sort.POPULAR,
                new Observer<List<FindItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<FindItem> findItems) {
                        testResult.set(findItems);
                    }
            });
        assertNotNull(subscription);
        verify(mockFindForMeService, times(1)).listItems(Type.CLOTHING, Sort.POPULAR, 1);
        assertNotNull(testResult.get());
    }*/

    @Module(
            injects = {ItemsLoaderTest.class},
            includes = { DataModule.class },
            overrides = true,
            library = true,
            complete = false
    )
    static public final class TestModule {

        /*@Provides @Singleton
        Application provideApplication() { return Robolectric.application; }

        @Provides @ApplicationContext
        public Context provideApplicationContext(){
            return Robolectric.application.getApplicationContext();
        }*/

        @Provides @Singleton
        RestAdapter provideRestAdapter(Endpoint endpoint, Client client) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(endpoint)
                    .build();
            return restAdapter;
        }

        @Provides @Singleton
        //@Named("mockService")
        FindForMeService provideFindForMeService(RestAdapter restAdapter){
            //return restAdapter.create(MockFindForMeService.class);
            MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
            FindForMeService mockService = new MockFindForMeService();
            FindForMeService service = mockRestAdapter.create(FindForMeService.class, mockService);
            return service;
        }

        @Provides @Singleton
        ItemsLoader provideItemsLoader(FindForMeService service) {
            return new ItemsLoader(service);
        }
    }

}

// It seems that i need to add a TestModule with dagger to be able to inject my class
