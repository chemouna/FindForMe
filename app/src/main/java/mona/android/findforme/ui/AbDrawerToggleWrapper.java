package mona.android.findforme.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.google.gdata.util.common.base.Preconditions;

import javax.inject.Inject;

import mona.android.findforme.R;

/**
 * Created by cheikhna on 31/08/2014.
 */
public class AbDrawerToggleWrapper {

    private ActionBarDrawerToggle mDrawerToggle;

    public AbDrawerToggleWrapper(Activity activity, final DrawerLayout drawerLayout,
                                        final DrawerLayout.DrawerListener drawerListener){
        mDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
                R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                drawerListener.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                drawerListener.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState){
                super.onDrawerStateChanged(newState);
                drawerListener.onDrawerStateChanged(newState);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                drawerListener.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
    }

    public AbDrawerToggleWrapper(ActionBarDrawerToggle drawerToggle){
        this.mDrawerToggle = drawerToggle;
    }

    public void syncState(){
        Preconditions.checkNotNull(mDrawerToggle, " Drawer toggle should not be null");
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig){
        Preconditions.checkNotNull(mDrawerToggle, " Drawer toggle should not be null");
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Preconditions.checkNotNull(mDrawerToggle, " Drawer toggle should not be null");
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    public static class AbDrawerToggleWrapperFactory {

        @Inject
        public AbDrawerToggleWrapperFactory() {
        }

        //drawerlistener maybe doesnt need to be provided
        public AbDrawerToggleWrapper create(Activity activity, final DrawerLayout drawerLayout,
                                     final DrawerLayout.DrawerListener drawerListener){
            Preconditions.checkNotNull(drawerLayout, " DrawerLayout must be not null");
            return new AbDrawerToggleWrapper(activity, drawerLayout, drawerListener);
        }

    }
}
