package mona.android.findforme.ui.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mona.android.findforme.FindForMeApplication;
import mona.android.findforme.R;
import mona.android.findforme.data.ItemsLoader;
import mona.android.findforme.data.api.Type;
import mona.android.findforme.data.api.model.FindItem;
import mona.android.findforme.data.rx.EndlessObserver;
import rx.Subscription;

/**
 * Created by cheikhna on 16/08/2014.
 */
public class GridContainer extends ViewAnimator {

    @InjectView(R.id.grid_view)
    AbsListView mGridView;

    @Inject
    Picasso mPicasso;

    @Inject
    ItemsLoader mItemsLoader;

    private final GridAdapter mAdapter;
    private Subscription mSubscription;

    private Type mType = Type.CLOTHING; //TODO allow it to be settable

    public GridContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        FindForMeApplication.get(context).inject(this);

        mAdapter = new GridAdapter(context, mPicasso);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        mGridView.setAdapter(mAdapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //TODO: fetch from server our images & use picasso to display them
        mSubscription = mItemsLoader.loadItems(mType, new EndlessObserver<List<FindItem>>() {
            @Override
            public void onNext(List<FindItem> findItems) {
                mAdapter.replaceWithItems(findItems);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        mSubscription.unsubscribe();
        super.onDetachedFromWindow();
    }

}
