package app.arash.androidcore.util;

import android.content.Context;
import android.view.View;
import app.arash.androidcore.R;

/**
 * Created by arash on 8/15/17.
 */

public class MultiScreenUtility {

  public static boolean isTablet(Context context) {
//    return context.getResources().getBoolean(R.bool.isTablet);
    return false;
  }

  public static boolean isTablet(View view) {
//    return view.getResources().getBoolean(R.bool.isTablet);
    return false;
  }
}
