package app.arash.androidcore.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import app.arash.androidcore.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }

  public static int dpToPx(Context context, int dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  public static int pxToDp(Context context, int px) {
    return (int) (px / context.getResources().getDisplayMetrics().density);
  }

  public static void makeSnack(View view, String msg) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
  }

  public static void showUrl(Context context, String url) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    context.startActivity(browserIntent);
  }

  public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager
        .getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  public static void hideKeyboard(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void showKeyboard(Context context, View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
        InputMethodManager.SHOW_FORCED, 0);
  }

  public static String getDeviceName() {
    if (!TextUtils.isEmpty(Build.BOARD) && !TextUtils.isEmpty(Build.MODEL)) {
      return String.format("%s %s", Build.BOARD, Build.MODEL);
    }
    return "";
  }

  public static String getOsVersion() {
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      return VERSION.BASE_OS;
    }
    return "";
  }

  public static void goToSetting(Context context) {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
    intent.setData(uri);
    context.startActivity(intent);
  }


  public static boolean isAppRunning(final Context context) {
    final ActivityManager activityManager = (ActivityManager) context
        .getSystemService(Context.ACTIVITY_SERVICE);
    final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager
        .getRunningAppProcesses();
    if (procInfos != null) {
      for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
        if (processInfo.processName.equals("ir.myims.pargoon")) {
          return true;
        }
      }
    }
    return false;
  }


  public static void shareContent(Context context, String content) {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent
        .putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
    shareIntent.putExtra(Intent.EXTRA_TEXT, content);
    shareIntent.setType("text/plain");
    context.startActivity(shareIntent);
  }


  public static String extractConfirmCode(String sms) {

    if (Empty.isNotEmpty(sms)) {

      Pattern p = Pattern.compile("-?\\d+");
      Matcher m = p.matcher(sms);
      while (m.find()) {
        String group = m.group();
        if (group.length() == 4) {
          return group;
        }
      }
    }
    return "";
  }
}
