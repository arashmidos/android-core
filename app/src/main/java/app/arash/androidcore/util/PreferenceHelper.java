package app.arash.androidcore.util;

import android.content.SharedPreferences;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.entity.KeyValue;

public class PreferenceHelper {

  public static final String FORCE_EXIT = "FORCE_EXIT";
  private static final String LATEST_VERSION = "LATEST_VERSION";
  private static final String UPDATE_URI = "UPDATE_URI";

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
}
