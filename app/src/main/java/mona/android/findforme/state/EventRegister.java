package mona.android.findforme.state;

/**
 * Created by cheikhna on 06/09/2014.
 */
public interface EventRegister {

    public void registerForEvents(Object receiver);

    public void unregisterForEvents(Object receiver);
}
