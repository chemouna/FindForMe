package mona.android.findforme.data.api.model;

import android.util.Log;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by cheikhna on 17/08/2014.
 */
public class ListResponse extends BackendBaseResponse {
    public List<FindItem> data;

    public ListResponse(int status, boolean success, List<FindItem> items) {
        super(status, success);
        this.data = items;
        Log.i("TEST", " ListResponse - status : " + status + " items size : " + items.size());
    }

    public List<FindItem> getData() {
        return data;
    }
}
