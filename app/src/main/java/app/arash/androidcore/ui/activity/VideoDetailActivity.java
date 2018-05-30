package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoDetailActivity extends AppCompatActivity {

  @Nullable
  @BindView(R.id.scroll)
  ScrollView scrollView;
  @Nullable
  @BindView(R.id.image_preview)
  ImageView imagePreview;

  @BindView(R.id.preview_layout)
  RelativeLayout previewLayout;
  @Nullable
  @BindView(R.id.title)
  TextView title;
  @Nullable
  @BindView(R.id.video_body)
  TextView videoBody;
  @Nullable
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @Nullable
  @BindView(R.id.video_time)
  TextView videoTime;
  @BindView(R.id.video_view)
  VideoView videoView;
  @BindView(R.id.video_layout)
  FrameLayout videoLayout;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  private Video video;
  private int position = 0;
  private VideoListAdapter listAdapter;
  private MediaController mediaController;
  private boolean loaded;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_detail);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    if (intent == null || intent.getSerializableExtra(Constant.VIDEO) == null) {
      setContentView(R.layout.view_error_page);
      return;
    }

    video = (Video) intent.getSerializableExtra(Constant.VIDEO);
    setContent();

    if (recyclerView != null) {
      setUpRecyclerView();
    } else {
      toolbar.setVisibility(View.GONE);
    }
    if (mediaController == null) {
      mediaController = new MediaController(this);
    }

    mediaController.setAnchorView(videoView);
    videoView.setMediaController(mediaController);
    String videoUrl = video.getVideoUrl();
    videoUrl = videoUrl.replaceAll(" ", "%20");
    videoView.setVideoURI(Uri.parse(videoUrl));
    videoView.setOnPreparedListener(mediaPlayer -> {
      videoView.seekTo(position);

      // When video Screen change size.
      mediaPlayer.setOnVideoSizeChangedListener(
          (mp, width, height) -> mediaController.setAnchorView(videoView));
    });

    if (scrollView != null) {

      scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
        if (mediaController != null) {
          mediaController.hide();
        }
      });
    }

    if (savedInstanceState != null) {
      videoView.setVisibility(View.VISIBLE);
      previewLayout.setVisibility(View.GONE);
      videoView.start();
    }
  }

  private void setContent() {
    Glide.with(this).load(video.getImagePreview()).into(imagePreview);
    if (title != null) {
      title.setText(video.getTitle());
      videoBody.setText(video.getBody());
    }
    videoTime.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%02d:%02d",
        video.getLength() / 60, video.getLength() % 60)));
  }

  private void setUpRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    recyclerView.setAdapter(listAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @OnClick({R.id.back_img, R.id.share_img, R.id.preview_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.share_img:
        String shareContent = video.getTitle() + "\n" + video.getVideoUrl();
        Utils.shareContent(this, shareContent);
        break;
      case R.id.preview_layout:
        videoView.setVisibility(View.VISIBLE);
        previewLayout.setVisibility(View.GONE);
        videoView.start();
    }
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
    DialogUtil.showProgressDialog(this, "در حال دریافت اطلاعات ویدیو");
    if (!loaded) {
      new VideoService().getVideoList(video.getCategoryId(), 5);
      loaded = true;
    }
  }

  @Subscribe
  public void getMessage(VideoEvent event) {
    DialogUtil.dismissProgressDialog();
    List<Video> list = new ArrayList<>();
    List<Video> videoList = event.getVideoList();
    for (int i = 0; i < videoList.size(); i++) {
      if (!video.getId().equals(videoList.get(i).getId())) {
        list.add(videoList.get(i));
      }
    }
    listAdapter = new VideoListAdapter(this, list);
    if (recyclerView != null) {

      recyclerView.setAdapter(listAdapter);
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);

    // Store current position.
    savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
    videoView.pause();
  }


  // After rotating the phone. This method is called.
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    // Get saved position.
    position = savedInstanceState.getInt("CurrentPosition");
    videoView.seekTo(position);
  }
}
