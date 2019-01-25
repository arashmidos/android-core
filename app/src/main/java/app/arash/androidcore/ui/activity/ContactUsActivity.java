package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.ContactPage;
import app.arash.androidcore.data.entity.StaticResponse;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.Event;
import app.arash.androidcore.data.event.StaticPageEvent;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ContactUsActivity extends AppCompatActivity {

  @BindView(R.id.contact_us_text)
  TextView contactUsText;
  @BindView(R.id.contact_us_phone)
  TextView contactUsPhone;
  @BindView(R.id.scroll)
  ScrollView scroll;
  @BindView(R.id.contact_us_mail)
  TextView contactUsMail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_us);
    ButterKnife.bind(this);

    DialogUtil.showProgressDialog(this, "در حال دریافت اطلاعات...");
    new VideoService().getStatics();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  @OnClick({R.id.back_img, R.id.fragment_contact_us_phone_container,
      R.id.fragment_contact_us_email_container})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.fragment_contact_us_phone_container:
        String uri = "tel:" + NumberUtil.digitsToEnglish(contactUsPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
        break;

      case R.id.fragment_contact_us_email_container:
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", contactUsMail.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.android_application));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent,
            getString(R.string.send_email_title)));
        break;
    }
  }


  @Override
  public void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
    DialogUtil.dismissProgressDialog();
  }

  @Subscribe
  public void getMessage(Event event) {
    DialogUtil.dismissProgressDialog();
    if (event instanceof ErrorEvent) {
      if (event.getStatusCode() == StatusCodes.NO_NETWORK) {
        ToastUtil.toastError(this, R.string.error_no_network);
      } else {
        ToastUtil.toastError(this, getString(R.string.error_in_connecting_to_server));
      }
    } else if (event instanceof StaticPageEvent) {

      StaticResponse response = ((StaticPageEvent) event).getSubResponse();
      ContactPage contactPage = response.getContactPage();

      contactUsText.setText(contactPage.getContactUsText());
      contactUsMail.setText(contactPage.getContactUsEmail());
      contactUsPhone.setText(NumberUtil.digitsToPersian(contactPage.getContactUsTelephone()));
      scroll.setVisibility(View.VISIBLE);

    }
  }
}
