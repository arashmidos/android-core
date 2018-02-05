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
import app.arash.androidcore.ui.adapter.MeasureListAdapter2.ViewHolder;
import app.arash.androidcore.ui.fragment.dialog.NewMeasureDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;

/**
 * Created by Arash on 1/23/18.
 */

public class MeasureListAdapter2 extends Adapter<ViewHolder> {

  private MainActivity context;
  private ArrayList<MeasureDetailType> categories;
  private LayoutInflater layoutInflater;

  public MeasureListAdapter2(MainActivity context, ArrayList<MeasureDetailType> categories) {
    this.context = context;
    this.categories = categories;
    this.layoutInflater = LayoutInflater.from(context);
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
    return categories.size();
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
      this.measure = categories.get(position);
      measureNameTv.setText(measure.getType());
    }

    @OnClick({R.id.measure_name_tv, R.id.item_layout})
    public void onViewClicked() {
      android.app.FragmentTransaction ft2 = context.getFragmentManager().beginTransaction();
      NewMeasureDialogFragment dialogFragment = NewMeasureDialogFragment
          .newInstance(context, measure);
      dialogFragment.show(ft2, "new measure");
    }
  }
}
