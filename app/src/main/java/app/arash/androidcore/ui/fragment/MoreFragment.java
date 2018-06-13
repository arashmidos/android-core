package app.arash.androidcore.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.BuildConfig;
import app.arash.androidcore.R;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.event.Event;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.activity.AboutUsActivity;
import app.arash.androidcore.ui.activity.ContactUsActivity;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.activity.VideoCategoryListActivity;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.PreferenceHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MoreFragment extends BaseFragment {

  @BindView(R.id.version_name)
  TextView versionName;
  @BindView(R.id.phone)
  TextView phone;
  private Unbinder unbinder;
  private MainActivity mainActivity;

  public MoreFragment() {
    // Required empty public constructor
  }

  public static MoreFragment newInstance() {
    return new MoreFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_more, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();

    setData();
    return view;
  }

  private void setData() {
    versionName
        .setText(NumberUtil.digitsToPersian(String.format("نسخه %s", BuildConfig.VERSION_NAME)));
    phone.setText(NumberUtil.digitsToPersian(PreferenceHelper.getPhoneNumber()));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.about_us_tv, R.id.contact_us_tv, R.id.log_out_tv, R.id.upgrade_app,
      R.id.videos_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.about_us_tv:
        startActivity(new Intent(mainActivity, AboutUsActivity.class));
        break;
      case R.id.contact_us_tv:
        startActivity(new Intent(mainActivity, ContactUsActivity.class));
        break;
      case R.id.videos_tv:
        startActivity(new Intent(mainActivity, VideoCategoryListActivity.class));
        break;
      case R.id.log_out_tv:
        DialogUtil
            .showCustomDialog(mainActivity, getString(R.string.logout_account), getString(R.string.message_confirm_exit),
                getString(R.string.exit), (dialogInterface, i) -> {
              new VideoService().unsubscribe();

                }, getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss(),
                R.drawable.ic_info_outline_24dp);
        break;
      case R.id.upgrade_app:
        Toast.makeText(mainActivity, "ارتقا نرم افزار", Toast.LENGTH_SHORT).show();
        break;
    }
  }

  @Override
  public int getFragmentId() {
    return MainActivity.MORE_FRAGMENT;
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
    if (event.getStatusCode() == StatusCodes.SUCCESS) {
      PreferenceHelper.setPhoneNumber("");
      PreferenceHelper.setToken("");
      mainActivity.finish();
    }
  }
}
