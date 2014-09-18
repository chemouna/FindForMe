package mona.android.findforme.test;

import android.test.ApplicationTestCase;

import mona.android.findforme.FindForMeApplication;

/**
 * Created by cheikhna on 16/09/2014.
 */
public class TestApplication extends ApplicationTestCase<FindForMeApplication> {

    public TestApplication(Class<FindForMeApplication> applicationClass) {
        super(applicationClass);
    }

    /*@Override
    public void buildObjectGraphAndInject() {
        List<Object> modules = Arrays.asList(Modules.list(this));
        modules.add(new TestModule());
        mObjectGraph = ObjectGraph.create(modules);
        mObjectGraph.inject(this);
    }*/


}
