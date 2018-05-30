package app.arash.androidcore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import app.arash.androidcore.R;
import app.arash.androidcore.util.PreferenceHelper;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    Intent intent;
    if (PreferenceHelper.hasLoggedIn()) {
      intent = new Intent(this, MainActivity.class);
    } else if (PreferenceHelper.hasSeenIntro()) {
      intent = new Intent(this, IntroActivity.class);
    } else {
      intent = new Intent(this, NewPhoneActivity.class);
    }
    startActivity(intent);
    finish();
  }
}
