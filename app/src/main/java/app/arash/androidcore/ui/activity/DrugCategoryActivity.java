package app.arash.androidcore.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.adapter.DrugListAdapter;
import app.arash.androidcore.ui.fragment.dialog.SearchDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DrugCategoryActivity extends AppCompatActivity {

  @BindView(R.id.toolbar_title_tv)
  TextView toolbarTitleTv;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private String drugCategory;
  private DrugListAdapter drugListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drug_category);
    ButterKnife.bind(this);
    getIntentData();
    setUpRecyclerView();
  }

  private void getIntentData() {
    if (getIntent() != null && getIntent().getExtras() != null
        && getIntent().getExtras().getString(Constant.DRUG_CATEGORY) != null) {

      drugCategory = getIntent().getExtras().getString(Constant.DRUG_CATEGORY);
      toolbarTitleTv.setText(drugCategory);
    }
  }

  private void setUpRecyclerView() {
    drugListAdapter = new DrugListAdapter(this, getDrugListByCategory());
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugListAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private List<Drug> getDrugListByCategory() {
    DrugDaoImpl drugDao = new DrugDaoImpl(this);
    return drugDao.getAllDrugsByCategory(drugCategory);
  }

  private void showSearchDialog() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    SearchDialogFragment searchDialogFragment = SearchDialogFragment.newInstance(this, true);
    searchDialogFragment.show(ft, "search");
  }

  @OnClick({R.id.search_img, R.id.back_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.search_img:
        showSearchDialog();
        break;
      case R.id.back_img:
        onBackPressed();
        break;
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    drugListAdapter.update(getDrugListByCategory());
  }

  @Subscribe
  public void getMessage(RefreshEvent event) {
    drugListAdapter.update(getDrugListByCategory());
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
