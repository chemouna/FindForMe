package mona.android.findforme.data.api.model;

import java.util.List;
import rx.functions.Func1;


/**
 * Created by cheikhna on 19/08/2014.
 */
public class ResponseToFindItemList implements Func1<ListResponse, List<FindItem>> {

    @Override
    public List<FindItem> call(ListResponse listResponse) {
        List<FindItem> results = listResponse.data;
        return results;
    }

}
