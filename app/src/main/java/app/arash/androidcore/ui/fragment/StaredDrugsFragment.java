package app.arash.androidcore.ui.fragment;

import android.content.Context;
import android.net.Uri;
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
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DrugListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StaredDrugsFragment extends Fragment {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  Unbinder unbinder;
  private MainActivity mainActivity;

  public StaredDrugsFragment() {
    // Required empty public constructor
  }

  public static StaredDrugsFragment newInstance() {
    StaredDrugsFragment fragment = new StaredDrugsFragment();
    return fragment;
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
    //TODO:need to load favorite list
    DrugListAdapter drugListAdapter = new DrugListAdapter(mainActivity, Drug.getFavoriteDrugList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugListAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}