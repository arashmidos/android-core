package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.CustomSpinnerAdapter;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Locale;

/**
 * Created by Arash on 1/24/18.
 */

public class AddDrugReminderDialogFragment extends DialogFragment {

  @BindView(R.id.spinner)
  Spinner spinner;
  @BindView(R.id.time_tv)
  TextView timeTv;
  @BindView(R.id.number_tv)
  TextView numberTv;
  @BindView(R.id.every_day_radio)
  RadioButton everyDayRadio;
  @BindView(R.id.specific_day_radio)
  RadioButton specificDayRadio;
  @BindView(R.id.radio_group)
  RadioGroup radioGroup;
  @BindView(R.id.instruction_spinner)
  Spinner instructionSpinner;
  @BindView(R.id.days_tv)
  TextView daysTv;
  @BindView(R.id.drug_tv)
  TextView drugTv;
  @BindView(R.id.reminder_detail_lay)
  LinearLayout reminderDetailLay;
  @BindView(R.id.reminder_sw)
  Switch reminderSw;
  @BindView(R.id.usage_detail_lay)
  RelativeLayout usageDetailLay;

  private AppCompatActivity context;
  private String numberInEachTime;
  private int hour;
  private Drug drug;
  private int minute;

  public static AddDrugReminderDialogFragment newInstance(AppCompatActivity context, Drug drug) {
    AddDrugReminderDialogFragment fragment = new AddDrugReminderDialogFragment();
    fragment.context = context;
    fragment.drug = drug;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.myDialog);
    setRetainInstance(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_add_drug, container, false);
    ButterKnife.bind(this, view);
    setData();
    return view;
  }

  private void setData() {
    spinner.setAdapter(
        new CustomSpinnerAdapter(getActivity(), R.layout.row_layout_label_value_spinner,
            getNumberOfUse(), getString(R.string.number_of_use_in_day)));

    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!spinner.getSelectedItem().equals(getString(R.string.number_of_use_in_day))) {
          usageDetailLay.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    instructionSpinner.setAdapter(
        new CustomSpinnerAdapter(getActivity(), R.layout.row_layout_label_value_spinner,
            getInstruction(), getString(R.string.usage_instruction)));

    numberInEachTime = getActivity().getString(R.string.one_item);
    hour = 8;
    minute = 0;
    timeTv.setText(NumberUtil.digitsToPersian("8:00"));

    if (drug != null) {
      drugTv.setText(drug.getNameFa().trim());
    }
    reminderSw.setOnCheckedChangeListener((compoundButton, b) -> {
      if (b) {
        reminderDetailLay.setVisibility(View.VISIBLE);
      } else {
        reminderDetailLay.setVisibility(View.GONE);
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    reminderDetailLay.setVisibility(View.GONE);
  }

  private String[] getNumberOfUse() {
    String[] item = new String[5];
    item[0] = "۱ بار در روز";
    item[1] = "۲ بار در روز";
    item[2] = "۳ بار در روز";
    item[3] = "۴ بار در روز";
    item[4] = "۵ بار در روز";
    item[4] = "۶ بار در روز";
    item[4] = "۷ بار در روز";
    item[4] = "۸ بار در روز";
    item[4] = "۹ بار در روز";
    item[4] = "۱۰ بار در روز";
    item[4] = "۱۱ بار در روز";
    item[4] = "۱۲ بار در روز";
    return item;
  }

  private String[] getInstruction() {
    String[] item = new String[4];
    item[0] = "قبل از وعده غذایی";
    item[1] = "همراه با وعده غذایی";
    item[2] = "بعد از وعده غذایی";
    item[3] = "دستورالعمل خاصی وجود ندارد";
    return item;
  }

  private void showNumberDialog() {
    Builder dialogBuilder = new Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_number_of_usage, null);
    dialogBuilder.setView(dialogView);

    RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
    AlertDialog alertDialog = dialogBuilder.create();

    RadioButton selectedItem = radioGroup.findViewWithTag(numberInEachTime);
    selectedItem.setChecked(true);
    alertDialog.show();
    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      RadioButton selectedRadio = (RadioButton) dialogView.findViewById(checkedId);
      numberInEachTime = (String) selectedRadio.getTag();
      numberTv.setText(numberInEachTime);
      alertDialog.cancel();
    });
  }

  private void showDaysDialog() {
    Builder dialogBuilder = new Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_days, null);
    dialogBuilder.setView(dialogView);

    CheckBox saturdayChb = dialogView.findViewById(R.id.saturday_chb);
    CheckBox sundayChb = dialogView.findViewById(R.id.sunday_chb);
    CheckBox mondayChb = dialogView.findViewById(R.id.monday_chb);
    CheckBox tuesdayChb = dialogView.findViewById(R.id.tuesday_chb);
    CheckBox wednesdayChb = dialogView.findViewById(R.id.wednesday_chb);
    CheckBox thursdayChb = dialogView.findViewById(R.id.thursday_chb);
    CheckBox fridayChb = dialogView.findViewById(R.id.friday_chb);
    TextView registerTv = dialogView.findViewById(R.id.register_tv);
    TextView cancelTv = dialogView.findViewById(R.id.cancel_tv);
    AlertDialog alertDialog = dialogBuilder.create();

    alertDialog.show();
    cancelTv.setOnClickListener(view -> alertDialog.dismiss());
    registerTv.setOnClickListener(view -> {
      String days = "";
      if (saturdayChb.isChecked()) {
        days += getString(R.string.saturday_comma);
      }
      if (sundayChb.isChecked()) {
        days += getString(R.string.sunday_comma);
      }
      if (mondayChb.isChecked()) {
        days += getString(R.string.monday_comma);
      }
      if (tuesdayChb.isChecked()) {
        days += getString(R.string.tuesday_comma);
      }
      if (wednesdayChb.isChecked()) {
        days += getString(R.string.wednesday_comma);
      }
      if (thursdayChb.isChecked()) {
        days += getString(R.string.thursday_comma);
      }
      if (fridayChb.isChecked()) {
        days += getString(R.string.friday_comma);
      }
      days = days.substring(0, days.length() - 1);
      daysTv.setText(days);
      alertDialog.dismiss();
    });
  }

  private void showTimePicker() {
// Get Current Time
    TimePickerDialog dialog = new TimePickerDialog(context, (timePicker, i, i1) -> {
      timeTv.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%d:%02d", i, i1)));
      hour = i;
      minute = i1;
    }, hour, minute, true);
    dialog.show();
  }

  @OnClick({R.id.done_img, R.id.close_img, R.id.reminder_sw, R.id.time_tv, R.id.number_tv,
      R.id.every_day_radio, R.id.specific_day_radio, R.id.drug_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        getDialog().dismiss();
        break;
      case R.id.drug_tv:
        showSearchDialog();
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
      case R.id.reminder_sw:
        if (reminderSw.isChecked()) {
          reminderDetailLay.setVisibility(View.VISIBLE);
        } else {
          reminderDetailLay.setVisibility(View.GONE);
        }
        break;
      case R.id.time_tv:
        showTimePicker();
        break;
      case R.id.number_tv:
        showNumberDialog();
        break;
      case R.id.every_day_radio:
        everyDayRadio.setChecked(true);
        specificDayRadio.setChecked(false);
        daysTv.setVisibility(View.GONE);
        break;
      case R.id.specific_day_radio:
        everyDayRadio.setChecked(false);
        specificDayRadio.setChecked(true);
        daysTv.setVisibility(View.VISIBLE);
        daysTv.setText("");
        showDaysDialog();
        break;
    }
  }

  private void showSearchDialog() {
    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
    SearchDialogFragment searchDialogFragment = SearchDialogFragment
        .newInstance(getActivity(), true, this);
    searchDialogFragment.show(ft, "search");
  }

  public void setSelectedDrug(Drug drug) {
    this.drug = drug;
    drugTv.setText(drug.getNameFa().trim());
  }

}
