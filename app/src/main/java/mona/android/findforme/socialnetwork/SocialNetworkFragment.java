package mona.android.findforme.socialnetwork;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.androidsocialnetworks.lib.SocialNetwork;
import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.model.UserProfile;
import mona.android.findforme.qualifiers.ApplicationContext;
import mona.android.findforme.state.UserProfileChangedEvent;
import mona.android.findforme.util.AppUtils;
import timber.log.Timber;

/**
 * Created by cheikhna on 14/09/2014.
 */
public class SocialNetworkFragment extends SocialNetworkManager
            implements SocialNetworksContract, OnLoginCompleteListener,
                        OnRequestSocialPersonCompleteListener {

    private SocialNetwork socialNetwork;

    @Inject
    Bus mBus;

    public static SocialNetworkFragment build(Context context) {
        SocialNetworkManager socialNetworkManager =
                Builder.from(context)
               .twitter(AppUtils.TWITTER_APP_ID, AppUtils.TWITTER_API_SECRET)
               .facebook()
               .googlePlus()
               .build();

        SocialNetworkFragment socialNetworkFragment = new SocialNetworkFragment();
        socialNetworkFragment.setArguments(socialNetworkManager.getArguments());
        return socialNetworkFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void loginGooglePlus() {
        socialNetwork = getGooglePlusSocialNetwork();
        socialNetwork.requestLogin(this);
    }

    @Override
    public void loginTwitter() {
        socialNetwork = getTwitterSocialNetwork();
        socialNetwork.requestLogin(this);
    }

    @Override
    public void loginFacebook() {
        socialNetwork = getFacebookSocialNetwork();
        socialNetwork.requestLogin(this);
    }

    @Override
    public Fragment getManagerFragment() {
        return this;
    }

    @Override
    public void onLoginSuccess(int socialNetworkID) {
        socialNetwork.requestCurrentPerson(this);
    }

    @Override
    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
        Timber.w(" Error in socialNetworkAuth - socialNetworkID : %d - requestId : %s - errorMessage : %s ",
                socialNetworkID, requestID, errorMessage);
    }

    @Override
    public void onRequestSocialPersonSuccess(int socialNetworkID, SocialPerson socialPerson) {
        UserProfile profile = new UserProfile(socialNetworkID, socialPerson);
        mBus.post(new UserProfileChangedEvent(profile));
    }

}