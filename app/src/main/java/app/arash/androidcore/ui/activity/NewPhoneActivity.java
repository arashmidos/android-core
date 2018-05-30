package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.event.ActionEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.Event;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewPhoneActivity extends AppCompatActivity {

  @BindView(R.id.phone_edt)
  EditText phoneEdt;
  @BindView(R.id.root)
  LinearLayout root;
  @BindView(R.id.phone_iv)
  ImageView phoneIv;
  @BindView(R.id.enter_button)
  TextView enterButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_phone);
    ButterKnife.bind(this);
    onEditTextFocus();
  }

  private void onEditTextFocus() {
    phoneEdt.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        phoneIv.setColorFilter(ContextCompat.getColor(this, R.color.color_primary));
      } else {
        phoneIv.setColorFilter(ContextCompat.getColor(this, R.color.gray_9e));
      }
    });
  }

  @OnClick({R.id.submit_btn, R.id.enter_button})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.submit_btn:
      case R.id.enter_button:
        if (!TextUtils.isEmpty(phoneEdt.getText().toString().trim())) {
          DialogUtil.showProgressDialog(this, getString(R.string.message_please_wait));
          new VideoService().sendSms(phoneEdt.getText().toString().trim());
        } else {
          ToastUtil.toastError(root, R.string.enter_phone_is_required);
        }
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
    DialogUtil.dismissProgressDialog();
  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Subscribe
  public void getMessage(Event event) {
    DialogUtil.dismissProgressDialog();
    if (event instanceof ActionEvent) {
      PreferenceHelper.setPhoneNumber(phoneEdt.getText().toString().trim());
      startActivity(new Intent(this, CodeActivity.class));
    } else if (event instanceof ErrorEvent) {
      if (event.getStatusCode() == StatusCodes.NO_NETWORK) {
        ToastUtil.toastError(this, R.string.error_no_network);
      } else {
        ToastUtil.toastError(this, getString(R.string.error_in_connecting_to_server));
      }
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
