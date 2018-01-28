package app.arash.androidcore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DoctorListAdapter.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;

/**
 * Created by Arash
 */

public class DoctorListAdapter extends Adapter<ViewHolder> {

  private final DrugDaoImpl drugDaoImpl;
  private MainActivity context;
  private List<Doctor> doctorList;
  private LayoutInflater inflater;

  public DoctorListAdapter(MainActivity context, List<Doctor> doctorList) {
    this.context = context;
    this.doctorList = doctorList;
    this.inflater = LayoutInflater.from(context);
    this.drugDaoImpl = new DrugDaoImpl(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_doctor_list, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return doctorList.size();
  }

  public void update(List<Doctor> doctorList) {
    this.doctorList = doctorList;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.more_img)
    ImageView moreImg;
    @BindView(R.id.doctor_name_tv)
    TextView doctorNameTv;
    private Doctor doctor;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.doctor = doctorList.get(position);
      doctorNameTv.setText(doctor.getName());
    }

    @OnClick({R.id.more_img})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.more_img:
          //TODO: Doctor bottom sheet
      }
    }
  }
}
