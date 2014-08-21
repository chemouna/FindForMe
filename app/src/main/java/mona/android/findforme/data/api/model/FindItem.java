package mona.android.findforme.data.api.model;

/**
 * Created by cheikhna on 17/08/2014.
 */
public class FindItem {

    //For now we have only image ...
    //TODO: send username and description and reuse them here
    //TODO: on ServerSide generate some ids

    public String id;
    public String imageLink;

    public FindItem(String id, String imageLink) {
        this.id  = id;
        this.imageLink = imageLink;
    }

}
