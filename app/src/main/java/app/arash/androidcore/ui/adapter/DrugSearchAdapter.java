package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.DrugSearchAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class DrugSearchAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Drug> drugs;
  private LayoutInflater inflater;

  public DrugSearchAdapter(Context context,
      List<Drug> drugs) {
    this.context = context;
    this.drugs = drugs;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_drug_search, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return drugs.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recent_img)
    ImageView recentImg;
    @BindView(R.id.drug_name_tv)
    TextView drugNameTv;

    private Drug drug;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.drug = drugs.get(position);
      if (!TextUtils.isEmpty(drug.getNameFa())) {
        drugNameTv.setText(drug.getNameFa());
      }
    }
  }
}
