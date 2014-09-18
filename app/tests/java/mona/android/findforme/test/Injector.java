package mona.android.findforme.test;

import dagger.ObjectGraph;

/**
 * Created by cheikhna on 16/09/2014.
 */
public final class Injector {
    public static ObjectGraph objectGraph = null;

    public static synchronized void init(Object... rootModules) {
        objectGraph = objectGraph == null ? ObjectGraph.create(rootModules) : objectGraph.plus(rootModules);
    }

    public static void inject(final Object target) {
        objectGraph.inject(target);
    }

    public static void add(Object... object) {
        objectGraph = ObjectGraph.create(object);
    }
}