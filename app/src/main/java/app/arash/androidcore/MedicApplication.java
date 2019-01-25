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
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.util.Constants;
import co.ronash.pushe.Pushe;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.github.inflationx.calligraphy3.CalligraphyConfig.Builder;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import java.util.List;
import java.util.Locale;

/**
 * @author Arash
 */
public class MedicApplication extends MultiDexApplication {

  public static MedicApplication sInstance;

  public static SharedPreferences sPreference;
  private List<Category> categories;

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

//    if (!BuildConfig.DEBUG) {
    Fabric.with(this, new Crashlytics());
//    }

    MultiDex.install(this);

    ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
            new Builder()
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

  public void setLanguage() {
    Locale locale = new Locale(Constants.DEFAULT_LANGUAGE);

    Resources res = getResources();
    DisplayMetrics dm = res.getDisplayMetrics();
    Configuration conf = res.getConfiguration();
    conf.locale = locale;
    res.updateConfiguration(conf, dm);
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
}
