package mona.android.findforme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.impl.FacebookSocialNetwork;
import com.androidsocialnetworks.lib.impl.GooglePlusSocialNetwork;
import com.androidsocialnetworks.lib.impl.TwitterSocialNetwork;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
import com.google.common.base.Preconditions;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import mona.android.findforme.model.UserProfile;
import mona.android.findforme.state.ApplicationState;
import mona.android.findforme.state.persistence.AsyncDatabaseHelper;
import timber.log.Timber;

/**
 * Created by cheikhna on 09/08/2014.
 */
public class LoginActivity extends BaseActivity
                implements OnLoginCompleteListener, OnRequestSocialPersonCompleteListener {

    private static final String SOCIAL_NETWORK_TAG = "social_network_fragment";

    @InjectView(R.id.ib_login_google_plus)
    Button mIbLoginGooglePlus;

    @InjectView(R.id.ib_login_twitter)
    Button mIbLoginTwitter;

    @InjectView(R.id.ib_login_facebook)
    Button mIbLoginFacebook;

    @Inject
    SocialNetworkManager mSocialNetworkManager;

    @Inject
    AsyncDatabaseHelper mAsyncDbHelper;

    @Inject
    Bus mBus;

    @Inject
    ApplicationState mState;

    private SocialNetwork mCurrentSocialNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = FindForMeApplication.get(this).getApplicationState();

        if(mState.getUserProfile() != null) {
            startActivity(new Intent(this, FindForMeActivity.class));
        }

        getSupportFragmentManager().beginTransaction().add(
                mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();

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
        mCurrentSocialNetwork = mSocialNetworkManager.getGooglePlusSocialNetwork();
        mCurrentSocialNetwork.requestLogin(this);
    }

    @OnClick(R.id.ib_login_twitter)
    void loginTwitter(){
        mCurrentSocialNetwork = mSocialNetworkManager.getTwitterSocialNetwork();
        mCurrentSocialNetwork.requestLogin(this);
        //maybe an rxJava of one after the other could do it
    }

    @OnClick(R.id.ib_login_facebook)
    void loginFacebook(){
        mCurrentSocialNetwork = mSocialNetworkManager.getFacebookSocialNetwork();
        mCurrentSocialNetwork.requestLogin(this);
        //Toast.makeText(this, R.string.click_fb, Toast.LENGTH_LONG).show();
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

    @Override
    public void onLoginSuccess(int socialNetworkID) {
        mCurrentSocialNetwork.requestCurrentPerson(this);
    }

    @Override
    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
        Timber.w(" Error in socialNetworkAuth - socialNetworkID : %d - requestId : %s - errorMessage : %s ",
                    socialNetworkID, requestID, errorMessage);
    }

    @Override
    public void onRequestSocialPersonSuccess(int socialNetworkID, SocialPerson socialPerson) {
        UserProfile profile = new UserProfile(socialNetworkID, socialPerson);
        mState.setUserProfile(profile);
        mAsyncDbHelper.put(profile);
        startActivity(new Intent(this, FindForMeActivity.class));
    }

    //TODO: add logout action - that should run  :
    /*
    for (SocialNetwork socialNetwork : socialNetworkManager.getInitializedSocialNetworks()) {
        socialNetwork.logout();
    }
     */

}