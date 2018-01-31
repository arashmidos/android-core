package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.ui.adapter.DrugSpecificationAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.DrugSpecificationFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class DrugSpecificationAdapter extends
    Adapter<ViewHolder> {

  private Context context;
  private List<String> specifications;
  private LayoutInflater inflater;
  private DrugSpecificationFragment drugSpecificationFragment;

  public DrugSpecificationAdapter(Context context, List<String> specifications,
      DrugSpecificationFragment drugSpecificationFragment) {
    this.context = context;
    this.specifications = specifications;
    this.inflater = LayoutInflater.from(context);
    this.drugSpecificationFragment = drugSpecificationFragment;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if (viewType == Constant.NAME) {
      view = inflater.inflate(R.layout.item_drug_specification_name, parent, false);
    } else if (viewType == Constant.TITLE) {
      view = inflater.inflate(R.layout.item_drug_specification_title, parent, false);
    } else {
      view = inflater.inflate(R.layout.item_drug_specification_detail, parent, false);
    }
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return Constant.NAME;
    } else if (position < specifications.size() / 2) {
      return Constant.TITLE;
    } else {
      return Constant.DETAIL;
    }
  }

  @Override
  public int getItemCount() {
    return specifications.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    @Nullable
    @BindView(R.id.name_tv)
    TextView nameTv;
    @Nullable
    @BindView(R.id.detail_title_tv)
    TextView detailTitleTv;
    @Nullable
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @Nullable
    @BindView(R.id.title_tv)
    TextView titleTv;

    private String item;
    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      if (nameTv != null) {
        nameTv.setOnClickListener(this);
      } else if (titleTv != null) {
        titleTv.setOnClickListener(this);
      }
    }

    public void setData(int position) {
      item = specifications.get(position);
      this.position = position;
      if (!TextUtils.isEmpty(item)) {
        if (nameTv != null) {
          nameTv.setText(item);
        } else if (titleTv != null) {
          titleTv.setText(item);
        } else if (detailTitleTv != null) {
          if (position == ((specifications.size() / 2))) {
            detailTitleTv.setText("نام");
          } else {
            detailTitleTv.setText(specifications.get(position - (specifications.size() / 2)));
          }
          if (descriptionTv != null) {
            descriptionTv.setText(item);
          }
        }
      }
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.title_tv:
        case R.id.name_tv:
          drugSpecificationFragment.scrollToPosition(position + (specifications.size() / 2));
          break;
      }
    }
  }
}
