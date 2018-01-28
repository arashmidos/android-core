package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import app.arash.androidcore.R;
import app.arash.androidcore.ui.adapter.IntroPagerAdapter;
import app.arash.androidcore.util.DepthPageTransformer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroActivity extends AppCompatActivity {

  @BindView(R.id.view_pager)
  ViewPager pager;
  @BindView(R.id.indicator_default)
  CircleIndicator indicator;

  private IntroPagerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);
    ButterKnife.bind(this);
    init();
    setData();
  }

  /**
   * init variables
   */

  private void init() {
    adapter = new IntroPagerAdapter(getSupportFragmentManager(), this);
  }

  /**
   * set data
   */

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
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
