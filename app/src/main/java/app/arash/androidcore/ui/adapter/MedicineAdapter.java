package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Medicine;
import app.arash.androidcore.ui.adapter.MedicineAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import java.util.Locale;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class MedicineAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Medicine> medicines;
  private LayoutInflater inflater;

  public MedicineAdapter(Context context,
      List<Medicine> medicines) {
    this.context = context;
    this.medicines = medicines;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_medicine, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return medicines.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.time_status_img)
    ImageView timeStatusImg;
    @BindView(R.id.bottom_line)
    View bottomLine;
    @BindView(R.id.top_line)
    View topLine;
    @BindView(R.id.first_view)
    View firstView;
    @BindView(R.id.last_view)
    View lastView;
    @BindView(R.id.medicine_time_tv)
    TextView medicineTimeTv;
    @BindView(R.id.medicine_number_tv)
    TextView medicineNumberTv;
    @BindView(R.id.medicine_name_tv)
    TextView medicineNameTv;
    @BindView(R.id.main_lay)
    LinearLayout mainLay;

    private Medicine medicine;
    private int position;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.medicine = medicines.get(position);
      this.position = position;
      setItemsBackground();
      setLinesVisibility();
      setTimeStatusImg();
      setText();
    }

    private void setText() {
      if (!TextUtils.isEmpty(medicine.getTime())) {
        medicineTimeTv.setText(medicine.getTime());
      }
      if (!TextUtils.isEmpty(medicine.getName())) {
        medicineNameTv.setText(medicine.getName());
      }
      medicineNumberTv.setText(String.format(Locale.US, "%s عدد", medicine.getNumber()));
    }

    private void setTimeStatusImg() {
      switch (medicine.getStatus()) {
        case 0:
          timeStatusImg.setImageResource(R.drawable.ic_sun_24_dp);
          break;
        case 1:
          timeStatusImg.setImageResource(R.drawable.ic_sunset_24_dp);
          break;
        case 2:
          timeStatusImg.setImageResource(R.drawable.ic_moon_24_dp);
          break;
      }
    }

    private void setItemsBackground() {
      if (position % 2 != 0) {
        mainLay.setBackgroundColor(ContextCompat.getColor(context, R.color.white_fa));
      } else {
        mainLay.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
      }
    }

    private void setBottomTopViewBackground(View view) {
      if (position % 2 != 0) {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.white_fa));
      } else {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
      }
    }

    private void setLinesVisibility() {
      if (medicines.size() == 1) {
        bottomLine.setVisibility(View.GONE);
        topLine.setVisibility(View.GONE);
        firstView.setVisibility(View.VISIBLE);
        lastView.setVisibility(View.VISIBLE);
        setBottomTopViewBackground(firstView);
        setBottomTopViewBackground(lastView);
      } else if (position == 0) {
        bottomLine.setVisibility(View.VISIBLE);
        topLine.setVisibility(View.GONE);
        firstView.setVisibility(View.VISIBLE);
        lastView.setVisibility(View.GONE);
        setBottomTopViewBackground(firstView);
      } else if (position == medicines.size() - 1) {
        bottomLine.setVisibility(View.GONE);
        topLine.setVisibility(View.VISIBLE);
        firstView.setVisibility(View.GONE);
        lastView.setVisibility(View.VISIBLE);
        setBottomTopViewBackground(lastView);
      } else {
        firstView.setVisibility(View.GONE);
        lastView.setVisibility(View.GONE);
        bottomLine.setVisibility(View.VISIBLE);
        topLine.setVisibility(View.VISIBLE);
      }
    }

    @OnClick(R.id.main_lay)
    public void onViewClicked() {
    }
  }
}
