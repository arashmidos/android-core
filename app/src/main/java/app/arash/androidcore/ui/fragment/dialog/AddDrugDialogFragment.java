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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Arash on 1/24/18.
 */

public class AddDrugDialogFragment extends DialogFragment {


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

  private AppCompatActivity context;
  private String numberInEachTime;
  private int hour;
  private Drug drug;
  private int minute;

  public static AddDrugDialogFragment newInstance(AppCompatActivity context, Drug drug) {
    AddDrugDialogFragment fragment = new AddDrugDialogFragment();
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
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, getNumberOfUse());
    spinner.setAdapter(adapter);
    ArrayAdapter<String> instructionAdapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, getInstruction());
    instructionSpinner.setAdapter(instructionAdapter);
    numberInEachTime = getActivity().getString(R.string.one_item);
    hour = 8;
    minute = 0;
    timeTv.setText(NumberUtil.digitsToPersian("8:00"));
    if (drug != null) {
      drugTv.setText(drug.getNameFa().trim());
    }
  }

  private List<String> getNumberOfUse() {
    List<String> item = new ArrayList<>();
    item.add("۱ بار در روز");
    item.add("۲ بار در روز");
    item.add("۳ بار در روز");
    item.add("۴ بار در روز");
    item.add("۵ بار در روز");
    return item;
  }

  private List<String> getInstruction() {
    List<String> item = new ArrayList<>();
    item.add("قبل از وعده غذایی");
    item.add("همراه با وعده غذایی");
    item.add("بعد از وعده غذایی");
    item.add("دستورالعمل خاصی وجود ندارد");
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
    cancelTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        alertDialog.dismiss();
      }
    });
    registerTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
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
      }
    });
//    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//      RadioButton selectedRadio = (RadioButton) dialogView.findViewById(checkedId);
//      numberInEachTime = (String) selectedRadio.getTag();
//      numberTv.setText(numberInEachTime);
//      alertDialog.cancel();
//    });
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
