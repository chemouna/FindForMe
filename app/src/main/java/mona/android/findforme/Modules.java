package mona.android.findforme;

import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.MainModule;
import mona.android.findforme.data.DataModule;
import mona.android.findforme.ui.UiModule;

/**
 * Created by cheikhna on 10/08/2014.
 */
public class Modules {

    public static Object[] list(FindForMeApplication app) {
        return new Object[]{
            new FindForMeModule(app)
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