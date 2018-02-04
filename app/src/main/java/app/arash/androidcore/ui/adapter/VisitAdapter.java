package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.ui.adapter.VisitAdapter.ViewHolder;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.SunDate;
import app.arash.androidcore.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class VisitAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<DoctorVisit> visits;
  private DoctorDaoImpl doctorDao;
  private LayoutInflater layoutInflater;

  public VisitAdapter(Context context, List<DoctorVisit> visits) {
    this.context = context;
    this.visits = visits;
    this.doctorDao = new DoctorDaoImpl(context);
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_visit, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return visits.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.visit_time_tv)
    TextView visitTimeTv;
    @BindView(R.id.left_time_tv)
    TextView leftTimeTv;
    @BindView(R.id.doctor_name_tv)
    TextView doctorNameTv;
    @BindView(R.id.main_lay)
    CardView mainLay;
    @BindView(R.id.expertise_tv)
    TextView expertiseTv;

    private DoctorVisit visit;
    private Doctor doctor;
    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.visit = visits.get(position);
      this.position = position;
      this.doctor = doctorDao.retrieve(visit.getDoctorId());
      setMargin();
      if (!TextUtils.isEmpty(doctor.getName())) {
        doctorNameTv.setText(doctor.getName());
      }
      if (!TextUtils.isEmpty(doctor.getExpertise())) {
        expertiseTv.setText(doctor.getExpertise());
      }
      String[] dateDetail = visit.getVisitDate().split(" ");
      SunDate sunDate = new SunDate(Integer.parseInt(dateDetail[0]),
          DateUtil.getPersionMonthNum(dateDetail[1]),
          Integer.parseInt(dateDetail[2]));
      Calendar calendar = sunDate.getCalendar();
      visitTimeTv.setText(String
          .format("%s %s , %s", DateUtil.getPersianDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)),
              visit.getVisitDate().substring(0, visit.getVisitDate().length() - 4),
              visit.getVisitTime()));
      leftTimeTv.setText(NumberUtil.digitsToPersian(
          String.format("%d روز دیگر",
              DateUtil.getDifferenceDateDayCount(new Date(), calendar.getTime()))));
    }

    private void setMargin() {
      if (position == visits.size() - 1) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.dpToPx(context, 8), Utils.dpToPx(context, 8),
            Utils.dpToPx(context, 8), Utils.dpToPx(context, 8));
        mainLay.setLayoutParams(params);
      } else {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.dpToPx(context, 8), Utils.dpToPx(context, 8),
            Utils.dpToPx(context, 8), Utils.dpToPx(context, 0));
        mainLay.setLayoutParams(params);
      }
    }
  }
}
