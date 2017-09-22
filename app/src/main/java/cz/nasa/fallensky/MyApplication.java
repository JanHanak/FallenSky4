package cz.nasa.fallensky;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.nasa.fallensky.core.DownloadService;
import cz.nasa.fallensky.utils.Communication;
import cz.nasa.fallensky.utils.MyConstants;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    protected static final String TAG = "MyApplication";
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name(BuildConfig.APPLICATION_ID).schemaVersion(1).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(configuration);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);




    }
}