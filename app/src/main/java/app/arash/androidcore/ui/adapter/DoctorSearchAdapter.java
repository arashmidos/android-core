package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.ui.adapter.DoctorSearchAdapter.ViewHolder;
import app.arash.androidcore.ui.fragment.dialog.DoctorSearchDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class DoctorSearchAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Doctor> doctors;
  private LayoutInflater inflater;
  private DoctorSearchDialogFragment doctorSearchDialogFragment;

  public DoctorSearchAdapter(Context context, DoctorSearchDialogFragment doctorSearchDialogFragment,
      List<Doctor> doctors) {
    this.doctorSearchDialogFragment = doctorSearchDialogFragment;
    this.context = context;
    this.doctors = doctors;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_doctor_search, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return doctors.size();
  }

  public void update(List<Doctor> doctors) {
    this.doctors = doctors;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.doctor_tv)
    TextView doctorTv;

    private Doctor doctor;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.doctor = doctors.get(position);
      if (doctor != null && !TextUtils.isEmpty(doctor.getName())) {
        doctorTv.setText(doctor.getName().trim());
      }
    }

    @OnClick({R.id.search_layout})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.search_layout:
          doctorSearchDialogFragment.selectedItem(doctor);
          break;
      }
    }
  }
}
