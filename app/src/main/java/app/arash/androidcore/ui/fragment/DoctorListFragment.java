package app.arash.androidcore.ui.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorDeleteEvent;
import app.arash.androidcore.data.entity.DoctorRefreshEvent;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DoctorListAdapter;
import app.arash.androidcore.ui.fragment.dialog.NewDoctorDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class DoctorListFragment extends BaseFragment {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  Unbinder unbinder;
  private MainActivity mainActivity;
  private DoctorListAdapter doctorListAdapter;

  public DoctorListFragment() {
    // Required empty public constructor
  }

  public static DoctorListFragment newInstance() {
    return new DoctorListFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_doctor, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {
    doctorListAdapter = new DoctorListAdapter(mainActivity, getAllDoctors());
    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(doctorListAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private List<Doctor> getAllDoctors() {
    DoctorDaoImpl doctorDao = new DoctorDaoImpl(mainActivity);
    return doctorDao.retrieveAll();
  }


  @Override
  public void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    doctorListAdapter.update(getAllDoctors());
  }

  @Override
  public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void getMessage(DoctorRefreshEvent event) {
    doctorListAdapter.update(getAllDoctors());
  }

  @Subscribe
  public void getMessage(DoctorDeleteEvent event) {
    doctorListAdapter.update(getAllDoctors());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public int getFragmentId() {
    return MainActivity.DOCTORS_FRAGMENT;
  }

  @OnClick({R.id.add_fab})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.add_fab:
        showNewDoctorDialog();
        break;
    }
  }

  private void showNewDoctorDialog() {
    FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
    NewDoctorDialogFragment newDoctorDialogFragment = NewDoctorDialogFragment
        .newInstance(mainActivity, null);
    newDoctorDialogFragment.show(ft, "new doctor");
  }
}
