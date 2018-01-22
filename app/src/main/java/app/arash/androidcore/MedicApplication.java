package app.arash.androidcore;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import app.arash.androidcore.util.Constants;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Arash
 */
public class MedicApplication extends MultiDexApplication {

  public static MedicApplication sInstance;

  public static SharedPreferences sPreference;

  public static MedicApplication getInstance() {
    return sInstance;
  }

  public static SharedPreferences getPreference() {
    if (sPreference == null) {
      sPreference = PreferenceManager.getDefaultSharedPreferences(
          sInstance.getApplicationContext());
    }

    return sPreference;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sInstance = this;

    if (!BuildConfig.DEBUG) {
      Fabric.with(this, new Crashlytics());
    }

    MultiDex.install(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
          .setDefaultFontPath("fonts/IRANSansMobile.ttf")
          .setFontAttrId(R.attr.fontPath)
          .build());
    }

    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    setLanguage();
  }

  public void setLanguage() {
    Locale locale = new Locale(Constants.DEFAULT_LANGUAGE);

    Resources res = getResources();
    DisplayMetrics dm = res.getDisplayMetrics();
    Configuration conf = res.getConfiguration();
    conf.locale = locale;
    res.updateConfiguration(conf, dm);
  }
}
