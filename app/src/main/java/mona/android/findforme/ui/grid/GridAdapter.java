package mona.android.findforme.ui.grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import mona.android.findforme.R;
import mona.android.findforme.data.api.model.FindItem;
import mona.android.findforme.ui.common.BindableAdapter;

/**
 * Created by cheikhna on 16/08/2014.
 */
public class GridAdapter extends BindableAdapter<FindItem> {
    private List<FindItem> items = Collections.emptyList();

    private final Picasso picasso;

    public GridAdapter(Context context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public FindItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View itemView = inflater.inflate(R.layout.find_item_view, container, false);
        return itemView;
    }

    @Override public void bindView(FindItem item, int position, View view) {
        //((GalleryItemView) view).bindTo(item, picasso);
        RequestCreator request = picasso.load(item.imageLink);
        ImageView itemImage = (ImageView) view.findViewById(R.id.iv_find_item_image);
        request.resize(itemImage.getMeasuredWidth(), itemImage.getMeasuredHeight())
                        .centerCrop().into(itemImage);

    }

    public void replaceWithItems(List<FindItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

}