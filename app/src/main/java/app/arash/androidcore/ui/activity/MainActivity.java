package app.arash.androidcore.ui.activity;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.FabChangedEvent;
import app.arash.androidcore.data.entity.FabChangedEvent.FabStatus;
import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.ui.fragment.BaseFragment;
import app.arash.androidcore.ui.fragment.ChartListFragment;
import app.arash.androidcore.ui.fragment.DoctorListFragment;
import app.arash.androidcore.ui.fragment.DrugsFragment;
import app.arash.androidcore.ui.fragment.HomeFragment;
import app.arash.androidcore.ui.fragment.MoreFragment;
import app.arash.androidcore.ui.fragment.dialog.MeasureListDialogFragment.OnNewMeasureSelected;
import app.arash.androidcore.ui.fragment.dialog.NewMeasureDialogFragment;
import app.arash.androidcore.ui.fragment.dialog.NewMeasureDialogFragment.OnNewMeasureAdded;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements OnNewMeasureSelected,
    OnNewMeasureAdded {

  public static final int HOME_FRAGMENT = 1;
  public static final int MORE_FRAGMENT = 2;
  public static final int DRUGS_FRAGMENT = 3;
  public static final int CHARTS_FRAGMENT = 4;
  public static final int DOCTORS_FRAGMENT = 5;
  private static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.container)
  FrameLayout container;
  @BindView(R.id.chart_img)
  ImageView chartImg;
  @BindView(R.id.medicine_img)
  ImageView medicineImg;
  @BindView(R.id.home_img)
  ImageView homeImg;
  @BindView(R.id.doctor_img)
  ImageView doctorImg;
  @BindView(R.id.more_img)
  ImageView moreImg;
  @BindView(R.id.bottom_navigation_bar)
  LinearLayout bottomNavigationBar;
  @BindView(R.id.chart_tv)
  TextView chartTv;
  @BindView(R.id.medicine_tv)
  TextView medicineTv;
  @BindView(R.id.home_tv)
  TextView homeTv;
  @BindView(R.id.doctor_tv)
  TextView doctorTv;
  @BindView(R.id.more_tv)
  TextView moreTv;
  @BindView(R.id.overlay)
  FrameLayout overlay;
  @BindView(R.id.devider_overlay)
  View deviderOverlay;
  @BindView(R.id.devider)
  View devider;

  private List<Integer> bottomBarImageView;
  private List<Integer> bottomBarTextView;
  private boolean doubleBackToExitPressedOnce = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initBottomBarList();

    if (savedInstanceState == null) {
      setupFragment(HOME_FRAGMENT);
    }
  }

  private void initBottomBarList() {
    bottomBarImageView = new ArrayList<>();
    bottomBarImageView.add(R.id.chart_img);
    bottomBarImageView.add(R.id.medicine_img);
    bottomBarImageView.add(R.id.home_img);
    bottomBarImageView.add(R.id.doctor_img);
    bottomBarImageView.add(R.id.more_img);
    bottomBarTextView = new ArrayList<>();
    bottomBarTextView.add(R.id.chart_tv);
    bottomBarTextView.add(R.id.medicine_tv);
    bottomBarTextView.add(R.id.home_tv);
    bottomBarTextView.add(R.id.doctor_tv);
    bottomBarTextView.add(R.id.more_tv);
  }

  @OnClick({R.id.chart_lay, R.id.medicine_lay, R.id.home_lay, R.id.doctor_lay, R.id.more_lay,
      R.id.overlay})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.chart_lay:
        onBottomBarItemClicked(R.id.chart_tv, R.id.chart_img);
        setupFragment(CHARTS_FRAGMENT);
        break;
      case R.id.medicine_lay:
        onBottomBarItemClicked(R.id.medicine_tv, R.id.medicine_img);
        setupFragment(DRUGS_FRAGMENT);
        break;
      case R.id.home_lay:
        onBottomBarItemClicked(R.id.home_tv, R.id.home_img);
        setupFragment(HOME_FRAGMENT);
        break;
      case R.id.doctor_lay:
        onBottomBarItemClicked(R.id.doctor_tv, R.id.doctor_img);
        setupFragment(DOCTORS_FRAGMENT);
        break;
      case R.id.more_lay:
        onBottomBarItemClicked(R.id.more_tv, R.id.more_img);
        setupFragment(MORE_FRAGMENT);
        break;
      case R.id.overlay:
        EventBus.getDefault().post(new FabChangedEvent(FabStatus.COLLAPSED));
    }
  }

  private void onBottomBarItemClicked(int selectedTv, int selectedImg) {
    for (int i = 0; i < bottomBarTextView.size(); i++) {
      TextView textView = findViewById(bottomBarTextView.get(i));
      ImageView imageView = findViewById(bottomBarImageView.get(i));
      if (bottomBarTextView.get(i) == selectedTv) {
        textView.setTextColor(ContextCompat.getColor(this, R.color.color_primary));
        textView.setTextSize(12f);
      } else {
        textView.setTextColor(ContextCompat.getColor(this, R.color.gray_66));
        textView.setTextSize(10f);
      }
      if (bottomBarImageView.get(i) == selectedImg) {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.color_primary));
      } else {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.gray_9e));
      }
    }
  }

  private void setupFragment(int fragmentId) {
    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
    BaseFragment fragment = null;
    switch (fragmentId) {
      case HOME_FRAGMENT:
        fragment = HomeFragment.newInstance();
        break;
      case MORE_FRAGMENT:
        fragment = MoreFragment.newInstance();
        break;
      case DRUGS_FRAGMENT:
        fragment = DrugsFragment.newInstance();
        break;
      case DOCTORS_FRAGMENT:
        fragment = DoctorListFragment.newInstance();
        break;
      case CHARTS_FRAGMENT:
        fragment = ChartListFragment.newInstance();
        break;
    }
    tr.replace(R.id.container, fragment);
    tr.commit();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  public void showOverlay(boolean show) {
    if (show) {
      overlay.setVisibility(View.VISIBLE);
      devider.setVisibility(GONE);
      deviderOverlay.setVisibility(View.VISIBLE);
    } else {
      overlay.setVisibility(GONE);
      devider.setVisibility(View.VISIBLE);
      deviderOverlay.setVisibility(GONE);
    }
  }

  @Override
  public void addNewMeasure(MeasureDetailType type) {
    android.app.FragmentTransaction ft2 = getFragmentManager().beginTransaction();
    NewMeasureDialogFragment dialogFragment = NewMeasureDialogFragment
        .newInstance(this, type);
    dialogFragment.show(ft2, "new measure");
  }

  @Override
  public void newMeasureAdded(Measure measure) {
    Intent intent = new Intent(this, ChartDetailActivity.class);
    intent.putExtra(Constant.MEASURE, measure);
    startActivity(intent);
  }

  /**
   * handle on back pressed
   */

  @Override
  public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
      finishAffinity();
      return;
    }
    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, R.string.exit_text, Toast.LENGTH_SHORT).show();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);

  }
}
