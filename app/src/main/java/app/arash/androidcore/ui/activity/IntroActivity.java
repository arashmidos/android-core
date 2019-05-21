package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import app.arash.androidcore.R;
import app.arash.androidcore.ui.adapter.IntroPagerAdapter;
import app.arash.androidcore.util.DepthPageTransformer;
import app.arash.androidcore.util.Empty;
import app.arash.androidcore.util.PreferenceHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends AppCompatActivity {

  @BindView(R.id.view_pager)
  ViewPager pager;
  @BindView(R.id.indicator_default)
  CircleIndicator indicator;

  private IntroPagerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent;
    if (PreferenceHelper.hasSeenIntro()) {
      if (Empty.isEmpty(PreferenceHelper.getPhoneNumber()) || Empty
          .isEmpty(PreferenceHelper.getToken())) {
        intent = new Intent(this, NewPhoneActivity.class);
      } else {
        intent = new Intent(this, MainActivity.class);
      }
      startActivity(intent);
      finish();
    } else {
      setContentView(R.layout.activity_intro);
      ButterKnife.bind(this);
      init();
      setData();
    }
  }

  private void init() {
    adapter = new IntroPagerAdapter(getSupportFragmentManager(), this);
  }

  private void setData() {
    pager.setAdapter(adapter);
    indicator.setViewPager(pager);
    pager.setPageTransformer(true, new DepthPageTransformer());
  }

  @Override
  public void onBackPressed() {
    if (pager.getCurrentItem() == 0) {
      super.onBackPressed();
    } else {
      pager.setCurrentItem(pager.getCurrentItem() - 1);
    }
  }

  @OnClick(R.id.enter_tv)
  public void onClick() {
    PreferenceHelper.setSeenIntro(true);
    Intent intent = new Intent(this, NewPhoneActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
