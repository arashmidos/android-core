package app.arash.androidcore.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DoctorSpecificationFragment extends Fragment {

  @BindView(R.id.name_tv)
  TextView nameTv;
  @BindView(R.id.expertise_tv)
  TextView expertiseTv;
  @BindView(R.id.call_img)
  ImageView callImg;
  @BindView(R.id.phone_tv)
  TextView phoneTv;
  @BindView(R.id.address_tv)
  TextView addressTv;
  @BindView(R.id.root)
  LinearLayout root;
  private Unbinder unbinder;
  private Doctor doctor;
  private LinearLayoutManager layoutManager;

  public DoctorSpecificationFragment() {
    // Required empty public constructor
  }

  public static DoctorSpecificationFragment newInstance(Doctor doctor) {
    DoctorSpecificationFragment fragment = new DoctorSpecificationFragment();
    fragment.doctor = doctor;
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_doctor_specification, container, false);
    unbinder = ButterKnife.bind(this, view);

    setData();
    return view;
  }

  private void setData() {
    nameTv.setText(doctor.getName());
    expertiseTv.setText(doctor.getExpertise());
    phoneTv.setText(doctor.getPhone());
    addressTv.setText(doctor.getAddress());
    if (doctor.getPhone() == null) {
      callImg.setVisibility(View.GONE);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.call_img)
  public void onViewClicked() {
    String uri = "tel:" + doctor.getPhone().trim();
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse(uri));
    startActivity(intent);
  }
}
