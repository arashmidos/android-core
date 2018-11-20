package app.arash.androidcore.ui.activity;

import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.data.impl.DoctorVisitDaoImpl;
import app.arash.androidcore.receiver.BootReceiver;
import app.arash.androidcore.ui.fragment.dialog.DoctorSearchDialogFragment;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alirezaafkar.sundatepicker.DatePicker.Builder;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.TrackHelper;

public class NewVisitActivity extends AppCompatActivity {

  @BindView(R.id.doctor_tv)
  TextView doctorTv;
  @BindView(R.id.date_value_tv)
  TextView dateValueTv;
  @BindView(R.id.time_value_tv)
  TextView timeValueTv;
  @BindView(R.id.description_edt)
  EditText descriptionEdt;
  @BindView(R.id.root)
  LinearLayout root;

  private int hour = -1;
  private Calendar cal;
  private int minute = -1;
  private DoctorVisitDaoImpl doctorVisitDao;
  private Doctor doctor = null;
  private DoctorVisit doctorVisit = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_visit);
    ButterKnife.bind(this);
    doctorVisitDao = new DoctorVisitDaoImpl(this);
    getIntentData();
    setData();
    Tracker tracker = MedicApplication.getInstance().getTracker();

    TrackHelper.track().screen("/activity/visit").title("Visit").with(tracker);

  }

  private void setData() {
    if (hour == -1 || minute == -1) {
      final Calendar c = Calendar.getInstance();
      hour = c.get(Calendar.HOUR_OF_DAY);
      minute = c.get(Calendar.MINUTE);
    }
  }

  private void getIntentData() {
    if (getIntent() != null && getIntent().getSerializableExtra(Constant.VISIT_OBJ) != null
        && getIntent().getSerializableExtra(Constant.DOCTOR_OBJ) != null) {
      this.doctor = (Doctor) getIntent().getSerializableExtra(Constant.DOCTOR_OBJ);
      if (!TextUtils.isEmpty(doctor.getName())) {
        doctorTv.setText(doctor.getName());
      }
      doctorVisit = (DoctorVisit) getIntent().getSerializableExtra(Constant.VISIT_OBJ);
      Date date = DateUtil.convertStringToDate(doctorVisit.getVisitDate(),
          DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      this.cal = calendar;
      String[] timeDetail = doctorVisit.getVisitTime().split(":");
      hour = Integer.parseInt(timeDetail[0]);
      minute = Integer.parseInt(timeDetail[1]);
      timeValueTv.setText(NumberUtil.digitsToPersian(doctorVisit.getVisitTime()));
      dateValueTv.setText(NumberUtil.digitsToPersian(DateUtil.getPersianVisitDate(date)));
      if (!TextUtils.isEmpty(doctorVisit.getDescription())) {
        descriptionEdt.setText(doctorVisit.getDescription());
      }
    } else if (getIntent() != null
        && getIntent().getSerializableExtra(Constant.DOCTOR_OBJ) != null) {
      this.doctor = (Doctor) getIntent().getSerializableExtra(Constant.DOCTOR_OBJ);
      if (!TextUtils.isEmpty(doctor.getName())) {
        doctorTv.setText(doctor.getName());
      }
    }
  }

  @OnClick({R.id.done_img, R.id.close_img, R.id.doctor_tv, R.id.date_layout, R.id.time_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        if (isValid()) {
          String time = timeValueTv.getText().toString().trim();
//          String date = dateValueTv.getText().toString().trim();
          String description = descriptionEdt.getText().toString().trim();
          cal.set(Calendar.HOUR_OF_DAY, hour);
          cal.set(Calendar.MINUTE, minute);
          long doctorId = doctor.getId();
          if (doctorVisit == null) {
            doctorVisitDao.create(new DoctorVisit(cal.getTime(), NumberUtil.digitsToEnglish(time),
                description, doctorId));
          } else {
            doctorVisitDao.update(new DoctorVisit(doctorVisit.getId(),
                cal.getTime(), time, description, doctorId));
          }
          new BootReceiver().setAlarm(this);
          finish();
        }
        break;
      case R.id.close_img:
        finish();
        break;
      case R.id.doctor_tv:
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DoctorSearchDialogFragment doctorSearchDialogFragment = DoctorSearchDialogFragment
            .newInstance(this);
        doctorSearchDialogFragment.show(ft, "search doctor");
        break;
      case R.id.date_layout:
        showDatePicker();
        break;
      case R.id.time_layout:
        showTimePicker();
        break;
    }
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(doctorTv.getText().toString()) || doctorTv.getText().toString()
        .equals(getString(R.string.choose_doctor)) || doctor == null) {
      ToastUtil.toastError(root, getString(R.string.choose_doctor_is_required));
      return false;
    } else if (cal == null) {
      ToastUtil.toastError(root, getString(R.string.choose_date_is_required));
      return false;
    } else if (TextUtils.isEmpty(timeValueTv.getText().toString())) {
      ToastUtil.toastError(root, getString(R.string.choose_time_is_required));
      return false;
    }
    return true;
  }

  private void showDatePicker() {
    Builder builder = new Builder().id(1);
    if (cal != null) {
      builder.date(cal);
    }
    builder.build((id, calendar, day, month, year) -> {
      cal = calendar;
      dateValueTv.setText(NumberUtil
          .digitsToPersian(String.format("%s %s %s", day, DateUtil.monthNames[month - 1], year)));
    }).show(getSupportFragmentManager(), "");
  }

  private void showTimePicker() {
// Get Current Time
    TimePickerDialog dialog = new TimePickerDialog(this, (timePicker, i, i1) -> {
      timeValueTv.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%s:%s", i, i1)));
      hour = i;
      minute = i1;
    }, hour, minute, true);
    dialog.show();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  public void setSelectedDoctor(Doctor doctor) {
    this.doctor = doctor;
    if (!TextUtils.isEmpty(doctor.getName())) {
      doctorTv.setText(doctor.getName());
    }
  }
}
