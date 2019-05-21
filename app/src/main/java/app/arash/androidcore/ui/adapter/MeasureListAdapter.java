package app.arash.androidcore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.MeasureListAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.dialog.MeasureListDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Arash on 1/23/18.
 */

public class MeasureListAdapter extends Adapter<ViewHolder> {

  private final MeasureListDialogFragment parent;
  private MainActivity context;
  private MeasureDetailType[] categories;
  private LayoutInflater layoutInflater;

  public MeasureListAdapter(MainActivity context, MeasureDetailType[] categories,
      MeasureListDialogFragment parent) {
    this.context = context;
    this.categories = categories;
    this.layoutInflater = LayoutInflater.from(context);
    this.parent = parent;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_measure, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return categories.length;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.measure_name_tv)
    TextView measureNameTv;

    private int position;
    private MeasureDetailType measure;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.position = position;
      this.measure = categories[position];
      measureNameTv.setText(measure.getType());
    }

    @OnClick({R.id.measure_name_tv, R.id.item_layout})
    public void onViewClicked() {
      context.addNewMeasure(measure);
      parent.dismiss();
    }
  }
}
