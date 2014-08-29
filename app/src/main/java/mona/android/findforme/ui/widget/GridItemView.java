package mona.android.findforme.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mona.android.findforme.R;
import mona.android.findforme.data.api.model.FindItem;

/**
 * Created by cheikhna on 29/08/2014.
 */
public class GridItemView extends LinearLayout {
    //we are doing doing this class to render images dynamicaly according to their size

    @InjectView(R.id.iv_find_item_image) ImageView imageView;

    private float aspectRatio = 1;
    private RequestCreator request;

    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    //TODO: on server side store true image width & height to not stretch it much and send them here to client
    public void bindTo(FindItem item, Picasso picasso) {
        request = picasso.load(item.getImageLink());
        aspectRatio = 1f * item.getWidth() / item.getHeight();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode != MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException(" layout_width must be match_parent ");
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // Honor aspect ratio for height but no larger than 2x width.
        int height = Math.min((int) (width / aspectRatio), width * 2);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (request != null) {
            request.resize(width, height).centerCrop().into(imageView);
            request = null;
        }
    }

}
