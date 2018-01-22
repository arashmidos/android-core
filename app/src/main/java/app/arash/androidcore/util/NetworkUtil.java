package app.arash.androidcore.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Arash on 2018-01-19
 */
public class NetworkUtil {

  public static final String TAG = NetworkUtil.class.getSimpleName();

  public static boolean hasActiveInternetConnection(Context context) {
    if (isNetworkAvailable(context)) {
      try {
        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com")
            .openConnection());
        urlc.setRequestProperty("User-Agent", "Test");
        urlc.setRequestProperty("Connection", "close");
        urlc.setConnectTimeout(1500);
        urlc.connect();
        return (urlc.getResponseCode() == 200);
      } catch (IOException e) {
        Log.e(TAG, "Error checking internet connection", e);
      }
    } else {
      Log.d(TAG, "No network available!");
    }
    return false;
  }

  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null;
  }
}
