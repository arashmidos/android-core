package app.arash.androidcore.ui.adapter;

import static app.arash.androidcore.data.entity.Constant.DRUG_CATEGORY;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.ui.activity.DrugCategoryActivity;
import app.arash.androidcore.ui.adapter.DrugCategoryAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class DrugCategoryAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<String> categories = new ArrayList<>();
  private LayoutInflater layoutInflater;

  public DrugCategoryAdapter(Context context, List<String> categories) {
    this.context = context;
    this.categories = categories;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_drug_category, parent, false);
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

    @BindView(R.id.category_name_tv)
    TextView categoryNameTv;

    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.position = position;
      if (!TextUtils.isEmpty(categories.get(position))) {
        categoryNameTv.setText(categories.get(position));
      }
    }

    @OnClick(R.id.category_name_tv)
    public void onViewClicked() {
      Intent intent = new Intent(context, DrugCategoryActivity.class);
      intent.putExtra(DRUG_CATEGORY, categories.get(position));
      context.startActivity(intent);
    }
  }
}
