package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.activity.DrugDetailActivity;
import app.arash.androidcore.ui.adapter.DrugSearchAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class DrugSearchAdapter extends Adapter<ViewHolder> {

  private final DrugDaoImpl drugDaoImpl;
  private Context context;
  private List<String> historyList;
  private LayoutInflater inflater;
  private boolean isHistory = true;

  public DrugSearchAdapter(Context context, List<String> historyList) {
    this.context = context;
    this.historyList = historyList;
    this.inflater = LayoutInflater.from(context);
    this.drugDaoImpl = new DrugDaoImpl(context);
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
    return historyList.size();
  }

  public void update(List<String> searchHistoryList, boolean isHistory) {
    this.historyList = searchHistoryList;
    this.isHistory = isHistory;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recent_img)
    ImageView recentImg;
    @BindView(R.id.drug_name_tv)
    TextView drugNameTv;

    private String constraint;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.constraint = historyList.get(position);
      if (!TextUtils.isEmpty(constraint)) {
        drugNameTv.setText(constraint);
      }
      if (!isHistory) {
        recentImg.setVisibility(View.GONE);
      } else {
        recentImg.setVisibility(View.VISIBLE);
      }
    }

    @OnClick({R.id.search_layout})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.search_layout:
          Drug drug = drugDaoImpl.retriveByName(drugNameTv.getText().toString().trim());
          if (drug != null) {
            Intent intent = new Intent(context, DrugDetailActivity.class);
            intent.putExtra(Constant.DRUG_OBJ, drug);
            context.startActivity(intent);
          }
          break;
      }
    }
  }
}
