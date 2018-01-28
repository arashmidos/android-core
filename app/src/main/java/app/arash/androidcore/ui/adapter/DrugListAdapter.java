package app.arash.androidcore.ui.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.activity.DrugDetailActivity;
import app.arash.androidcore.ui.adapter.DrugListAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.bottomsheet.DrugBottomSheet;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class DrugListAdapter extends Adapter<ViewHolder> {

  private AppCompatActivity context;
  private List<Drug> drugs = new ArrayList<>();
  private LayoutInflater layoutInflater;

  public DrugListAdapter(AppCompatActivity context, List<Drug> drugs) {
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

  public void updateList(List<Drug> myDrugs) {
    this.drugs = myDrugs;
    notifyDataSetChanged();
  }

  public void update(List<Drug> allDrugs) {
    this.drugs = allDrugs;
    notifyDataSetChanged();
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
      if (!TextUtils.isEmpty(drug.getNameFa())) {
        drugNameTv.setText(drug.getNameFa().trim());
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

    @OnClick({R.id.more_items_img, R.id.star_img, R.id.alarm_img, R.id.drug_name_tv})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.more_items_img:
          DrugBottomSheet drugBottomSheet = DrugBottomSheet.newInstance(drug);
          drugBottomSheet.show(context.getSupportFragmentManager(), "drug bottom sheet");
          break;
        case R.id.drug_name_tv:
          Intent intent = new Intent(context, DrugDetailActivity.class);
          intent.putExtra(Constant.DRUG_OBJ, drug);
          context.startActivity(intent);
          break;
      }
    }
  }
}
