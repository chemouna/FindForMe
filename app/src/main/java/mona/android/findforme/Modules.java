package mona.android.findforme;

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