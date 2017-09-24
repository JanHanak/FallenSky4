package cz.nasa.fallensky.core;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.nasa.fallensky.data.Meteorit;
import cz.nasa.fallensky.utils.Communication;
import cz.nasa.fallensky.utils.MessageEvent;
import cz.nasa.fallensky.utils.MyConstants;
import cz.nasa.fallensky.utils.StandartRequest;
import io.realm.Realm;
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    private RequestQueue mRequestQueue;

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(@NonNull Request<T> req) {
        req.setTag(TAG);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                300000,
                1,
                1
        );
        req.setRetryPolicy(retryPolicy);
        getRequestQueue().add(req);
    }


    private void getData(){
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(@NonNull final JSONArray response) {
                VolleyLog.d(TAG, "answer: " + response.toString());
                try {
                    List<Meteorit> meteorits= LoganSquare.parseList(response.toString(), Meteorit.class);

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.delete(Meteorit.class);

                    realm.insertOrUpdate(meteorits);
                    realm.commitTransaction();
                    EventBus.getDefault().post(new MessageEvent(MyConstants.DATA_SAVED));


                    SharedPreferences myPrefs = getSharedPreferences(MyConstants.MY_PREFS, MODE_PRIVATE);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String currentDateandTime = sdf.format(new Date());
                    SharedPreferences.Editor e = myPrefs.edit();
                    e.putString(MyConstants.LAST_SAVED_DATA, currentDateandTime);
                    e.apply();
                }  catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("X-App-Token", "ToqHE294i5Fyg62gWO7uQoP0C");
        try {
            StandartRequest standartRequest = new StandartRequest(Request.Method.GET, "https://data.nasa.gov/resource/y77d-th95.json", params, responseListener, errorListener);
            addToRequestQueue(standartRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(@NonNull VolleyError error) {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            if(error.networkResponse != null && error.networkResponse.data != null){
                String jsonString = null;
                try {
                    jsonString = new String(error.networkResponse.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new MessageEvent(MyConstants.ERROR,jsonString));

                VolleyLog.d(TAG, "ErrorData: " + jsonString);
            } else {
                EventBus.getDefault().post(new MessageEvent(MyConstants.ERROR,"UNKNOWN ERROR"));

            }
        }
    };
    
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started!");
        if (Communication.isNetworkAvailable(getApplicationContext())) {
            getData();
        }
    }
}