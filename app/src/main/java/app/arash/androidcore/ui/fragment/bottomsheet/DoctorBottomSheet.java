package app.arash.androidcore.ui.fragment.bottomsheet;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorDeleteEvent;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.ui.fragment.dialog.NewDoctorDialogFragment;
import app.arash.androidcore.util.DialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;

public class DoctorBottomSheet extends BottomSheetDialogFragment {

  @BindView(R.id.edit_doctor_tv)
  TextView editDoctorTv;
  @BindView(R.id.delete_doctor_tv)
  TextView deleteDoctorTv;
  @BindView(R.id.edit_visit)
  TextView editVisit;
  @BindView(R.id.delete_visit)
  TextView deleteVisit;
  private AppCompatActivity activity;
  private Doctor doctor;
  private DoctorDaoImpl doctorDao;


  public static DoctorBottomSheet newInstance(Doctor doctor) {
    DoctorBottomSheet drugBottomSheet = new DoctorBottomSheet();
    drugBottomSheet.doctor = doctor;
    return drugBottomSheet;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.bottom_sheet_doctor, container, false);
    ButterKnife.bind(this, view);
    init();
    return view;
  }

  /**
   * init variables
   */

  private void init() {
    activity = (AppCompatActivity) getActivity();
    doctorDao = new DoctorDaoImpl(activity);
  }

  @OnClick({R.id.edit_doctor_tv, R.id.delete_doctor_tv, R.id.edit_visit, R.id.delete_visit})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.edit_doctor_tv:
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        NewDoctorDialogFragment newDoctorDialogFragment = NewDoctorDialogFragment
            .newInstance(activity, doctor);
        newDoctorDialogFragment.show(ft, "new doctor");
        break;
      case R.id.delete_doctor_tv:
        DialogUtil.showCustomDialog(activity, "", "آیا از حذف دکتر اطمینان دارید؟", "حذف",
            (dialogInterface, i) -> {
              doctorDao.delete(doctor.getId());
              EventBus.getDefault().post(new DoctorDeleteEvent(doctor));
            }, "انصراف", (dialogInterface, i) -> {
              dialogInterface.dismiss();
            }, R.drawable.ic_info_outline_24dp);
        break;
      case R.id.edit_visit:
        Toast.makeText(activity, "ویرایش وقت ویزیت", Toast.LENGTH_SHORT).show();
        break;
      case R.id.delete_visit:
        Toast.makeText(activity, "حذف وقت ویزیت", Toast.LENGTH_SHORT).show();
        break;

    }
    dismiss();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}