package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.data.impl.MeasureDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.MeasureListAdapter2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.List;


public class ChartListFragment extends BaseFragment {

  Unbinder unbinder;
  @BindView(R.id.my_list)
  RecyclerView myList;
  @BindView(R.id.chart_list)
  RecyclerView chartList;
  @BindView(R.id.my_chart_layout)
  LinearLayout myChartLayout;
  @BindView(R.id.all_chart_layout)
  LinearLayout allChartLayout;
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
    MeasureDetailType[] values = MeasureDetailType.values();
    MeasureDaoImpl measureDao = new MeasureDaoImpl(mainActivity);
    List<Integer> userCharts = measureDao.getAllUserCharts();
    ArrayList<MeasureDetailType> myCharts = new ArrayList<>();
    ArrayList<MeasureDetailType> allCharts = new ArrayList<>();

    for (int i = 0; i < values.length; i++) {
      if (userCharts.contains(values[i].getId())) {
        myCharts.add(values[i]);
      } else {
        allCharts.add(values[i]);
      }
    }
    MeasureListAdapter2 myChartAdapter = new MeasureListAdapter2(mainActivity, myCharts);
    MeasureListAdapter2 allChartAdapter = new MeasureListAdapter2(mainActivity, allCharts);
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(mainActivity);
    if (myCharts.size() == 0) {
      myList.setVisibility(View.GONE);
      myChartLayout.setVisibility(View.GONE);
    }
    myList.setAdapter(myChartAdapter);
    myList.setLayoutManager(layoutManager2);
    if (allCharts.size() == 0) {
      chartList.setVisibility(View.GONE);
      allChartLayout.setVisibility(View.GONE);
    }
    chartList.setAdapter(allChartAdapter);
    chartList.setLayoutManager(layoutManager);
  }

  @Override
  public void onResume() {
    super.onResume();
    setUpRecyclerView();
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
