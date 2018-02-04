package app.arash.androidcore.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.arash.androidcore.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_us);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.back_img)
  public void onViewClicked() {
    onBackPressed();
  }
}
