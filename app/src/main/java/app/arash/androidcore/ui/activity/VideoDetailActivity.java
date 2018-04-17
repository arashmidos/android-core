package app.arash.androidcore.ui.activity;

import static android.view.View.GONE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.service.PlayerService;
import app.arash.androidcore.service.PlayerService.LocalBinder;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoDetailActivity extends AppCompatActivity {

  private static final String TAG = VideoDetailActivity.class.getSimpleName();
  @Nullable
  @BindView(R.id.scroll)
  ScrollView scrollView;
  @Nullable
  @BindView(R.id.image_preview)
  ImageView imagePreview;
  @BindView(R.id.fullscreen)
  ImageView fullscreen;
  @BindView(R.id.fullscreen_out)
  ImageView fullscreen_out;
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
  SimpleExoPlayerView videoView;
  @BindView(R.id.video_layout)
  FrameLayout videoLayout;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  private Video video;
  private long position = 0;
  private VideoListAdapter listAdapter;
  private boolean loaded;
  private PlayerService playerService;
  private boolean boundToPlayerService;
  private boolean wasPlaying;
  protected final ServiceConnection serviceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      LocalBinder binder = (LocalBinder) service;
      playerService = binder.getService();
      boundToPlayerService = true;
      Log.i(TAG, "Service Bound");
      playerService.playVideo(video);
      if (position != 0 && wasPlaying) {
        playerService.play();
      }

      videoView.setPlayer(playerService.getPlayer());
      videoView.setControlDispatcher(new ControlDispatcher() {
        @Override
        public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
          player.setPlayWhenReady(playWhenReady);
          playerService.play(playWhenReady);
          return true;
        }

        @Override
        public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
          player.seekTo(windowIndex, positionMs);
          return true;
        }

        @Override
        public boolean dispatchSetRepeatMode(Player player, int repeatMode) {
          player.setRepeatMode(repeatMode);
          return true;
        }

        @Override
        public boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled) {
          player.setShuffleModeEnabled(shuffleModeEnabled);
          return true;
        }
      });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      playerService = null;
      boundToPlayerService = false;
    }
  };

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
      toolbar.setVisibility(GONE);
    }
   /* if (mediaController == null) {
      mediaController = new MediaController(this);
    }

    mediaController.setAnchorView(videoView);

    if (scrollView != null) {

      scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
        if (mediaController != null) {
          mediaController.hide();
        }
      });
    }*/

    if (savedInstanceState != null) {
      videoView.setVisibility(View.VISIBLE);
      previewLayout.setVisibility(GONE);
//      videoView.start();
//      videoUrl = videoUrl.replaceAll(" ", "%20");

    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    bindService(new Intent(this, PlayerService.class), serviceConnection,
        Context.BIND_AUTO_CREATE);
    startService(new Intent(this, PlayerService.class));
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (boundToPlayerService) {
      // Unbind from the service. This signals to the service that this activity is no longer
      // in the foreground, and the service can respond by promoting itself to a foreground
      // service.
      unbindService(serviceConnection);
      boundToPlayerService = false;
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

    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      fullscreen_out.setVisibility(View.VISIBLE);
      fullscreen.setVisibility(GONE);
    } else {
      fullscreen_out.setVisibility(GONE);
      fullscreen.setVisibility(View.VISIBLE);
    }
  }

  private void setUpRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    recyclerView.setAdapter(listAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @OnClick({R.id.back_img, R.id.share_img, R.id.preview_layout, R.id.fullscreen,
      R.id.fullscreen_out})
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
        previewLayout.setVisibility(GONE);
        playerService.play();
        break;
      case R.id.fullscreen:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fullscreen.setVisibility(GONE);
        fullscreen_out.setVisibility(View.VISIBLE);
        break;
      case R.id.fullscreen_out:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fullscreen_out.setVisibility(GONE);
        fullscreen.setVisibility(View.VISIBLE);
        break;

//        videoView.start();
    }
  }


  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
    DialogUtil.dismissProgressDialog();
    if (playerService != null) {
      wasPlaying = PlayerService.playing;
      playerService.pause();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    if (!loaded) {
      DialogUtil.showProgressDialog(this, "در حال دریافت اطلاعات ویدیو");
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
    if (videoView == null || videoView.getPlayer() == null) {
      return;
    }
    savedInstanceState.putLong("CurrentPosition", videoView.getPlayer().getCurrentPosition());

    savedInstanceState.putBoolean("wasPlaying", wasPlaying);
//    videoView.pause();
  }


  // After rotating the phone. This method is called.
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    // Get saved position.
    position = savedInstanceState.getLong("CurrentPosition");
    wasPlaying = savedInstanceState.getBoolean("wasPlaying");

//    videoView.seekTo(position);
  }
}
