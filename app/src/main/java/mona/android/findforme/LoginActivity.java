package mona.android.findforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import mona.android.findforme.socialnetwork.SocialNetworksContract;
import mona.android.findforme.state.ApplicationState;
import mona.android.findforme.state.UserProfileChangedEvent;
import mona.android.findforme.state.persistence.AsyncDatabaseHelper;

/**
 * Created by cheikhna on 09/08/2014.
 */
public class LoginActivity extends BaseActivity {

    private static final String SOCIAL_NETWORK_TAG = "social_network_fragment";

    @InjectView(R.id.ib_login_google_plus)
    Button mIbLoginGooglePlus;

    @InjectView(R.id.ib_login_twitter)
    Button mIbLoginTwitter;

    @InjectView(R.id.ib_login_facebook)
    Button mIbLoginFacebook;

    @Inject
    SocialNetworksContract mSocialNetworkManager;

    @Inject
    AsyncDatabaseHelper mAsyncDbHelper;

    @Inject
    Bus mBus;

    @Inject
    ApplicationState mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = FindForMeApplication.get(this).getApplicationState();

        if(mState.getUserProfile() != null) {
            startActivity(new Intent(this, FindForMeActivity.class));
        }

        getSupportFragmentManager().beginTransaction().add(
                mSocialNetworkManager.getManagerFragment(), SOCIAL_NETWORK_TAG).commit();

        verifyPreconditions();
    }

    private void verifyPreconditions(){
        mState = Preconditions.checkNotNull(mState, " ApplicationState cannot be null");
        mSocialNetworkManager = Preconditions.checkNotNull(mSocialNetworkManager, " SocialNetworkManager cannot be null");
        mAsyncDbHelper = Preconditions.checkNotNull(mAsyncDbHelper, " AsyncDatabaseHelper cannot be null");
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.login_screen;
    }

    @OnClick(R.id.ib_login_google_plus)
    void loginGooglePlus(){
        mSocialNetworkManager.loginGooglePlus();
    }

    @OnClick(R.id.ib_login_twitter)
    void loginTwitter(){
        mSocialNetworkManager.loginTwitter();
    }

    @OnClick(R.id.ib_login_facebook)
    void loginFacebook(){
        mSocialNetworkManager.loginFacebook();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * This is required only if you are using Google Plus, the issue is that there SDK
         * require Activity to launch Auth, so library can't receive onActivityResult in fragment
         */
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Subscribe
    public void onUserProfileChanged(UserProfileChangedEvent event){
        mState.setUserProfile(event.getUserProfile());
        mAsyncDbHelper.put(event.getUserProfile());
        startActivity(new Intent(this, FindForMeActivity.class));
    }

}