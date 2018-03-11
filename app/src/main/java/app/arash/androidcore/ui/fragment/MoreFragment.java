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
import app.arash.androidcore.ui.activity.AboutUsActivity;
import app.arash.androidcore.ui.activity.ContactUsActivity;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.activity.VideoCategoryListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MoreFragment extends BaseFragment {

  @BindView(R.id.version_name)
  TextView versionName;
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
    versionName.setText(String.format("version %s", BuildConfig.VERSION_NAME));
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
        Toast.makeText(mainActivity, "خروج", Toast.LENGTH_SHORT).show();
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
}
