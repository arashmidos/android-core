package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import app.arash.androidcore.R;
import app.arash.androidcore.util.Constants;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.regex.Pattern;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PhoneActivity extends AppCompatActivity {

  @BindView(R.id.phone_iv)
  ImageView phoneIv;
  @BindView(R.id.phone_number_edt)
  EditText phoneNumberEdt;
  @BindView(R.id.phone_main_lay)
  ConstraintLayout phoneMainLay;
  private Pattern pattern;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone);
    ButterKnife.bind(this);
    onEditTextFocus();
    onEditTextAction();
    pattern = Pattern.compile(Constants.PATTERN);
  }


  private void onEditTextFocus() {
    phoneNumberEdt.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        phoneIv.setColorFilter(ContextCompat.getColor(this, R.color.green_primary));
      } else {
        phoneIv.setColorFilter(ContextCompat.getColor(this, R.color.gray_9e));
      }
    });
  }

  private void onEditTextAction() {
    phoneNumberEdt.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        onNextClicked();
      }
      return false;
    });
  }

  private boolean isValidPhoneNumber() {
    String userInput = phoneNumberEdt.getText().toString().trim();
    if (!userInput.startsWith("0")) {
      userInput = "0" + userInput;
    }
    if (pattern.matcher(userInput).find()) {
      return true;
    } else {
      ToastUtil.toastError(phoneMainLay, getString(R.string.please_enter_valid_number));
      return false;
    }
  }

  @OnClick({R.id.back_img, R.id.next_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.next_btn:
        onNextClicked();
        break;
    }
  }

  private void onNextClicked() {
    if (isValidPhoneNumber()) {
      String userInput = phoneNumberEdt.getText().toString().trim();
      if (!userInput.startsWith("0")) {
        userInput = "0" + userInput;
      }
      Intent intent = new Intent(this, CodeActivity.class);
      intent.putExtra(Constants.PHONE_NUMBER, userInput);
      startActivity(intent);
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
