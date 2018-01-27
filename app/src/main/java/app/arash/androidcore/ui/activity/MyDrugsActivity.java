package app.arash.androidcore.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.DrugListAdapter;
import app.arash.androidcore.ui.fragment.dialog.SearchDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDrugsActivity extends AppCompatActivity {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_drugs);
    ButterKnife.bind(this);
    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    //TODO:need to load my drugs list
    DrugListAdapter drugListAdapter = new DrugListAdapter(this, Drug.getDrugList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugListAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private void showSearchDialog() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    SearchDialogFragment searchDialogFragment = SearchDialogFragment.newInstance(this);
    searchDialogFragment.show(ft, "search");
  }

  @OnClick({R.id.search_img, R.id.back_img, R.id.add_fab})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.search_img:
        showSearchDialog();
        break;
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.add_fab:
        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}
