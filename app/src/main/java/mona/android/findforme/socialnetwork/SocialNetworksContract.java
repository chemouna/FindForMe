package mona.android.findforme.socialnetwork;

import android.support.v4.app.Fragment;

/**
 * Created by cheikhna on 14/09/2014.
 */
public interface SocialNetworksContract {

    public void loginGooglePlus(); //what should be the return type here ?

    public void loginTwitter();

    public void loginFacebook();

    public Fragment getManagerFragment();

}


/*
    TODO: use this interface to guide tests

 */