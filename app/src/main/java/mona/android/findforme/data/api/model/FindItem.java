package mona.android.findforme.data.api.model;

import android.util.Log;

/**
 * Created by cheikhna on 17/08/2014.
 */
public class FindItem {
    private String Key;
    private String LastModified;
    private int width;
    private int height;
    private String imagelink;

    public FindItem(String key, String lastModified, String imageLink, int width, int height) {
        Log.i("TEST", " finditem w key : " + key + " imageLink : " + imageLink + " lastModified : "+ lastModified);
        this.Key = key;
        this.imagelink = imageLink;
        this.LastModified = lastModified;
        this.width = width;
        this.height = height;
    }

    public String getImageLink() {
        return imagelink;
    }

    public String getLastModified() {
        return LastModified;
    }

    public String getKey() {
        return Key;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "FindItem{" +
                "key='" + Key + '\'' +
                ", lastModified='" + LastModified + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", imageLink='" + imagelink + '\'' +
                '}';
    }

}