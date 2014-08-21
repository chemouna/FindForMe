package mona.android.findforme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by cheikhna on 10/08/2014.
 */
public class BaseActivity extends FragmentActivity {

    /**
     (Not included in open sourced sample)
     TODO:
     - add NavigationDrawer to it (exple in iosched + use dagger & butterKnife where suited
     - have a super modularized code

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FindForMeApplication.get(this).inject(this);
    }


}
