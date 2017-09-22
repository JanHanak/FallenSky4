package cz.nasa.fallensky.utils;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class StandartRequest extends Request<JSONArray> {
    private Map<String, String> mParams;
    private Listener<JSONArray> mListener;

    public StandartRequest(int method, String url, Map<String, String> params,
                           Listener<JSONArray> responseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mParams = params;
        this.mListener = responseListener;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mParams;
    }

    @NonNull
    @Override
    protected Response<JSONArray> parseNetworkResponse(@NonNull NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "UTF-8");
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        mListener.onResponse(response);
    }
}