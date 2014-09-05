package mona.android.findforme;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import mona.android.findforme.ui.AppContainer;
import mona.android.findforme.ui.widget.CheckableFrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import mona.android.findforme.events.PhotoUploadQueueSizeEvent;
import mona.android.findforme.events.PhotoUploadSuccessEvent;
import mona.android.findforme.tasks.PhotoUploadTask;
import mona.android.findforme.tasks.PhotoUploadTaskQueue;

public class FindForMeActivity extends BaseActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @InjectView(R.id.fl_take_photo)
    CheckableFrameLayout mFlTakePhoto;

    /*@Inject
    AppContainer appContainer;*/

    @Inject
    Bus mBus;
    @Inject
    PhotoUploadTaskQueue mQueue;

    /*@InjectView(R.id.scalpel)
    ScalpelFrameLayout scalpelView;
    private static boolean first = true;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Switch enabledSwitch = new Switch(this);
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (first) {
                    first = false;
                }
                scalpelView.setLayerInteractionEnabled(isChecked);
                invalidateOptionsMenu();
            }
        });
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(enabledSwitch);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);*/
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.find_for_me;
    }

    @OnClick(R.id.fl_take_photo)
    void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @DebugLog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                PhotoUploadTask task = new PhotoUploadTask(createImageFile(imageBitmap));
                mQueue.add(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile(Bitmap bmp) throws IOException {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/findforme";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        File file = new File(dir, "findforme" + tsLong + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
        return file;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.findforme, menu);

        /*if (!scalpelView.isLayerInteractionEnabled()) {
            return false;
        }
        menu.add("Draw Views")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingViews())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawViews(checked);
                        return true;
                    }
                });
        menu.add("Draw IDs")
                .setCheckable(true)
                .setChecked(scalpelView.isDrawingIds())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        scalpelView.setDrawIds(checked);
                        return true;
                    }
                });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("UnusedDeclaration") // Used by event bus.
    @Subscribe
    public void onUploadSuccess(PhotoUploadSuccessEvent event) {
        Toast.makeText(this, "Upload completed", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("UnusedDeclaration") // Used by event bus.
    @Subscribe
    public void onQueueSizeChanged(PhotoUploadQueueSizeEvent event) {
        Log.i("TEST", " onQueueSizeChanged called ");
    }

}