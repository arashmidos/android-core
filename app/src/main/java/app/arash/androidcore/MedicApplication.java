package app.arash.androidcore;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import app.arash.androidcore.util.Constants;
import co.ronash.pushe.Pushe;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import java.util.Locale;
import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.TrackerConfig;

/**
 * @author Arash
 */
public class MedicApplication extends MultiDexApplication {

  public static MedicApplication sInstance;

  public static SharedPreferences sPreference;
  private Tracker mPiwikTracker;

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

    ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    setLanguage();

    Pushe.initialize(this, true);

    Log.d("Pushe", Pushe.getPusheId(this));
    Log.d("Test", "");
  }

  public synchronized Tracker getTracker() {
    if (mPiwikTracker != null) {
      return mPiwikTracker;
    }
    mPiwikTracker = Piwik
        .getInstance(this)
        .newTracker(
            new TrackerConfig("https://rasavas.ir/matomo/piwik.php", BuildConfig.PIWIK_SITE_ID,
                "FitPress Android App"));
    return mPiwikTracker;
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
