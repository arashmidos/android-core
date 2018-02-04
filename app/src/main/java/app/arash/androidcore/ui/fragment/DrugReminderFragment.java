package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.arash.androidcore.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DrugReminderFragment extends Fragment {

  Unbinder unbinder;

  public DrugReminderFragment() {
    // Required empty public constructor
  }


  public static DrugReminderFragment newInstance() {
    return new DrugReminderFragment();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drug_reminder, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.reminder)
  public void onViewClicked() {
    Toast.makeText(getActivity(), "بزودی در نسخه آینده", Toast.LENGTH_SHORT).show();
  }
}
