package mona.android.findforme.modules;

import mona.android.findforme.FindForMeApplication;

/**
 * Created by cheikhna on 10/08/2014.
 */
public class Modules {

    public static Object[] list(FindForMeApplication app) {
        return new Object[]{
            new MainModule(app),
            new DataModule(app)
        };
    }

    private Modules() {
        //Not instanciable
    }

}

/**
 If context retention causes a pb --> do like philm with ContextProvider

 - Seperate TaskProviderModule on its own

 */