package mona.android.findforme.model;

import com.androidsocialnetworks.lib.SocialPerson;

/**
 * Created by cheikhna on 30/08/2014.
 */
public class UserProfile {

    //this class shld represent a profile no matter what the user's sn site origin

    private long socialNetworkId; //provided by socialNetworkManagerr -> but that's a dependency we dont want
                                    // maybe have a mapping for it
    private String id;
    private String name;
    private String avatarURL;
    private String profileURL;
    private String username;

    public UserProfile(String id, String name, String avatarURL,
               String profileURL, String username, long socialNetworkId){
        this.id = id;
        this.name = name;
        this.avatarURL = avatarURL;
        this.profileURL = profileURL;
        this.username = username;
        this.socialNetworkId = socialNetworkId;
    }

    public UserProfile(int socialNetworkId, final SocialPerson person){
        this.socialNetworkId = socialNetworkId;
        setFromSocialPerson(person);
    }

    public void setFromSocialPerson(SocialPerson person){
        this.id = person.id;
        this.name = person.name;
        this.avatarURL = person.avatarURL;
        this.profileURL = person.profileURL;
        this.username = person.nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public long getSocialNetworkId() {
        return socialNetworkId;
    }

}
