package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import app.arash.androidcore.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactUsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_us);
    ButterKnife.bind(this);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @OnClick({R.id.back_img, R.id.fragment_contact_us_phone_container,
      R.id.fragment_contact_us_email_container})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.fragment_contact_us_phone_container:
        String uri = "tel:" + getString(R.string.phone_number_tel);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
        break;

      case R.id.fragment_contact_us_email_container:
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", getString(R.string.email), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.android_application));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent,
            getString(R.string.send_email_title)));
        break;
    }
  }
}
