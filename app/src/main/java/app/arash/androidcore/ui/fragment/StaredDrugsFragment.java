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
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DrugListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class StaredDrugsFragment extends Fragment {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  Unbinder unbinder;
  private MainActivity mainActivity;
  private DrugListAdapter drugListAdapter;

  public StaredDrugsFragment() {
    // Required empty public constructor
  }

  public static StaredDrugsFragment newInstance() {
    return new StaredDrugsFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_stared_drugs, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {
    drugListAdapter = new DrugListAdapter(mainActivity, getFavoriteList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugListAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private List<Drug> getFavoriteList() {
    DrugDaoImpl drugDao = new DrugDaoImpl(mainActivity);
    return drugDao.getAllFavourites();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onResume() {
    super.onResume();
    drugListAdapter.update(getFavoriteList());
    EventBus.getDefault().register(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void getMessage(RefreshEvent event) {
    drugListAdapter.update(getFavoriteList());
  }
}