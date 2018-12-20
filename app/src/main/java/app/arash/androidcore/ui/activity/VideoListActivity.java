package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VideoListActivity extends AppCompatActivity {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.toolbar_title_tv)
  TextView toolbarTitleTv;
  private VideoListAdapter listAdapter;
  private Category category;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_list);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    if (intent == null || intent.getSerializableExtra(Constant.VIDEO_CATEGORY) == null) {
      setContentView(R.layout.view_error_page);
      return;
    }

    category = (Category) intent.getSerializableExtra(Constant.VIDEO_CATEGORY);
    toolbarTitleTv.setText(category.getName().trim());

    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(), layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(listAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @OnClick(R.id.back_img)
  public void onViewClicked(View view) {
    onBackPressed();
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
    DialogUtil.showProgressDialog(this, getString(R.string.getting_video_list));
    new VideoService().getVideoList(category.getId());
  }

  @Subscribe
  public void getMessage(VideoEvent event) {
    DialogUtil.dismissProgressDialog();
    listAdapter = new VideoListAdapter(this, event.getVideoList());
    recyclerView.setAdapter(listAdapter);
  }

  @Subscribe
  public void getMessage(ErrorEvent event) {
    DialogUtil.dismissProgressDialog();

    switch (event.getStatusCode()) {
      case NO_NETWORK:
        ToastUtil.toastError(this, R.string.error_no_network);
        break;
      case AUTHENTICATE_ERROR:
        ToastUtil.toastError(this, getString(R.string.credit_low));
        Intent intent = new Intent(this, NewPhoneActivity.class);
        PreferenceHelper.setToken("");
        startActivity(intent);
        finish();
        break;
      case NETWORK_ERROR:
        ToastUtil.toastError(this, getString(R.string.error_in_connecting_to_server));
        break;
      case NO_DATA_ERROR:
        ToastUtil.toastError(this, getString(R.string.no_data_found));
        break;
      case SERVER_ERROR:
        ToastUtil.toastError(this, getString(R.string.error_server));
        break;
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
