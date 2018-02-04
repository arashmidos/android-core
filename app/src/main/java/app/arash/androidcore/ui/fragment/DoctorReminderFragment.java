package app.arash.androidcore.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.data.impl.DoctorVisitDaoImpl;
import app.arash.androidcore.ui.activity.NewVisitActivity;
import app.arash.androidcore.ui.adapter.DoctorVisitAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.List;

/**
 * @author arash
 */
public class DoctorReminderFragment extends Fragment {

  @BindView(R.id.visit_empty_view)
  LinearLayout visitEmptyView;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.add_fab)
  FloatingActionButton addFab;

  private Unbinder unbinder;
  private DoctorVisitDaoImpl doctorVisitDao;
  private Doctor doctor;

  public DoctorReminderFragment() {
    // Required empty public constructor
  }


  public static DoctorReminderFragment newInstance(Doctor doctor) {
    DoctorReminderFragment doctorReminderFragment = new DoctorReminderFragment();
    doctorReminderFragment.doctor = doctor;
    return doctorReminderFragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_doctor_reminder, container, false);
    unbinder = ButterKnife.bind(this, view);
    doctorVisitDao = new DoctorVisitDaoImpl(getActivity());
    return view;
  }

  private void setData() {
    List<DoctorVisit> doctorVisits = doctorVisitDao.getAllVisitByDoctorId(doctor.getId());
    if (doctorVisits == null || doctorVisits.size() == 0) {
      recyclerView.setVisibility(View.GONE);
      visitEmptyView.setVisibility(View.VISIBLE);
      addFab.setVisibility(View.GONE);
    } else {
      recyclerView.setVisibility(View.VISIBLE);
      visitEmptyView.setVisibility(View.GONE);
      addFab.setVisibility(View.VISIBLE);
      setUpRecyclerView(doctorVisits);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    setData();
  }

  private void setUpRecyclerView(
      List<DoctorVisit> doctorVisits) {
    DoctorVisitAdapter doctorVisitAdapter = new DoctorVisitAdapter(getActivity(), doctorVisits,
        doctor);
    LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setAdapter(doctorVisitAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.add_fab)
  public void onViewClicked() {
    startActivity(new Intent(getActivity(), NewVisitActivity.class));
  }
}
