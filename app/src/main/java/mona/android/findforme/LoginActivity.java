package mona.android.findforme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;

import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.impl.FacebookSocialNetwork;
import com.androidsocialnetworks.lib.impl.GooglePlusSocialNetwork;
import com.androidsocialnetworks.lib.impl.TwitterSocialNetwork;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import timber.log.Timber;

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
    SocialNetworkManager mSocialNetworkManager;

    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        ButterKnife.inject(this);

        getSupportFragmentManager().beginTransaction().add(
                mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();

    }

    //TODO: user shouldn't arrive at this screen if he already is connected to one of them

    @OnClick(R.id.ib_login_google_plus)
    void loginGooglePlus(){
        GooglePlusSocialNetwork gPlusSocialNetwork = mSocialNetworkManager.getGooglePlusSocialNetwork();
        if(gPlusSocialNetwork.isConnected()){
            Timber.w(" Already connected to Google Plus ");
            return;
        }
        gPlusSocialNetwork.requestLogin(new OnLoginCompleteListener() {
            @DebugLog
            @Override
            public void onLoginSuccess(int i) {
                Timber.d(" getGooglePlusSocialNetwork -  onLoginSuccess ");
            }
            @DebugLog
            @Override
            public void onError(int i, String s, String s2, Object o) {
                Timber.d(" getGooglePlusSocialNetwork - onError ");
            }
        });
    }

    @OnClick(R.id.ib_login_twitter)
    void loginTwitter(){
        TwitterSocialNetwork twitterSocialNetwork = mSocialNetworkManager.getTwitterSocialNetwork();
        if(twitterSocialNetwork.isConnected()){
            Timber.w(" Already connected to Twitter ");
            return;
        }
        twitterSocialNetwork.requestLogin(new OnLoginCompleteListener() {
            @DebugLog
            @Override
            public void onLoginSuccess(int socialNetworkID) {
                Timber.d(" getTwitterSocialNetwork - onLoginSuccess ");
            }

            @DebugLog
            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                Timber.d(" getTwitterSocialNetwork - onError ");
            }
        });
    }

    @OnClick(R.id.ib_login_facebook)
    void loginFacebook(){
        FacebookSocialNetwork fbSocialNetwork = mSocialNetworkManager.getFacebookSocialNetwork();
        if(fbSocialNetwork.isConnected()){
            Timber.w(" Already connected to Facebook ");
            return;
        }
        fbSocialNetwork.requestLogin(new OnLoginCompleteListener() {
            @DebugLog
            @Override
            public void onLoginSuccess(int i) {
                Timber.d(" getFacebookSocialNetwork - onLoginSuccess ");
            }
            @DebugLog
            @Override
            public void onError(int i, String s, String s2, Object o) {
                Timber.d(" getFacebookSocialNetwork - onError ");
            }
        });
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

}