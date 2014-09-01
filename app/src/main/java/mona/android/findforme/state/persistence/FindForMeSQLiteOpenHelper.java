package mona.android.findforme.state.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.base.Preconditions;

import hugo.weaving.DebugLog;
import mona.android.findforme.model.UserProfile;
import mona.android.findforme.state.persistence.DatabaseHelper;
import timber.log.Timber;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by cheikhna on 31/08/2014.
 */
public class FindForMeSQLiteOpenHelper extends SQLiteOpenHelper implements DatabaseHelper {

    private boolean mIsClosed;

    private static final String DATABASE_NAME = "findforme.db";
    private static final int DATABASE_VERSION = 1;

    private static final Class[] MODELS = new Class[]{UserProfile.class};

    static {
        for (Class clazz : MODELS) {
            cupboard().register(clazz);
        }
    }

    public FindForMeSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        cupboard().withDatabase(db).upgradeTables();
    }

    @Override
    public UserProfile getUserProfile(String username) {
        assertNotClosed();
        try {
            return cupboard().withDatabase(getReadableDatabase())
                        .query(UserProfile.class)
                        .withSelection("username = ? ", username)
                        .get();
        } catch(Exception e){
            Timber.e(" Db crash : %s ", e.getMessage());
            return null;
        }
    }

    @DebugLog
    @Override
    public void put(UserProfile profile) {
        assertNotClosed();
        try {
            cupboard().withDatabase(getWritableDatabase()).put(profile);
        } catch (Exception e) {
             Timber.e(" Db crash : %s ", e.getMessage());
             //Add this to crittercism or crashlytics
        }
    }

    @Override
    public void delete(UserProfile profile) {
        assertNotClosed();
        try {
            cupboard().withDatabase(getWritableDatabase()).delete(profile);
        } catch (Exception e) {
            Timber.e(" Db crash : %s ", e.getMessage());
        }
    }

    @Override
    public boolean isClosed() {
        return mIsClosed;
    }

    @Override
    public synchronized void close() {
        mIsClosed = true;
        super.close();
    }

    private void assertNotClosed() {
        Preconditions.checkState(!mIsClosed, "Database is closed");
    }

}
