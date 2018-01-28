package app.arash.androidcore.ui.fragment.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.activity.DrugDetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrugBottomSheet extends BottomSheetDialogFragment {

  @BindView(R.id.add_to_stared_tv)
  TextView addToStaredTv;
  @BindView(R.id.add_to_my_drug_tv)
  TextView addToMyDrugTv;
  @BindView(R.id.remove_from_stared_tv)
  TextView removeFromStaredTv;
  @BindView(R.id.remove_from_my_drug_tv)
  TextView removeFromMyDrugTv;
  @BindView(R.id.remove_reminder_tv)
  TextView removeReminderTv;
  @BindView(R.id.reminder_setting_tv)
  TextView reminderSettingTv;
  @BindView(R.id.set_reminder_tv)
  TextView setReminderTv;
  private DrugDetailActivity drugDetailActivity;
  private Drug drug;


  public static DrugBottomSheet newInstance(Drug drug) {
    DrugBottomSheet drugBottomSheet = new DrugBottomSheet();
    drugBottomSheet.drug = drug;
    return drugBottomSheet;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bottom_sheet_drug, container, false);
    ButterKnife.bind(this, view);
    init();
    setData();
    return view;
  }

  /**
   * init variables
   */

  private void init() {
    drugDetailActivity = (DrugDetailActivity) getActivity();
  }

  private void setData() {
    if (drug.isStared()) {
      addToStaredTv.setVisibility(View.GONE);
      removeFromStaredTv.setVisibility(View.VISIBLE);
    } else {
      addToStaredTv.setVisibility(View.VISIBLE);
      removeFromStaredTv.setVisibility(View.GONE);
    }
    if (drug.isMyDrug()) {
      addToMyDrugTv.setVisibility(View.GONE);
      removeFromMyDrugTv.setVisibility(View.VISIBLE);
    } else {
      addToMyDrugTv.setVisibility(View.VISIBLE);
      removeFromMyDrugTv.setVisibility(View.GONE);
    }
    if (drug.isHasAlarmSet()) {
      reminderSettingTv.setVisibility(View.VISIBLE);
      removeReminderTv.setVisibility(View.VISIBLE);
      setReminderTv.setVisibility(View.GONE);
    } else {
      reminderSettingTv.setVisibility(View.GONE);
      removeReminderTv.setVisibility(View.GONE);
      setReminderTv.setVisibility(View.VISIBLE);
    }
  }

  @OnClick({R.id.add_to_stared_tv, R.id.add_to_my_drug_tv, R.id.remove_from_stared_tv,
      R.id.remove_from_my_drug_tv, R.id.remove_reminder_tv, R.id.set_reminder_tv,
      R.id.reminder_setting_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.add_to_stared_tv:
        Toast.makeText(drugDetailActivity, "add to star", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.add_to_my_drug_tv:
        Toast.makeText(drugDetailActivity, "add to my drug", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.remove_from_stared_tv:
        Toast.makeText(drugDetailActivity, "remove from star", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.remove_from_my_drug_tv:
        Toast.makeText(drugDetailActivity, "remove from my drug", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.remove_reminder_tv:
        Toast.makeText(drugDetailActivity, "remove reminder", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.set_reminder_tv:
        Toast.makeText(drugDetailActivity, "set reminder", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
      case R.id.reminder_setting_tv:
        Toast.makeText(drugDetailActivity, "setting reminder", Toast.LENGTH_SHORT).show();
        dismiss();
        break;
    }
  }
}