package mona.android.findforme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.squareup.otto.Bus;

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

    @InjectView(R.id.fl_take_photo) FrameLayout mFlTakePhoto;

    @Inject Bus mBus;
    @Inject PhotoUploadTaskQueue mQueue;

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_for_me);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.fl_take_photo)
    void takePhoto(){
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
            //TODO: 0- right now maybe just send it directly but maybe later have an upload or cancel option
            //TODO: 1 - use tape to send image to a server / or take a look at android JobScheduler
            //2 - setup a small remote server
            try {
                //File imageFile = new File(imageBitmap);
                PhotoUploadTask task = new PhotoUploadTask(createImageFile(imageBitmap));
                mQueue.add(task);
            }
            catch(IOException e){
                //
                e.printStackTrace();
            }
        }
    }

    private File createImageFile(Bitmap bmp) throws IOException {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/findforme";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();

        Long tsLong = System.currentTimeMillis()/1000;
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("UnusedDeclaration") // Used by event bus.
    @Subscribe
    public void onUploadSuccess(PhotoUploadSuccessEvent event) {
        Toast.makeText(this, "Upload completed", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("UnusedDeclaration") // Used by event bus.
    @Subscribe public void onQueueSizeChanged(PhotoUploadQueueSizeEvent event) {
        Log.i("TEST", " onQueueSizeChanged called ");
    }

}