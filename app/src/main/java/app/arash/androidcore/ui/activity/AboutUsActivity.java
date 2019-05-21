package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AboutUsActivity extends AppCompatActivity {

  @BindView(R.id.description_tv)
  TextView descriptionTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_us);
    ButterKnife.bind(this);

    DialogUtil.showProgressDialog(this, "در حال دریافت اطلاعات...");
    new VideoService().getStatics();

  }

  @OnClick(R.id.back_img)
  public void onViewClicked() {
    DialogUtil.dismissProgressDialog();
    onBackPressed();
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

      descriptionTv.setVisibility(View.VISIBLE);
      descriptionTv.setText(contactPage.getAboutUs());
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
