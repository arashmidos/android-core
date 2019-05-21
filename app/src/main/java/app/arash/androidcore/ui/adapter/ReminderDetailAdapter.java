package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.ReminderDetail;
import app.arash.androidcore.ui.adapter.ReminderDetailAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.dialog.AddDrugReminderDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;

/**
 * Created by shkbhbb on 2/15/18.
 */

public class ReminderDetailAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<ReminderDetail> reminderDetails;
  private AddDrugReminderDialogFragment addDrugReminderDialogFragment;
  private LayoutInflater inflater;
  public ReminderDetailAdapter(Context context,
      List<ReminderDetail> reminderDetails,
      AddDrugReminderDialogFragment addDrugReminderDialogFragment) {
    this.context = context;
    this.reminderDetails = reminderDetails;
    this.addDrugReminderDialogFragment = addDrugReminderDialogFragment;
    this.inflater = LayoutInflater.from(context);
  }

  public List<ReminderDetail> getReminderDetails() {
    return reminderDetails;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_usage_reminder, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return reminderDetails.size();
  }

  public void updateTime(int pos, String time) {
    reminderDetails.get(pos).setTime(time);
    notifyItemChanged(pos);
  }

  public void updateNumber(int pos, String number) {
    reminderDetails.get(pos).setNumberInDay(number);
    notifyItemChanged(pos);
  }

  public void updateAll(List<ReminderDetail> reminderDetails) {
    this.reminderDetails = reminderDetails;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.number_tv)
    TextView numberTv;

    private ReminderDetail reminderDetail;
    private int pos;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int pos) {
      this.pos = pos;
      this.reminderDetail = reminderDetails.get(pos);
      timeTv.setText(reminderDetail.getTime());
      numberTv.setText(reminderDetail.getNumberInDay());
    }

    @OnClick({R.id.time_tv, R.id.number_tv})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.time_tv:
          addDrugReminderDialogFragment.showTimePicker(pos, reminderDetail.getTime());
          break;
        case R.id.number_tv:
          addDrugReminderDialogFragment.showNumberDialog(pos, reminderDetail.getNumberInDay());
          break;
      }
    }
  }
}
