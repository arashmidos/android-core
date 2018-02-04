package app.arash.androidcore.ui.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.activity.DrugDetailActivity;
import app.arash.androidcore.ui.fragment.dialog.AddDrugDialogFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DrugReminderFragment extends Fragment {

  Unbinder unbinder;
  private Drug drug;

  public DrugReminderFragment() {
    // Required empty public constructor
  }


  public static DrugReminderFragment newInstance(Drug drug) {
    DrugReminderFragment drugReminderFragment = new DrugReminderFragment();
    drugReminderFragment.drug = drug;
    return drugReminderFragment;
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
    FragmentTransaction ftAddDrug = getActivity().getFragmentManager().beginTransaction();
    AddDrugDialogFragment addDrugDialogFragment = AddDrugDialogFragment
        .newInstance(((DrugDetailActivity) getActivity()), drug);
    addDrugDialogFragment.show(ftAddDrug, "add drug");
  }
}
