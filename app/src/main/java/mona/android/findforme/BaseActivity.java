package mona.android.findforme;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mona.android.findforme.helpers.UserManager;
import mona.android.findforme.ui.AbDrawerToggleWrapper;
import mona.android.findforme.util.LPreviewSupportBase;

/**
 * Created by cheikhna on 10/08/2014.
 */
public abstract class BaseActivity extends FragmentActivity {
    /**
     (Not included in open sourced sample)
     TODO:
     - add NavigationDrawer to it (exple in iosched + use dagger & butterKnife where suited
     - have a super modularized code
    */
    @Inject
    LPreviewSupportBase lPreviewSupport;

    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    @Inject
    AbDrawerToggleWrapper.AbDrawerToggleWrapperFactory drawerToggleWrapperFactory;

    //TODO: may need to abstract this userManager in a MainManager with other needed stuff
    @Inject
    UserManager mUserManager;

    private AbDrawerToggleWrapper mDrawerToggleWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FindForMeApplication.get(this).inject(this);

        setContentView(getContentViewLayoutId());
        ButterKnife.inject(this);
    }

    protected abstract int getContentViewLayoutId();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    private void setupNavDrawer(){
        //TODO: check if this drawerlistener can be moved to AbDrawerToggleWrapper
        mDrawerToggleWrapper = drawerToggleWrapperFactory.create(this, mDrawerLayout,
                new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                /*updateStatusBarForNavDrawerSlide(0f);
                onNavDrawerStateChanged(false, false);*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                /*updateStatusBarForNavDrawerSlide(1f);
                onNavDrawerStateChanged(true, false);*/
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //invalidateOptionsMenu();
                //onNavDrawerStateChanged(isNavDrawerOpen(), newState != DrawerLayout.STATE_IDLE);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                /*updateStatusBarForNavDrawerSlide(slideOffset);
                onNavDrawerSlide(slideOffset);*/
            }
        });
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // TODO: populate the nav drawer with the correct items

        mDrawerToggleWrapper.syncState();

        // When the user runs the app for the first time, we want to land them with the
        // navigation drawer open. But just the first time.
        /*if (!PrefUtils.isWelcomeDone(this)) {
            // first run of the app starts with the nav drawer open
            PrefUtils.markWelcomeDone(this);
            mDrawerLayout.openDrawer(Gravity.START);
        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerToggleWrapper.onOptionsItemSelected(item)) {
            return true;
        }
        switch(id){

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggleWrapper.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        mUserManager.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mUserManager.stop();
        super.onPause();
    }

}