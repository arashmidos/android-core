package app.arash.androidcore.ui.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.ui.activity.DrugDetailActivity;
import app.arash.androidcore.ui.fragment.dialog.AddDrugReminderDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DrugReminderFragment extends Fragment {

  @BindView(R.id.empty_view)
  LinearLayout emptyView;
  @BindView(R.id.time_lay)
  LinearLayout timeLay;
  @BindView(R.id.number_tv)
  TextView numberTv;
  @BindView(R.id.usage_hour_tv)
  TextView usageHourTv;
  @BindView(R.id.meal_tv)
  TextView mealTv;
  @BindView(R.id.detail_lay)
  ScrollView detailLay;
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
    ButterKnife.bind(this, view);

    setData();
    return view;
  }

  private void setData() {
    if (!drug.isHasAlarmSet()) {
      emptyView.setVisibility(View.VISIBLE);
      detailLay.setVisibility(View.GONE);
    }else{
      detailLay.setVisibility(View.VISIBLE);
      emptyView.setVisibility(View.GONE);
    }
  }

  @OnClick(R.id.reminder)
  public void onViewClicked() {
    FragmentTransaction ftAddDrug = getActivity().getFragmentManager().beginTransaction();
    AddDrugReminderDialogFragment addDrugReminderDialogFragment = AddDrugReminderDialogFragment
        .newInstance(((DrugDetailActivity) getActivity()), drug);
    addDrugReminderDialogFragment.show(ftAddDrug, "add drug");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @Override
  public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
//    drugListAdapter.update(getDrugListByCategory());
  }

  @Subscribe
  public void getMessage(RefreshEvent event) {
    this.drug = event.getDrug();
    setData();
  }

}
