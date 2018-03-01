package app.arash.androidcore.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.ui.adapter.VideoListAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends Adapter<ViewHolder> {

  private AppCompatActivity context;
  private List<Category> categories = new ArrayList<>();
  private LayoutInflater layoutInflater;

  public VideoListAdapter(AppCompatActivity context, List<Category> categories) {
    this.context = context;
    this.categories = categories;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_video_category, parent, false);
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

  public void updateList(List<Category> myDrugs) {
    this.categories = myDrugs;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.category_name_tv)
    TextView categoryName;

    private Category category;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.category = categories.get(position);
      if (!TextUtils.isEmpty(category.getName())) {
        categoryName.setText(category.getName().trim());
      }
    }

    @OnClick({R.id.root_layout, R.id.category_name_tv})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.category_name_tv:

        case R.id.root_layout:
//          Intent intent = new Intent(context, DrugDetailActivity.class);
//          intent.putExtra(Constant.DRUG_OBJ, drug);
//          context.startActivity(intent);
          Toast.makeText(context, "VideoCategory" + category.getName(), Toast.LENGTH_SHORT).show();
          break;
      }
    }
  }
}
