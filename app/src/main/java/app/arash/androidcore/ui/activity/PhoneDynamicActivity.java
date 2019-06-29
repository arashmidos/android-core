package app.arash.androidcore.ui.activity;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.StaticResponse;
import app.arash.androidcore.data.event.ActionEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.Event;
import app.arash.androidcore.data.event.StaticPageEvent;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.util.Constants;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.Empty;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PhoneDynamicActivity extends AppCompatActivity {

  private static final String TAG = PhoneDynamicActivity.class.getName();
  private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

  @BindView(R.id.phone_number_edt)
  EditText phoneNumberEdt;
  @BindView(R.id.phone_main_lay)
  RelativeLayout phoneMainLay;
  @BindView(R.id.up)
  TextView up;
  @BindView(R.id.down)
  TextView down;
  @BindView(R.id.next_btn)
  Button nextBtn;
  @BindView(R.id.tarrif)
  TextView tarrif;
  @BindView(R.id.splash)
  ConstraintLayout splash;
  @BindView(R.id.static_page)
  RelativeLayout staticPage;
  private Pattern pattern;
  private String userInput;
  private int imageDownloaded = 0;
  private StaticResponse response;
  private String upText;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone_dynamic_no_img);
    ButterKnife.bind(this);
    onEditTextAction();
    pattern = Pattern.compile(Constants.PATTERN);

    if (Empty.isNotEmpty(PreferenceHelper.getPhoneNumber())) {
      new Handler().postDelayed(() -> {
        Intent intent = new Intent(PhoneDynamicActivity.this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
      }, 3000);
    } else {
      new VideoService().getStatics();
    }
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

  @OnClick({R.id.next_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.next_btn:
        onNextClicked();
        break;
    }
  }

  private void onNextClicked() {
    if (isValidPhoneNumber()) {
      userInput = phoneNumberEdt.getText().toString().trim();
      if (!userInput.startsWith("0")) {
        userInput = "0" + userInput;
      }
//      PreferenceHelper.setPhoneNumber(userInput)

      if (!isSmsPermissionGranted()) {
        requestPermissions();
      } else {
        DialogUtil.showProgressDialog(this, getString(R.string.message_please_wait));
        new VideoService().sendSms(userInput);
      }
    }
  }

  private void requestPermissions() {
    boolean shouldProvideRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission.RECEIVE_SMS);

    // Provide an additional rationale to the user. This would happen if the user denied the
    // request previously, but didn't check the "Don't ask again" checkbox.
    if (shouldProvideRationale) {
      Log.i(TAG, "Displaying permission rationale to provide additional context.");
      DialogUtil.showConfirmDialog(this, "توجه",
          "برای دریافت خودکار کد ورود به برنامه، دسترسی خواندن پیامک را فعال کنید",
          (dialogInterface, i) -> {

            ActivityCompat.requestPermissions(this,
                new String[]{permission.RECEIVE_SMS}, REQUEST_PERMISSIONS_REQUEST_CODE);
          });
    } else {
      Log.i(TAG, "Requesting permission");
      // Request permission. It's possible this can be auto answered if device policy
      // sets the permission in a given state or the user denied the permission
      // previously and checked "Never ask again".
      ActivityCompat.requestPermissions(PhoneDynamicActivity.this,
          new String[]{permission.RECEIVE_SMS}, REQUEST_PERMISSIONS_REQUEST_CODE);
    }
  }

  /**
   * Callback received when a permissions request has been completed.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Log.i(TAG, "onRequestPermissionResult");
    if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
      if (grantResults.length <= 0) {
        // If user interaction was interrupted, the permission request is cancelled and you
        // receive empty arrays.
        Log.i(TAG, "User interaction was cancelled.");
      } else if ((grantResults.length == 1
          && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
        // Permission was granted.
        PreferenceHelper.setPermissionGranted(true);
      } else {
        PreferenceHelper.setPermissionGranted(false);
      }

      DialogUtil.showProgressDialog(this, getString(R.string.message_please_wait));
      new VideoService().sendSms(userInput);
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

  public boolean isSmsPermissionGranted() {
    return ContextCompat.checkSelfPermission(this, permission.RECEIVE_SMS)
        == PackageManager.PERMISSION_GRANTED;
  }

  @Subscribe
  public void getMessage(Event event) {
    DialogUtil.dismissProgressDialog();
    if (event instanceof ActionEvent && event.getStatusCode() == StatusCodes.SMS_SUCCESS) {

      goToNextPage();
    } else if (event instanceof ErrorEvent) {
      if (event.getStatusCode() == StatusCodes.NO_NETWORK) {
        ToastUtil.toastError(this, R.string.error_no_network);
      } else {
        ToastUtil.toastError(this, getString(R.string.error_in_connecting_to_server));
      }
    } else if (event instanceof StaticPageEvent) {

      this.response = ((StaticPageEvent) event).getSubResponse();
      setupUI();
    }
  }

  private void setupUI() {
    splash.setVisibility(View.GONE);
    staticPage.setVisibility(View.VISIBLE);
    Glide.with(this)
        .load(response.getSendCodePage().getBack())
        .addListener(new RequestListener<Drawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model,
              Target<Drawable> target, boolean isFirstResource) {
            Log.d(TAG, "back failed");
            return false;
          }

          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
              DataSource dataSource, boolean isFirstResource) {
            Log.d(TAG, "back loaded");
//            if (++imageDownloaded == 2) {
//
//            }
            return false;
          }
        })
        .into(new SimpleTarget<Drawable>() {
          @Override
          public void onResourceReady(Drawable resource,
              Transition<? super Drawable> transition) {
            phoneMainLay.setBackground(resource);
          }
        });
    down.setText(response.getSendCodePage().getDown());
    upText = response.getSendCodePage().getUp();
    this.up.setText(upText);


    tarrif.setText(response.getSendCodePage().getLinkText());
//      tarrif.setText(String.format("<a href=\"%s\">%s</a>", response.getSendCodePage().getLink(),response.getSendCodePage().getLinkText()));
    tarrif.setPaintFlags(tarrif.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    tarrif.setOnClickListener(v -> {
      Intent intent = new Intent(Intent.ACTION_VIEW,
          Uri.parse(response.getSendCodePage().getLink()));
      startActivity(intent);
    });
  }

  private void goToNextPage() {
    Intent intent = new Intent(this, CodeActivity.class);
    intent.putExtra(Constants.PHONE_NUMBER, userInput);
    intent.putExtra(Constants.UP_TEXT, upText);

    startActivity(intent);
//      startActivity(new Intent(this, MainActivity.class));
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
