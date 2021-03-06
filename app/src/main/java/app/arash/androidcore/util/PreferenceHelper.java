package app.arash.androidcore.util;

import android.content.SharedPreferences;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.entity.KeyValue;

public class PreferenceHelper {

  public static final String FORCE_EXIT = "FORCE_EXIT";
  private static final String LATEST_VERSION = "LATEST_VERSION";
  private static final String UPDATE_URI = "UPDATE_URI";
  private static final String SHOW_INTRO = "SHOW_INTRO";
  private static final String HAS_SEEN = "HAS_SEEN";
  private static final String PHONE_NUMBER = "PHONE_NUMBER";
  private static final String RECENT_SEARCH = "RECENT_SEARCH";
  private static final String HAS_LOGGED_IN = "HAS_LOGGED_IN";
  private static final String TOKEN = "TOKEN";
  private static final String PERMISSION_GRANTED = "PERMISSION_GRANTED";

  public static void setSeenIntro(boolean hasSeen) {
    MedicApplication.getPreference().edit().putBoolean(HAS_SEEN, hasSeen).apply();
  }

  public static boolean hasSeenIntro() {
    return MedicApplication.getPreference().getBoolean(HAS_SEEN, false);
  }

  public static String getPhoneNumber() {
    return MedicApplication.getPreference().getString(PHONE_NUMBER, "");
  }

  public static void setPhoneNumber(String phoneNumber) {
    MedicApplication.getPreference().edit().putString(PHONE_NUMBER, phoneNumber).apply();
  }

  public static int getLatestVersion() {
    return MedicApplication.getPreference().getInt(LATEST_VERSION, 0);
  }

  public static void setLatestVersion(int latestVersion) {
    MedicApplication.getPreference().edit().putInt(LATEST_VERSION, latestVersion).apply();
  }

  public static boolean isForceExit() {
    return MedicApplication.getPreference().getBoolean(FORCE_EXIT, false);
  }

  public static void setForceExit(boolean forceExit) {
    MedicApplication.getPreference().edit().putBoolean(FORCE_EXIT, forceExit).apply();
  }

  public static String getUpdateUri() {
    return MedicApplication.getPreference().getString(UPDATE_URI, "");
  }

  public static void setUpdateUri(String uri) {
    MedicApplication.getPreference().edit().putString(UPDATE_URI, uri).apply();
  }

  public static void saveKey(KeyValue entity) {
    MedicApplication.getPreference().edit().putString(entity.getKey(), entity.getValue())
        .apply();
  }

  public static KeyValue retrieveByKey(String settingKey) {

    String value = MedicApplication.getPreference().getString(settingKey, "");
    if (value.equals("")) {
      return null;
    }
    return new KeyValue(0L, settingKey, value);
  }

  public static void clearAllKeys() {
    SharedPreferences.Editor editor = MedicApplication.getPreference().edit();
    editor.clear();
    editor.apply();
  }

  public static String getToken() {
    return MedicApplication.getPreference().getString(TOKEN, "");
  }

  public static void setToken(String token) {
    MedicApplication.getPreference().edit().putString(TOKEN, token).apply();
  }


  public static boolean isPermissionGranted() {
    return MedicApplication.getPreference().getBoolean(PERMISSION_GRANTED, false);
  }

  public static void setPermissionGranted(boolean permissionGranted) {
    MedicApplication.getPreference().edit().putBoolean(PERMISSION_GRANTED, permissionGranted)
        .apply();
  }
}
