package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Arash on 1/24/18.
 */

public class NewMeasureDialogFragment extends DialogFragment {

  @BindView(R.id.root)
  LinearLayout root;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.measure_unit)
  TextView measureUnit;
  @BindView(R.id.date_value_tv)
  TextView dateValueTv;
  @BindView(R.id.date_layout)
  LinearLayout dateLayout;
  @BindView(R.id.time_value_tv)
  TextView timeValueTv;
  @BindView(R.id.time_layout)
  LinearLayout timeLayout;
  @BindView(R.id.measure_label)
  TextView measureLabel;
  @BindView(R.id.measure_value)
  EditText measureValue;

  private Unbinder unbinder;
  private AppCompatActivity context;
  private MeasureDetailType type;

  public static NewMeasureDialogFragment newInstance(AppCompatActivity context,
      MeasureDetailType type) {
    NewMeasureDialogFragment fragment = new NewMeasureDialogFragment();
    fragment.context = context;
    fragment.type = type;
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
    View view = inflater.inflate(R.layout.fragment_dialog_new_measure, container, false);
    unbinder = ButterKnife.bind(this, view);

    setData();
    return view;
  }

  private void setData() {
    title.setText(String.format("ثبت %s", type.getType()));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(measureValue.getText().toString().trim())) {
      ToastUtil.toastError(root, getString(R.string.error_field_is_empty));
      return false;
    }
    return true;
  }

  @OnClick({R.id.done_img, R.id.close_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
        if (isValid()) {

        }
        break;
      case R.id.close_img:
        getDialog().dismiss();
        break;
    }
  }
}
