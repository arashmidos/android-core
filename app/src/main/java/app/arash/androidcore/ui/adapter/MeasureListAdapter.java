package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.ui.adapter.MeasureListAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Arash on 1/23/18.
 */

public class MeasureListAdapter extends Adapter<ViewHolder> {

  private Context context;
  private MeasureDetailType[] categories;
  private LayoutInflater layoutInflater;

  public MeasureListAdapter(Context context, MeasureDetailType[] categories) {
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
    return categories.length;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.measure_name_tv)
    TextView measureNameTv;

    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.position = position;
      measureNameTv.setText(categories[position].getType());
    }

    @OnClick(R.id.measure_name_tv)
    public void onViewClicked() {
      Toast.makeText(context, "boodi hala", Toast.LENGTH_SHORT).show();
    }
  }
}
