package mona.android.findforme.data.api.model;

import java.util.List;

/**
 * Created by cheikhna on 17/08/2014.
 */
public class ListResponse extends BackendBaseResponse {
    public List<FindItem> data;

    public ListResponse(int status, boolean success, List<FindItem> items) {
        super(status, success);
        this.data = items;
    }

}
