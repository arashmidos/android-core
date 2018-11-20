package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.TrackHelper;

public class AboutUsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_us);
    ButterKnife.bind(this);

    Tracker tracker = MedicApplication.getInstance().getTracker();

    TrackHelper.track().screen("/activity/about").title("About").with(tracker);

  }

  @OnClick(R.id.back_img)
  public void onViewClicked() {
    onBackPressed();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
