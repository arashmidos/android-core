package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.data.impl.MeasureDaoImpl;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.SunDate;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.alirezaafkar.sundatepicker.DatePicker;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Arash on 1/24/18.
 */

public class NewDrugDialogFragment extends DialogFragment {

  @BindView(R.id.root)
  LinearLayout root;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.measure_unit)
  TextView measureUnit;
  @BindView(R.id.date_value_tv)
  TextView dateValueTv;
  @BindView(R.id.date_layout)
  LinearLayout dateLayout;
  @BindView(R.id.time_value_tv)
  TextView timeValueTv;
  @BindView(R.id.time_layout)
  LinearLayout timeLayout;
  @BindView(R.id.measure_label)
  TextView measureLabel;
  @BindView(R.id.measure_value)
  EditText measureValue;

  private Unbinder unbinder;
  private AppCompatActivity context;
  private MeasureDetailType type;
  private SunDate datePicked;
  private Calendar cal;
  private int hour = -1;
  private int minute = -1;

  public static NewDrugDialogFragment newInstance(AppCompatActivity context,
      MeasureDetailType type) {
    NewDrugDialogFragment fragment = new NewDrugDialogFragment();
    fragment.context = context;
    fragment.type = type;
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
    View view = inflater.inflate(R.layout.fragment_dialog_new_measure, container, false);
    unbinder = ButterKnife.bind(this, view);

    setData();
    return view;
  }

  private void setData() {
    title.setText(String.format("ثبت %s", getString(type.getType())));
    measureLabel.setText(String.format("%s جدید", getString(type.getType())));
    measureUnit.setText(getString(type.getUnit()));

    if (hour == -1 || minute == -1) {
      final Calendar c = Calendar.getInstance();
      hour = c.get(Calendar.HOUR_OF_DAY);
      minute = c.get(Calendar.MINUTE);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(measureValue.getText().toString().trim())) {
      ToastUtil.toastError(root,
          String.format("مقدار %s نمی تواند خالی باشد", getString(type.getType())));
      return false;
    }
    if (cal == null) {
      ToastUtil.toastError(root, getString(R.string.choose_date_is_required));
      return false;
    }
    try {
      int value = Integer.valueOf(measureValue.getText().toString().trim());
      return true;
    } catch (NumberFormatException ignore) {
      ToastUtil.toastError(root, getString(R.string.error_input_value_not_correct));
      return false;
    }
  }

  @OnClick({R.id.done_img, R.id.close_img, R.id.date_layout, R.id.time_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        if (isValid()) {
          cal.set(Calendar.HOUR_OF_DAY, hour);
          cal.set(Calendar.MINUTE, minute);
          Measure measure = new Measure(type.getId(),
              Integer.valueOf(measureValue.getText().toString().trim()), cal.getTime());
          MeasureDaoImpl dao = new MeasureDaoImpl(context);
          Long id = dao.create(measure);
          if (id != null) {
            ToastUtil.toastMessage(context, getString(R.string.data_inserted_successfully));
            ((OnNewMeasureAdded) context).newMeasureAdded(measure);
            dismiss();
          }
        }
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
      case R.id.date_layout:
        showDatePicker();
        break;
      case R.id.time_layout:
        showTimePicker();
        break;
    }
  }

  private void showDatePicker() {
    DatePicker.Builder builder = new DatePicker.Builder().id(1);
    builder.future(false);
    if (cal != null) {
      builder.date(cal);
    }
    builder.build((id, calendar, day, month, year) -> {
      datePicked = new SunDate(day, month, year);
      cal = calendar;
      dateValueTv.setText(NumberUtil
          .digitsToPersian(String.format("%s %s %s", day, DateUtil.monthNames[month - 1], year)));
      dateValueTv.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
    }).show(context.getSupportFragmentManager(), "");
  }

  private void showTimePicker() {
// Get Current Time
    TimePickerDialog dialog = new TimePickerDialog(context, (timePicker, i, i1) -> {
      timeValueTv.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%s:%s", i, i1)));
      timeValueTv.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
      hour = i;
      minute = i1;
    }, hour, minute, false);
    dialog.show();
  }

  public interface OnNewMeasureAdded {

    void newMeasureAdded(Measure measure);
  }
}
