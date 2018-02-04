package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.ui.activity.DoctorDetailActivity;
import app.arash.androidcore.ui.adapter.DoctorVisitAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.bottomsheet.DoctorBottomSheet;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shkbhbb on 2/4/18.
 */

public class DoctorVisitAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<DoctorVisit> doctorVisits;
  private LayoutInflater inflater;
  private Doctor doctor;

  public DoctorVisitAdapter(Context context,
      List<DoctorVisit> doctorVisits, Doctor doctor) {
    this.context = context;
    this.doctorVisits = doctorVisits;
    this.inflater = LayoutInflater.from(context);
    this.doctor = doctor;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_visit_doctor, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return doctorVisits.size();
  }

  public void update(List<DoctorVisit> doctorVisits) {
    this.doctorVisits = doctorVisits;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.day_tv)
    TextView dayTv;
    @BindView(R.id.date_time_tv)
    TextView dateTimeTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;

    private DoctorVisit doctorVisit;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      doctorVisit = doctorVisits.get(position);
      Date date = DateUtil.convertStringToDate(doctorVisit.getVisitDate(),
          DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");

      dateTimeTv.setText(NumberUtil.digitsToPersian(String
          .format("%s %s", DateUtil.getPersianVisitDate(date), doctorVisit.getVisitTime())));

      if (!TextUtils.isEmpty(doctorVisit.getDescription())) {
        descriptionTv.setText(doctorVisit.getDescription());
        descriptionTv.setVisibility(View.VISIBLE);
      } else {
        descriptionTv.setVisibility(View.GONE);
      }
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      dayTv.setText(DateUtil.getPersianDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    @OnClick(R.id.more_img)
    public void onViewClicked() {
      DoctorBottomSheet doctorBottomSheet = DoctorBottomSheet
          .newInstance(doctor, doctorVisit, true);
      doctorBottomSheet
          .show(((DoctorDetailActivity) context).getSupportFragmentManager(), "visit bottom sheet");
    }
  }
}
