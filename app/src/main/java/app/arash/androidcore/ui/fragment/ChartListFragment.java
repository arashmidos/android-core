package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.MeasureListAdapter2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ChartListFragment extends BaseFragment {

  Unbinder unbinder;
  @BindView(R.id.my_list)
  RecyclerView myList;
  @BindView(R.id.chart_list)
  RecyclerView chartList;
  private MainActivity mainActivity;

  public ChartListFragment() {
    // Required empty public constructor
  }

  public static ChartListFragment newInstance() {
    return new ChartListFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_chart_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {
    MeasureListAdapter2 adapter = new MeasureListAdapter2(mainActivity, MeasureDetailType.values());
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    chartList.setAdapter(adapter);
    chartList.setLayoutManager(layoutManager);
  }


  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public int getFragmentId() {
    return MainActivity.CHARTS_FRAGMENT;
  }
}
