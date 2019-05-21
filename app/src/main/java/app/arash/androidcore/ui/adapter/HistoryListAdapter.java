package app.arash.androidcore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.ui.activity.ChartDetailActivity;
import app.arash.androidcore.ui.adapter.HistoryListAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.dialog.MeasureHistoryListDialogFragment;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Date;
import java.util.List;

/**
 * Created by Arash on 1/23/18.
 */

public class HistoryListAdapter extends Adapter<ViewHolder> {

  private final MeasureHistoryListDialogFragment parent;
  private final List<Measure> historyList;
  private ChartDetailActivity context;
  private LayoutInflater layoutInflater;

  public HistoryListAdapter(ChartDetailActivity context, List<Measure> historyList,
      MeasureHistoryListDialogFragment parent) {
    this.context = context;
    this.historyList = historyList;
    this.layoutInflater = LayoutInflater.from(context);
    this.parent = parent;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_history, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return historyList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.measure_value_tv)
    TextView measureValueTv;
    @BindView(R.id.measure_date_tv)
    TextView measureDateTv;

    private int position;
    private Measure measure;
    private MeasureDetailType type;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.position = position;
      this.measure = historyList.get(position);
      type = MeasureDetailType.getByTypeId(measure.getType());
      measureValueTv.setText(NumberUtil.digitsToPersian(String
          .format("%s %s", context.getString(type.getUnit()), String.valueOf(measure.getValue()))));
      Date date = DateUtil.convertStringToDate(measure.getTimestamp(),
          DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");
      measureDateTv.setText(NumberUtil.digitsToPersian(DateUtil.getPersianVisitDate(date)));
    }
  }
}
