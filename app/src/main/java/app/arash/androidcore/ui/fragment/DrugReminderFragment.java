package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;


public class DrugReminderFragment extends Fragment {

  public DrugReminderFragment() {
    // Required empty public constructor
  }


  public static DrugReminderFragment newInstance() {
    DrugReminderFragment fragment = new DrugReminderFragment();
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drug_reminder, container, false);
    return view;
  }

}
