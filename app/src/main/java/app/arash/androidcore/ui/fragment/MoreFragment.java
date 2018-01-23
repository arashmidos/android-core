package app.arash.androidcore.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.ui.activity.AboutUsActivity;
import app.arash.androidcore.ui.activity.MainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MoreFragment extends BaseFragment {

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
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.about_us_tv, R.id.contact_us_tv, R.id.log_out_tv, R.id.upgrade_app})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.about_us_tv:
        startActivity(new Intent(mainActivity, AboutUsActivity.class));
        break;
      case R.id.contact_us_tv:
        Toast.makeText(mainActivity, "contact us", Toast.LENGTH_SHORT).show();
        break;
      case R.id.log_out_tv:
        Toast.makeText(mainActivity, "log out", Toast.LENGTH_SHORT).show();
        break;
      case R.id.upgrade_app:
        Toast.makeText(mainActivity, "upgrade app", Toast.LENGTH_SHORT).show();
        break;
    }
  }

  @Override
  public int getFragmentId() {
    return MainActivity.MORE_FRAGMENT;
  }
}
