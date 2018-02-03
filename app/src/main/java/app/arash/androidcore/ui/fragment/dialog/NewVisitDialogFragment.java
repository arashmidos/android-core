package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.SunDate;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.alirezaafkar.sundatepicker.DatePicker.Builder;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Arash on 1/24/18.
 */

public class NewVisitDialogFragment extends DialogFragment {


  @BindView(R.id.doctor_tv)
  TextView doctorTv;
  @BindView(R.id.date_value_tv)
  TextView dateValueTv;
  @BindView(R.id.time_value_tv)
  TextView timeValueTv;
  @BindView(R.id.description_edt)
  EditText descriptionEdt;

  private Unbinder unbinder;
  private AppCompatActivity context;
  private SunDate datePicked;
  private Calendar cal;
  private int hour = -1;
  private int minute = -1;

  public static NewVisitDialogFragment newInstance(AppCompatActivity context) {
    NewVisitDialogFragment fragment = new NewVisitDialogFragment();
    fragment.context = context;
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
    View view = inflater.inflate(R.layout.fragment_dialog_new_visit, container, false);
    unbinder = ButterKnife.bind(this, view);
    setData();
    return view;
  }

  private void setData() {
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

  @OnClick({R.id.done_img, R.id.close_img, R.id.date_layout, R.id.time_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        getDialog().dismiss();
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
    Builder builder = new Builder().id(1);
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

}
