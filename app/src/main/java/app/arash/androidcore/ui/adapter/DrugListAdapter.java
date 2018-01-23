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
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.DrugListAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class DrugListAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Drug> drugs = new ArrayList<>();
  private LayoutInflater layoutInflater;

  public DrugListAdapter(Context context,
      List<Drug> drugs) {
    this.context = context;
    this.drugs = drugs;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_drug_list, parent, false);
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

    @BindView(R.id.drug_name_tv)
    TextView drugNameTv;
    @BindView(R.id.star_img)
    ImageView starImg;
    @BindView(R.id.alarm_img)
    ImageView alarmImg;

    private Drug drug;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.drug = drugs.get(position);
      if (!TextUtils.isEmpty(drug.getDrugName())) {
        drugNameTv.setText(drug.getDrugName());
      }
      if (drug.isHasAlarmSet()) {
        alarmImg.setVisibility(View.VISIBLE);
      } else {
        alarmImg.setVisibility(View.GONE);
      }
      if (drug.isStared()) {
        starImg.setVisibility(View.VISIBLE);
      } else {
        starImg.setVisibility(View.GONE);
      }
    }

    @OnClick({R.id.more_items_img, R.id.star_img, R.id.alarm_img})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.more_items_img:
          Toast.makeText(context, "more", Toast.LENGTH_SHORT).show();
          break;
        case R.id.star_img:
          Toast.makeText(context, "star", Toast.LENGTH_SHORT).show();
          break;
        case R.id.alarm_img:
          Toast.makeText(context, "alarm", Toast.LENGTH_SHORT).show();
          break;

      }
    }
  }
}
