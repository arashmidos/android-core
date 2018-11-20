package app.arash.androidcore.ui.activity;

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
import android.view.Surface;
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
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.service.PlayerService;
import app.arash.androidcore.service.PlayerService.LocalBinder;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.util.DialogUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import app.arash.androidcore.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.DefaultEventListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VideoDetailActivity extends AppCompatActivity {

  private static final String TAG = VideoDetailActivity.class.getSimpleName();

  @Nullable
  @BindView(R.id.scroll)
  ScrollView scrollView;
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
  @BindView(R.id.player_view)
  PlayerView playerView;
  @BindView(R.id.video_layout)
  FrameLayout videoLayout;
  @BindView(R.id.toolbar)
  LinearLayout toolbar;
  @BindView(R.id.fullscreen)
  ImageView fullscreen;
  @BindView(R.id.fullscreen_out)
  ImageView fullscreen_out;
  private Video video;
  private long position = 0;
  private VideoListAdapter listAdapter;
  private boolean loaded;
  private VideoService videoService;
  private boolean wasPlaying;
  private List<Video> videos;
  private PlayerService playerService;
  private boolean boundToPlayerService;
  private ServiceConnection serviceConnection;
  private ComponentListener componentListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_detail);
    ButterKnife.bind(this);

    videoService = new VideoService();

    if (savedInstanceState != null) {
      position = savedInstanceState.getLong("CurrentPosition");
      wasPlaying = savedInstanceState.getBoolean("wasPlaying");
      videos = (List<Video>) savedInstanceState.getSerializable("videoList");
      video = (Video) savedInstanceState.getSerializable("video");
    } else {
      playerView.setVisibility(View.INVISIBLE);
      imagePreview.setVisibility(View.VISIBLE);
    }

    /////////
    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      fullscreen_out.setVisibility(View.VISIBLE);
      fullscreen.setVisibility(View.GONE);
    } else {
      if (video == null) {
        getIntentData();
      }

      setContent();

      if (videos == null) {
        if (video != null) {
          videoService.getVideoList(video.getCategoryId(), 10);
        }
      } else {
        setUpRecyclerView();
      }
      fullscreen_out.setVisibility(View.GONE);
      fullscreen.setVisibility(View.VISIBLE);
    }
    if (video != null) {
      initServiceConnection();
    }
    componentListener = new ComponentListener();
    /////////

    if (recyclerView == null) {
      toolbar.setVisibility(View.GONE);
    }
  }

  private void startVideo() {
    imagePreview.setVisibility(View.GONE);
    previewLayout.setVisibility(View.GONE);
    playerView.setVisibility(View.VISIBLE);
    playerService.play();
    wasPlaying = true;
  }

  private void initServiceConnection() {
    serviceConnection = new ServiceConnection() {

      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
        LocalBinder binder = (LocalBinder) service;
        playerService = binder.getService();
        boundToPlayerService = true;
        Log.i(TAG, "Service Bound");
        boolean returning = playerService.playSong(video);
        if (returning && wasPlaying) {
          startVideo();
        }

        playerView.setPlayer(playerService.getPlayer());
        playerView.setControlDispatcher(new ControlDispatcher() {
          @Override
          public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
            player.setPlayWhenReady(playWhenReady);
            playerService.play(playWhenReady);
            wasPlaying = playWhenReady;
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

          @Override
          public boolean dispatchStop(Player player, boolean reset) {
            player.stop(reset);
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
  }

  private void getIntentData() {
    Intent intent = getIntent();

    if (intent != null && intent.getSerializableExtra(Constant.VIDEO) != null) {
      video = (Video) intent.getSerializableExtra(Constant.VIDEO);
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

  @OnClick({R.id.back_img, R.id.share_img, R.id.preview_layout, R.id.fullscreen_out,
      R.id.fullscreen})
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
        startVideo();
        break;
      case R.id.fullscreen:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        fullscreen.setVisibility(View.GONE);
        fullscreen_out.setVisibility(View.VISIBLE);
        break;
      case R.id.fullscreen_out:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fullscreen_out.setVisibility(View.GONE);
        fullscreen.setVisibility(View.VISIBLE);
        break;
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (serviceConnection != null) {
      bindService(new Intent(this, PlayerService.class), serviceConnection,
          Context.BIND_AUTO_CREATE);
      startService(new Intent(this, PlayerService.class));
    }
    Log.i(TAG, "onStart");
  }

  @Override
  public void onStop() {
    super.onStop();
    if (boundToPlayerService) {
      // Unbind from the service. This signals to the service that this activity is no longer
      // in the foreground, and the service can respond by promoting itself to a foreground
      // service.
      unbindService(serviceConnection);
      boundToPlayerService = false;
    }
    Log.i(TAG, "onStop");
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    if (!loaded) {
      videoService.getVideoList(video.getCategoryId(), 5);
    }
  }

  @Subscribe
  public void getMessage(VideoEvent event) {
    DialogUtil.dismissProgressDialog();
    loaded = true;
    List<Video> list = new ArrayList<>();
    List<Video> videoList = event.getVideoList();
    for (int i = 0; i < videoList.size(); i++) {
      if (!video.getId().equals(videoList.get(i).getId())) {
        list.add(videoList.get(i));
      }
    }
    listAdapter = new VideoListAdapter(this, list);
    setUpRecyclerView();
  }

  @Subscribe
  public void getMessage(ErrorEvent event) {
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

  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSavedInstance");
    // Store current position.
    if (playerView == null || playerView.getPlayer() == null) {
      return;
    }
    savedInstanceState.putLong("CurrentPosition", playerView.getPlayer().getCurrentPosition());

    savedInstanceState.putBoolean("wasPlaying", wasPlaying);
    savedInstanceState.putSerializable("video", video);
    savedInstanceState.putSerializable("videoList", (Serializable) videos);
    position = savedInstanceState.getLong("CurrentPosition");
  }

  private void setUpRecyclerView() {

    if (videos != null && video != null) {
      videos.remove(video);
    }

    if (recyclerView != null) {
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(listAdapter);
    }
  }

  // After rotating the phone. This method is called.
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  private class ComponentListener extends DefaultEventListener implements
      VideoRendererEventListener, AudioRendererEventListener {

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
      String stateString;
      switch (playbackState) {
        case ExoPlayer.STATE_IDLE:
          stateString = "ExoPlayer.STATE_IDLE      -";
          break;
        case ExoPlayer.STATE_BUFFERING:
          stateString = "ExoPlayer.STATE_BUFFERING -";
          break;
        case ExoPlayer.STATE_READY:
          stateString = "ExoPlayer.STATE_READY     -";
          break;
        case ExoPlayer.STATE_ENDED:
          stateString = "ExoPlayer.STATE_ENDED     -";
          break;
        default:
          stateString = "UNKNOWN_STATE             -";
          break;
      }
      Log.d(TAG, "changed state to " + stateString
          + " playWhenReady: " + playWhenReady);
    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {
    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs,
        long initializationDurationMs) {
    }

    @Override
    public void onVideoInputFormatChanged(Format format) {
    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
        float pixelWidthHeightRatio) {
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {
    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {
    }

    @Override
    public void onAudioEnabled(DecoderCounters counters) {
    }

    @Override
    public void onAudioSessionId(int audioSessionId) {
    }

    @Override
    public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs,
        long initializationDurationMs) {
    }

    @Override
    public void onAudioInputFormatChanged(Format format) {
    }

    @Override
    public void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs,
        long elapsedSinceLastFeedMs) {
    }

    @Override
    public void onAudioDisabled(DecoderCounters counters) {
    }
  }
}
