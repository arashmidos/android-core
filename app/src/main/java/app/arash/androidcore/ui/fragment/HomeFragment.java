package app.arash.androidcore.ui.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Medicine;
import app.arash.androidcore.data.entity.Visit;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.MedicineAdapter;
import app.arash.androidcore.ui.adapter.VisitAdapter;
import app.arash.androidcore.ui.fragment.dialog.NewDoctorDialogFragment;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.Date;
import java.util.List;

public class HomeFragment extends BaseFragment {

  @BindView(R.id.medicine_recycler_view)
  RecyclerView medicineRecyclerView;
  Unbinder unbinder;
  @BindView(R.id.medicines_tv)
  TextView medicinesTv;
  @BindView(R.id.medicine_more_divider)
  View medicineMoreDivider;
  @BindView(R.id.more_medicine_tv)
  TextView moreMedicineTv;
  @BindView(R.id.medicine_card)
  CardView medicineCard;
  @BindView(R.id.visit_recycler_view)
  RecyclerView visitRecyclerView;
  @BindView(R.id.medicine_empty_view)
  LinearLayout medicineEmptyView;
  @BindView(R.id.visit_empty_view)
  CardView visitEmptyView;
  @BindView(R.id.toolbar_date)
  TextView toolbarDate;
  @BindView(R.id.visit_tv)
  TextView visitTv;
  @BindView(R.id.set_visit_tv)
  TextView setVisitTv;
  @BindView(R.id.charts_tv)
  TextView chartsTv;
  @BindView(R.id.healthy_chart_recycler_view)
  RecyclerView healthyChartRecyclerView;

  private MedicineAdapter medicineAdapter;
  private VisitAdapter visitAdapter;
  private MainActivity mainActivity;

  public HomeFragment() {
    // Required empty public constructor
  }


  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setDate();
    setUpMedicineRecyclerView();
    setUpVisitRecyclerView();
    return view;
  }

  private void setDate() {
    String dateString = DateUtil.getFullPersianDate(new Date());
    toolbarDate.setText(String.format("امروز، %s", NumberUtil.digitsToPersian(dateString)));
  }

  private void setUpMedicineRecyclerView() {
    List<Medicine> medicines = Medicine.getMedicineList();
    if (medicines.size() == 0) {
      medicineRecyclerView.setVisibility(View.GONE);
      medicineEmptyView.setVisibility(View.VISIBLE);
      moreMedicineTv.setVisibility(View.VISIBLE);
      medicineMoreDivider.setVisibility(View.VISIBLE);
      moreMedicineTv.setText(getString(R.string.set_drug_alarm));
    } else {
      visitEmptyView.setVisibility(View.GONE);
      medicineRecyclerView.setVisibility(View.VISIBLE);
      if (medicines.size() > 3) {
        moreMedicineTv.setVisibility(View.VISIBLE);
        medicineMoreDivider.setVisibility(View.VISIBLE);
        medicines = medicines.subList(0, 3);
      } else {
        moreMedicineTv.setVisibility(View.GONE);
        medicineMoreDivider.setVisibility(View.GONE);
      }
      medicinesTv.setVisibility(View.VISIBLE);
      medicineCard.setVisibility(View.VISIBLE);
      medicineAdapter = new MedicineAdapter(mainActivity, medicines);
      LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
      medicineRecyclerView.setAdapter(medicineAdapter);
      medicineRecyclerView.setLayoutManager(layoutManager);
    }
  }

  private void setUpVisitRecyclerView() {
    List<Visit> visits = Visit.getVisitList();
    if (visits.size() != 0) {
      visitRecyclerView.setVisibility(View.VISIBLE);
      visitEmptyView.setVisibility(View.GONE);
      visitAdapter = new VisitAdapter(mainActivity, visits);
      LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
      visitRecyclerView.setAdapter(visitAdapter);
      visitRecyclerView.setLayoutManager(layoutManager);
    } else {
      visitRecyclerView.setVisibility(View.GONE);
      visitEmptyView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getFragmentId() {
    return MainActivity.HOME_FRAGMENT;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.add_visit, R.id.add_doctor, R.id.add_chart, R.id.add_reminder,
      R.id.more_medicine_tv, R.id.set_visit_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.add_visit:
        break;
      case R.id.add_doctor:
        FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        NewDoctorDialogFragment newDoctorDialogFragment = NewDoctorDialogFragment
            .newInstance(mainActivity, null);
        newDoctorDialogFragment.show(ft, "new doctor");

        break;
      case R.id.add_chart:
        break;
      case R.id.add_reminder:
        break;
      case R.id.more_medicine_tv:
        Toast.makeText(mainActivity, "more", Toast.LENGTH_SHORT).show();
        break;
      case R.id.set_visit_tv:
        Toast.makeText(mainActivity, "set visit tv", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}
