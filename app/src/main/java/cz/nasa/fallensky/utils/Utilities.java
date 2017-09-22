package cz.nasa.fallensky.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressLint("DefaultLocale")
public class Utilities {
	public static SimpleDateFormat sourceFormatFull = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
	public static SimpleDateFormat outputFormat = new SimpleDateFormat("d.M.yyyy", Locale.US);

	public Utilities() {
	}
}
