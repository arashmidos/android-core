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
import app.arash.androidcore.data.event.CategoryEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.adapter.VideoCategoryListAdapter;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.util.DialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    DialogUtil.showProgressDialog(this, "در حال دریافت لیست ویدیو ها");
    new VideoService().getVideoList(category.getId());
  }

  @Subscribe
  public void getMessage(VideoEvent event) {
    DialogUtil.dismissProgressDialog();
    listAdapter = new VideoListAdapter(this, event.getVideoList());
    recyclerView.setAdapter(listAdapter);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}