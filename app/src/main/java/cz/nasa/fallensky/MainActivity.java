package cz.nasa.fallensky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.MapView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.nasa.fallensky.core.DownloadService;
import cz.nasa.fallensky.utils.Communication;
import cz.nasa.fallensky.utils.MessageEvent;
import cz.nasa.fallensky.utils.MyConstants;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences myPrefs = getSharedPreferences(MyConstants.MY_PREFS, MODE_PRIVATE);
        String stringDate = myPrefs.getString(MyConstants.LAST_SAVED_DATA, "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDateandTime = sdf.format(new Date());

        if (!stringDate.equals(currentDateandTime)) {
            if(Communication.isNetworkAvailable(getApplicationContext())){
                Intent intent = new Intent(Intent.ACTION_SYNC, null, getApplicationContext(), DownloadService.class);
                startService(intent);
            } else {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.no_internet), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

        //pre inicialization google maps
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){}
            }
        }).start();

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.message) {
            case MyConstants.ERROR:
                Snackbar snackbar = Snackbar.make(coordinatorLayout, event.body, Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
