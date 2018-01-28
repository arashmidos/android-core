package app.arash.androidcore.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.DrugsViewPagerAdapter;
import app.arash.androidcore.ui.fragment.DrugReminderFragment;
import app.arash.androidcore.ui.fragment.DrugSpecificationFragment;
import app.arash.androidcore.ui.fragment.bottomsheet.DrugBottomSheet;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
      drugNameTv.setText(drug.getNameFa());
      if (drug.isStared()) {
        starImg.setVisibility(View.VISIBLE);
      }
    }
  }

  private void setUpViewPager() {
    DrugsViewPagerAdapter viewPagerAdapter = new DrugsViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter.add(DrugReminderFragment.newInstance(), getString(R.string.reminder));
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
}
