package app.arash.androidcore.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DrugCategoryAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.List;


public class DrugCategoryFragment extends Fragment {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  Unbinder unbinder;
  private MainActivity mainActivity;

  public DrugCategoryFragment() {
    // Required empty public constructor
  }

  public static DrugCategoryFragment newInstance() {
    return new DrugCategoryFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drug_category, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {

    DrugCategoryAdapter adapter = new DrugCategoryAdapter(mainActivity, getCategoryList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private List<String> getCategoryList() {
    DrugDaoImpl drugDao = new DrugDaoImpl(mainActivity);
    return drugDao.getAllCategory();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
