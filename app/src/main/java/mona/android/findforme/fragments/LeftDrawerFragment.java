package mona.android.findforme.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import mona.android.findforme.R;
import mona.android.findforme.model.UserProfile;
import mona.android.findforme.state.ApplicationState;
import mona.android.findforme.state.UserState;

/**
 * Created by cheikhna on 01/09/2014.
 */
public class LeftDrawerFragment extends Fragment {

    @InjectView(R.id.tv_username)
    TextView tvUsername;

    @Inject
    ApplicationState mState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.drawer_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mState != null) {
            displayUserProfile(mState.getUserProfile());
        }
    }

    private void displayUserProfile(UserProfile profile){
        if(profile == null){
            return;
        }
        tvUsername.setText(profile.getUsername());
    }

    //TODO: have a way for this to be notified when UserProfileChanged occurs
    //f.Ex @Subscribe method here for that event

    @Subscribe
    public void onUserProfileChanged(UserState.UserProfileChangedEvent event) {
          displayUserProfile(mState.getUserProfile());
    }


}
