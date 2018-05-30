package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.util.Constants;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.PreferenceHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Locale;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CodeActivity extends AppCompatActivity {

  @BindView(R.id.code_edt)
  EditText codeEdt;
  @BindView(R.id.error_tv)
  TextView errorTv;
  @BindView(R.id.resend_tv)
  TextView resendTv;
  @BindView(R.id.resend_btn)
  Button resendBtn;
  @BindView(R.id.desc_tv)
  TextView descTv;

  private String phoneNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_code);
    ButterKnife.bind(this);
    getIntentData();
    countDown();
    codeEditTextWatcher();
  }

  private void getIntentData() {
    if (!TextUtils.isEmpty(getIntent().getExtras().getString(Constants.PHONE_NUMBER))) {
      phoneNumber = getIntent().getExtras().getString(Constants.PHONE_NUMBER);
      descTv.setText(NumberUtil
          .digitsToPersian(String.format(getString(R.string.code_sent_to_numer_x), phoneNumber)));
    }
  }

  /**
   * set the count down for the text below edit text
   */

  private void countDown() {
    resendBtn.setVisibility(View.GONE);
    resendTv.setVisibility(View.VISIBLE);
    new CountDownTimer(Constants.SMS_WAITING_TOTAL, Constants.INTERVAL) {
      public void onTick(long millisUntilFinished) {
        int minuteLeft = (int) (millisUntilFinished / 60000);
        int secondLeft = (int) ((millisUntilFinished - minuteLeft * 60000) / Constants.INTERVAL);
        String leftTime = String.format(Locale.US, "%02d:%02d", minuteLeft, secondLeft);
        resendTv.setText(NumberUtil
            .digitsToPersian(String.format("%s%s", getString(R.string.retry_code_in), leftTime)));
      }

      public void onFinish() {
        resendBtn.setVisibility(View.VISIBLE);
        resendTv.setVisibility(View.GONE);
      }

    }.start();
  }

  /**
   * on text changed show/hide submit button
   */

  private void codeEditTextWatcher() {
    codeEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 3) {
          errorTv.setVisibility(View.VISIBLE);
          codeEdt.setBackgroundResource(R.drawable.edit_text_line_error);
        } else if (s.length() == 4) {
          PreferenceHelper.setPhoneNumber(phoneNumber);
          PreferenceHelper.setSeenIntro(true);
          Intent intent = new Intent(CodeActivity.this, MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
              | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          startActivity(intent);
          finishAffinity();
        } else {
          errorTv.setVisibility(View.GONE);
          codeEdt.setBackgroundResource(R.drawable.edit_text_line_focus);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  @OnClick({R.id.back_img, R.id.resend_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.resend_btn:
        countDown();
        break;
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
