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
import app.arash.androidcore.data.entity.Visit;
import app.arash.androidcore.ui.adapter.VisitAdapter.ViewHolder;
import app.arash.androidcore.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class VisitAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Visit> visits;
  private LayoutInflater layoutInflater;

  public VisitAdapter(Context context, List<Visit> visits) {
    this.context = context;
    this.visits = visits;
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

    private Visit visit;
    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.visit = visits.get(position);
      this.position = position;
      setMargin();
      if (!TextUtils.isEmpty(visit.getDoctorName())) {
        doctorNameTv.setText(visit.getDoctorName());
      }
      if (!TextUtils.isEmpty(visit.getFiled())) {
        expertiseTv.setText(visit.getFiled());
      }
      if (!TextUtils.isEmpty(visit.getLeftTime())) {
        leftTimeTv.setText(visit.getLeftTime());
      }
      if (!TextUtils.isEmpty(visit.getTime())) {
        visitTimeTv.setText(visit.getTime());
      }
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
