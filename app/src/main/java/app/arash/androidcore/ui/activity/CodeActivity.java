package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony.Sms.Intents;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.BuildConfig;
import app.arash.androidcore.R;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.event.ActionEvent;
import app.arash.androidcore.data.event.Event;
import app.arash.androidcore.receiver.SmsBroadcastReceiver;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.util.Constants;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CodeActivity extends AppCompatActivity {

  private static final String TAG = CodeActivity.class.getName();
  @BindView(R.id.code_edt)
  EditText codeEdt;
  @BindView(R.id.error_tv)
  TextView errorTv;
  @BindView(R.id.timer_tv)
  TextView timerTv;
  @BindView(R.id.resend_tv)
  TextView resendTv;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.desc_tv)
  TextView descTv;
  @BindView(R.id.back_btn)
  ImageView backBtn;
  @BindView(R.id.next_btn)
  Button nextBtn;
  private String phoneNumber;
  private SmsBroadcastReceiver smsBroadcastReceiver;

  private TextWatcher watcher;

  private VideoService videoService;
  private String mobile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_code);
    ButterKnife.bind(this);
    videoService = new VideoService();
    getIntentData();
    countDown();
    codeEditTextWatcher();
    registerSmsReceiver();
  }


  private void codeEditTextWatcher() {
    watcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
          backBtn.setEnabled(true);
          nextBtn.setEnabled(true);

        } else {
          codeEdt.setBackgroundResource(R.drawable.edit_text_line_focus);
          backBtn.setEnabled(false);
          nextBtn.setEnabled(false);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    };
    codeEdt.addTextChangedListener(watcher);
  }

  private void registerSmsReceiver() {
    smsBroadcastReceiver = new SmsBroadcastReceiver(BuildConfig.SERVICE_NUMBER);
    registerReceiver(smsBroadcastReceiver,
        new IntentFilter(Intents.SMS_RECEIVED_ACTION));

    smsBroadcastReceiver.setListener(text -> {
      Log.d(TAG, text);
      codeEdt.setText(text);
    });
  }

  private void getIntentData() {
    if (!TextUtils.isEmpty(getIntent().getExtras().getString(Constants.PHONE_NUMBER))) {
      phoneNumber = getIntent().getExtras().getString(Constants.PHONE_NUMBER);
    }
    descTv.setText(NumberUtil
        .digitsToPersian(String.format(getString(R.string.code_sent_to_numer_x), phoneNumber)));
  }

  private void countDown() {
    resendTv.setVisibility(View.GONE);
    timerTv.setVisibility(View.VISIBLE);
    new CountDownTimer(Constants.SMS_WAITING_TOTAL, Constants.INTERVAL) {
      public void onTick(long millisUntilFinished) {
        int minuteLeft = (int) (millisUntilFinished / 60000);
        int secondLeft = (int) ((millisUntilFinished - minuteLeft * 60000) / Constants.INTERVAL);
        String leftTime = String.format(Locale.US, "%02d:%02d", minuteLeft, secondLeft);
        timerTv.setText(NumberUtil
            .digitsToPersian(String.format("%s %s", getString(R.string.retry_code_in), leftTime)));
      }

      public void onFinish() {
        resendTv.setVisibility(View.VISIBLE);
        timerTv.setVisibility(View.GONE);
      }

    }.start();
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(codeEdt.getText().toString().trim())
        || codeEdt.getText().toString().length() != 4) {
      errorTv.setVisibility(View.VISIBLE);
      codeEdt.setBackgroundResource(R.drawable.edit_text_line_error);
      return false;
    }
    return true;
  }

  @OnClick({R.id.back_btn, R.id.resend_tv, R.id.next_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.resend_tv:
        countDown();
        videoService.sendSms(PreferenceHelper.getPhoneNumber());
        break;
      case R.id.back_btn:
      case R.id.next_btn:
        if (isValid()) {
          DialogUtil.showProgressDialog(this, getString(R.string.message_please_wait));
          videoService.verifyCode(phoneNumber, codeEdt.getText().toString().trim());
        }
        break;
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

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  @Subscribe
  public void getMessage(Event event) {
    DialogUtil.dismissProgressDialog();
    if (event instanceof ActionEvent) {
      if (event.getStatusCode() == StatusCodes.SMS_SUCCESS) {
        ToastUtil.toastMessage(this, "کد تایید با موفقیت ارسال شد");
      } else if (event.getStatusCode() == StatusCodes.SUCCESS) {
        PreferenceHelper.setPhoneNumber(phoneNumber);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
      }
    } else {
      if (event.getStatusCode() == StatusCodes.NO_NETWORK) {
        ToastUtil.toastError(this, R.string.error_no_network);
      } else if (event.getStatusCode() == StatusCodes.AUTHENTICATE_ERROR) {
        ToastUtil.toastError(this, getString(R.string.credit_low));
      } else {
        ToastUtil.toastError(this, getString(R.string.entered_code_in_wrong));
      }
    }
  }
}
