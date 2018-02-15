package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.entity.SearchHistory;
import app.arash.androidcore.data.impl.SearchDaoImpl;
import app.arash.androidcore.ui.adapter.DrugsViewPagerAdapter;
import app.arash.androidcore.ui.fragment.DrugReminderFragment;
import app.arash.androidcore.ui.fragment.DrugSpecificationFragment;
import app.arash.androidcore.ui.fragment.bottomsheet.DrugBottomSheet;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DrugDetailActivity extends AppCompatActivity {

  @BindView(R.id.star_img)
  ImageView starImg;
  @BindView(R.id.drug_name_tv)
  TextView drugNameTv;
  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.view_pager)
  ViewPager viewPager;

  private Drug drug;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drug_detail);
    ButterKnife.bind(this);
    getIntentData();
    tabLayout.setupWithViewPager(viewPager);
    setUpViewPager();
    viewPager.setCurrentItem(1);
  }

  private void getIntentData() {
    if (getIntent() != null && getIntent().getSerializableExtra(Constant.DRUG_OBJ) != null) {
      drug = (Drug) getIntent().getSerializableExtra(Constant.DRUG_OBJ);
      drugNameTv.setText(drug.getNameFa().trim());
      if (drug.isStared()) {
        starImg.setVisibility(View.VISIBLE);
      }
      addDrugToHistory(drug);
    }
  }

  private void addDrugToHistory(Drug drug) {
    SearchDaoImpl searchDao = new SearchDaoImpl(this);
    searchDao.create(new SearchHistory(1, drug.getNameFa()));
  }

  private void setUpViewPager() {
    DrugsViewPagerAdapter viewPagerAdapter = new DrugsViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter.add(DrugReminderFragment.newInstance(drug), getString(R.string.reminder));
    viewPagerAdapter
        .add(DrugSpecificationFragment.newInstance(drug), getString(R.string.specification));
    viewPager.setAdapter(viewPagerAdapter);
  }

  @OnClick({R.id.more_img, R.id.back_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.more_img:
        DrugBottomSheet drugBottomSheet = DrugBottomSheet.newInstance(drug);
        drugBottomSheet.show(getSupportFragmentManager(), "drug bottom sheet");
        break;
      case R.id.back_img:
        onBackPressed();
        break;
    }
  }

  public void refresh(Drug drug) {
    if (drug.isStared()) {
      starImg.setVisibility(View.VISIBLE);
    } else {
      starImg.setVisibility(View.GONE);
    }
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
    if (event.getDrug() != null) {
      refresh(event.getDrug());
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
