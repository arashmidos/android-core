package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

  private Unbinder unbinder;
  private Context context;

  public static NewDoctorDialogFragment newInstance(Context context) {
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
      //TODO:change this message
      Toast.makeText(context, "invalid input", Toast.LENGTH_SHORT).show();
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
          //TODO:save here
          getDialog().dismiss();
        }
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
    }
  }
}
