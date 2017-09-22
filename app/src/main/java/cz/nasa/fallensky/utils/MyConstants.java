package cz.nasa.fallensky.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Macbook on 22/09/2017.
 */

public class MyConstants {
    public static final String ID = "ID";

    public static final String DATA_SAVED = "DATA_SAVED";
    public static final String ERROR = "ERROR";
    public static final String LAST_SAVED_DATA = "LAST_SAVED_DATA";

    public static final String MY_PREFS = "myPrefs";


    @NonNull
    public static SimpleDateFormat sourceFormatFull = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.US);
    @NonNull
    public static SimpleDateFormat outputFormat = new SimpleDateFormat("d.M.yyyy", Locale.US);
}
