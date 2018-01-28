package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorRefreshEvent;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class NewDoctorDialogFragment extends DialogFragment {

  @BindView(R.id.doctor_name_edt)
  EditText doctorNameEdt;
  @BindView(R.id.field_edt)
  EditText fieldEdt;
  @BindView(R.id.phone_number_edt)
  EditText phoneNumberEdt;
  @BindView(R.id.address_edt)
  EditText addressEdt;
  @BindView(R.id.root)
  LinearLayout root;

  private Unbinder unbinder;
  private MainActivity context;

  public static NewDoctorDialogFragment newInstance(MainActivity context) {
    NewDoctorDialogFragment fragment = new NewDoctorDialogFragment();
    fragment.context = context;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.myDialog);
    setRetainInstance(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_new_doctor, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(doctorNameEdt.getText().toString().trim())) {
      ToastUtil.toastError(root, getString(R.string.error_doctor_name_is_empty));
      return false;
    }
    return true;
  }

  @OnClick({R.id.done_img, R.id.close_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        if (isValid()) {
          String name = doctorNameEdt.getText().toString().trim();
          String phone = phoneNumberEdt.getText().toString().trim();
          String address = addressEdt.getText().toString().trim();
          String field = fieldEdt.getText().toString().trim();

          Doctor doctor = new Doctor(name, field, phone, address);
          Long id = new DoctorDaoImpl(context).create(doctor);
          if (id != null) {
            ToastUtil.toastMessage(context, getString(R.string.message_register_doctor_successful));
            getDialog().dismiss();
            EventBus.getDefault().post(new DoctorRefreshEvent(doctor));
          } else {
            ToastUtil.toastError(root, getString(R.string.error_in_register_doctor));
          }
        }
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
    }
  }
}
