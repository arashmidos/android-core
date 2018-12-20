package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorDeleteEvent;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.entity.SearchHistory;
import app.arash.androidcore.data.impl.SearchDaoImpl;
import app.arash.androidcore.ui.adapter.DrugsViewPagerAdapter;
import app.arash.androidcore.ui.fragment.DoctorReminderFragment;
import app.arash.androidcore.ui.fragment.DoctorSpecificationFragment;
import app.arash.androidcore.ui.fragment.bottomsheet.DoctorBottomSheet;
import app.arash.androidcore.util.Empty;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DoctorDetailActivity extends AppCompatActivity {

  @BindView(R.id.doctor_name_tv)
  TextView doctorNameTv;
  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.view_pager)
  ViewPager viewPager;

  private Doctor doctor;
  private String type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_doctor_detail);
    ButterKnife.bind(this);
    getIntentData();
    tabLayout.setupWithViewPager(viewPager);
    setUpViewPager();
    if (Empty.isNotEmpty(type)) {
      viewPager.setCurrentItem(0);
    } else {
      viewPager.setCurrentItem(1);
    }
  }

  private void getIntentData() {
    if (getIntent() != null && getIntent().getSerializableExtra(Constant.DOCTOR_OBJ) != null) {
      doctor = (Doctor) getIntent().getSerializableExtra(Constant.DOCTOR_OBJ);
      doctorNameTv.setText(doctor.getName());

      addDoctorToHistory(doctor);
      type = getIntent().getStringExtra(Constant.VIEW_TYPE);
    } else {
      //return error
    }
  }

  private void addDoctorToHistory(Doctor doctor) {
    SearchDaoImpl searchDao = new SearchDaoImpl(this);
    searchDao.create(new SearchHistory(2, doctor.getName()));
  }

  private void setUpViewPager() {
    DrugsViewPagerAdapter viewPagerAdapter = new DrugsViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter
        .add(DoctorReminderFragment.newInstance(doctor), getString(R.string.visit_time));
    viewPagerAdapter
        .add(DoctorSpecificationFragment.newInstance(doctor), getString(R.string.specification));
    viewPager.setAdapter(viewPagerAdapter);
  }

  @OnClick({R.id.more_img, R.id.back_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.more_img:
        DoctorBottomSheet doctorBottomSheet = DoctorBottomSheet.newInstance(doctor, null, false);
        doctorBottomSheet.show(getSupportFragmentManager(), "drug bottom sheet");
        break;
      case R.id.back_img:
        onBackPressed();
        break;
    }
  }

  public void refresh(Doctor doctor) {

  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void getMessage(RefreshEvent event) {

  }

  @Subscribe
  public void getMessage(DoctorDeleteEvent event) {
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
