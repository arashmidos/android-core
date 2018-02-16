package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.DrugAlarm;
import app.arash.androidcore.data.entity.DrugAlarmDetail;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.entity.ReminderDetail;
import app.arash.androidcore.data.impl.DrugAlarmDaoImpl;
import app.arash.androidcore.data.impl.DrugAlarmDetailDaoImpl;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.adapter.CustomSpinnerAdapter;
import app.arash.androidcore.ui.adapter.ReminderDetailAdapter;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Arash on 1/24/18.
 */

public class AddDrugReminderDialogFragment extends DialogFragment {

  @BindView(R.id.spinner)
  Spinner spinner;
  @BindView(R.id.usage_recycler_view)
  RecyclerView usageRecyclerView;
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
  @BindView(R.id.root)
  LinearLayout root;


  private AppCompatActivity context;
  private String numberInEachTime;
  private ReminderDetailAdapter reminderDetailAdapter;
  private int hour;
  private Drug drug;
  private int minute;
  private boolean[] dayArray = {true, true, true, true, true, true, true};

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
          usageRecyclerView.setVisibility(View.VISIBLE);
          List<ReminderDetail> reminderDetails = new ArrayList<>();
          for (int j = 0; j < i + 1; j++) {
            reminderDetails.add(new ReminderDetail());
          }
          reminderDetailAdapter.updateAll(reminderDetails);
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
    reminderDetailAdapter = new ReminderDetailAdapter(getActivity(),
        new ArrayList<>(), this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    usageRecyclerView.setLayoutManager(linearLayoutManager);
    usageRecyclerView.setAdapter(reminderDetailAdapter);
//    timeTv.setText(NumberUtil.digitsToPersian("8:00"));

    if (drug != null) {
      drugTv.setText(drug.getNameFa().trim());
    }
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  private String[] getNumberOfUse() {
    String[] item = new String[12];
    item[0] = "۱ بار در روز";
    item[1] = "۲ بار در روز";
    item[2] = "۳ بار در روز";
    item[3] = "۴ بار در روز";
    item[4] = "۵ بار در روز";
    item[5] = "۶ بار در روز";
    item[6] = "۷ بار در روز";
    item[7] = "۸ بار در روز";
    item[8] = "۹ بار در روز";
    item[9] = "۱۰ بار در روز";
    item[10] = "۱۱ بار در روز";
    item[11] = "۱۲ بار در روز";
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

  public void showNumberDialog(int pos, String numberInDay) {
    Builder dialogBuilder = new Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_number_of_usage, null);
    dialogBuilder.setView(dialogView);

    RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
    AlertDialog alertDialog = dialogBuilder.create();

    RadioButton selectedItem = radioGroup.findViewWithTag(numberInDay);
    selectedItem.setChecked(true);
    alertDialog.show();
    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      RadioButton selectedRadio = (RadioButton) dialogView.findViewById(checkedId);
      numberInEachTime = (String) selectedRadio.getTag();
      reminderDetailAdapter.updateNumber(pos, numberInEachTime);
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
      //Clear all days
      for (int i = 0; i < dayArray.length; i++) {
        dayArray[i] = false;
      }

      if (saturdayChb.isChecked()) {
        days += getString(R.string.saturday_comma);
        dayArray[0] = true;
      }
      if (sundayChb.isChecked()) {
        days += getString(R.string.sunday_comma);
        dayArray[1] = true;
      }
      if (mondayChb.isChecked()) {
        days += getString(R.string.monday_comma);
        dayArray[2] = true;
      }
      if (tuesdayChb.isChecked()) {
        days += getString(R.string.tuesday_comma);
        dayArray[3] = true;
      }
      if (wednesdayChb.isChecked()) {
        days += getString(R.string.wednesday_comma);
        dayArray[4] = true;
      }
      if (thursdayChb.isChecked()) {
        days += getString(R.string.thursday_comma);
        dayArray[5] = true;
      }
      if (fridayChb.isChecked()) {
        days += getString(R.string.friday_comma);
        dayArray[6] = true;
      }
      if (!days.isEmpty()) {
        days = days.substring(0, days.length() - 1);
      }
      daysTv.setText(days);
      alertDialog.dismiss();
    });
  }

  public void showTimePicker(int position, String time) {
    String[] timeDetail = time.split(":");
// Get Current Time
    TimePickerDialog dialog = new TimePickerDialog(context, (timePicker, i, i1) -> {
      reminderDetailAdapter
          .updateTime(position,
              NumberUtil.digitsToPersian(String.format(Locale.US, "%d:%02d", i, i1)));
      hour = i;
      minute = i1;
    }, Integer.parseInt(timeDetail[0]), Integer.parseInt(timeDetail[1]), true);
    dialog.show();
  }

  @OnClick({R.id.done_img, R.id.close_img, R.id.every_day_radio, R.id.specific_day_radio,
      R.id.drug_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        if (validate()) {
          saveReminder();
//          getDialog().dismiss();
        }

        break;
      case R.id.drug_tv:
        showSearchDialog();
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
      case R.id.every_day_radio:
        everyDayRadio.setChecked(true);
        specificDayRadio.setChecked(false);
        daysTv.setVisibility(View.GONE);
        for (int i = 0; i < dayArray.length; i++) {
          dayArray[i] = true;
        }
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

  private boolean validate() {
    if (drug == null) {
      ToastUtil.toastError(root, getString(R.string.error_no_drug_selected));
      return false;
    }
    if (spinner.getSelectedItem().equals(getString(R.string.number_of_use_in_day))) {
      ToastUtil.toastError(root, "تعداد دفعات را انتخاب کنید");
      return false;
    }

    if (!everyDayRadio.isChecked() && daysTv.getText().toString().isEmpty()) {
      ToastUtil.toastError(root, "هیچ روزی انتخاب نشده است");
      return false;
    }

    if (instructionSpinner.getSelectedItem().equals(getString(R.string.usage_instruction))) {
      ToastUtil.toastError(root, "دستورالعمل مصرف انتخاب نشده است");
      return false;
    }

    return true;
  }

  private void saveReminder() {

    DrugAlarm alarm = new DrugAlarm();
    alarm.setDrugId(drug.getId());

    alarm.setTimesInDay(spinner.getSelectedItemPosition() + 1);

    String days;
    if (everyDayRadio.isChecked()) {
      days = "هر روز";
    } else {
      days = daysTv.getText().toString();
    }

    alarm.setDays(days);
    alarm.setLastServed("");
    alarm.setInstruction(instructionSpinner.getSelectedItem().toString());

    DrugAlarmDaoImpl drugAlarmDao = new DrugAlarmDaoImpl(context);
    Long alarmId = drugAlarmDao.create(alarm);

    //Set Details
    DrugAlarmDetailDaoImpl detailDao = new DrugAlarmDetailDaoImpl(context);

    List<ReminderDetail> details = reminderDetailAdapter.getReminderDetails();
    if (everyDayRadio.isChecked()) {
      for (int i = 0; i < 7; i++) {
        for (int j = 0; j < details.size(); j++) {
          DrugAlarmDetail alarmDetail = new DrugAlarmDetail();
          alarmDetail.setAlarmId(alarmId);
          alarmDetail.setDay(i);
          alarmDetail.setTime(details.get(j).getTime());
          alarmDetail.setNumber(details.get(j).getNumberInDay());
          detailDao.create(alarmDetail);
        }
      }
    } else {
      for (int i = 0; i < 7; i++) {
        if (dayArray[i]) {
          for (int j = 0; j < details.size(); j++) {
            DrugAlarmDetail alarmDetail = new DrugAlarmDetail();
            alarmDetail.setAlarmId(alarmId);
            alarmDetail.setDay(i);
            alarmDetail.setTime(details.get(j).getTime());
            alarmDetail.setNumber(details.get(j).getNumberInDay());
            detailDao.create(alarmDetail);
          }
        }
      }
    }
    DrugDaoImpl drugDao = new DrugDaoImpl(context);
    drug.setHasAlarmSet(true);
    drugDao.update(drug);
    ToastUtil.toastMessage(context, "یادآور با موفقیت ثبت شد");
    dismiss();
    EventBus.getDefault().post(new RefreshEvent(drug));
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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
