package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;

/**
 * @author arash
 */
public class DoctorReminderFragment extends Fragment {

  public DoctorReminderFragment() {
    // Required empty public constructor
  }


  public static DoctorReminderFragment newInstance() {
    return new DoctorReminderFragment();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_doctor_reminder, container, false);
  }

}
