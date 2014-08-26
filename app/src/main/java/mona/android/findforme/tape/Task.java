package mona.android.findforme.tape;

import android.content.Context;

import java.io.File;

/**
 * Created by cheikhna on 26/08/2014.
 */
public class Task<T> {
    protected transient Context context;
    protected transient T callback;

    public void execute(Context context, T callback){ //Temp
        this.context = context;
        this.callback = callback;
    }

}
