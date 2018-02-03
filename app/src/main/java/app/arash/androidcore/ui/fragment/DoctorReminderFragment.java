package app.arash.androidcore.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.ui.activity.ContactUsActivity;
import app.arash.androidcore.ui.activity.NewVisitActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author arash
 */
public class DoctorReminderFragment extends Fragment {

  private Doctor doctor;

  public DoctorReminderFragment() {
    // Required empty public constructor
  }


  public static DoctorReminderFragment newInstance(Doctor doctor) {
    DoctorReminderFragment fragment =  new DoctorReminderFragment();
    fragment.doctor=doctor;
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_doctor_reminder, container, false);
    ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @OnClick(R.id.add_visit)
  public void onViewClicked() {
    Intent intent = new Intent(getActivity(), NewVisitActivity.class);
    intent.putExtra(Constant.DOCTOR, doctor);
    startActivity(intent);
  }
}
