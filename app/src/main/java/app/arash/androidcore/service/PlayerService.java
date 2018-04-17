package app.arash.androidcore.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.util.Empty;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player.DefaultEventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Arash
 *
 *         This class is a long-running service for location updates. When an activity is bound to
 *         this service, frequent location updates are permitted. When the activity is removed from
 *         the foreground, the service promotes itself to a foreground service, and location updates
 *         continue. When the activity comes back to the foreground, the foreground service stops,
 *         and the notification assocaited with that service is removed.
 */
public class PlayerService extends Service {

  private static final String PACKAGE_NAME = "app.arash.androidcore.service";
  public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";
  public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
  public static final String EXTRA_POSITION = PACKAGE_NAME + ".position";
  public static final String ACTION_PLAY_LIVE = PACKAGE_NAME + ".playlive";
  public static final String ACTION_PAUSE_LIVE = PACKAGE_NAME + ".pauselive";
  private static final String TAG = PlayerService.class.getSimpleName();
  private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
      ".started_from_notification";
  /**
   * The identifier for the notification displayed for the foreground service.
   */
  private static final int NOTIFICATION_ID = 12345679;
  private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
  public static boolean playing;
  private final IBinder mBinder = new LocalBinder();
  /**
   * Used to check whether the bound activity has really gone away and not unbound as part of an
   * orientation change. We create a foreground service notification only if the former takes
   * place.
   */
  private boolean changingConfiguration = false;
  private NotificationManager notificationManager;
  private Handler serviceHandler;
  private ComponentListener componentListener;
  private SimpleExoPlayerView videoView;
  private SimpleExoPlayer player;
  private int currentWindow = 0;
  private long playbackPosition = 0;
  private boolean playWhenReady = true;
  private Video currentVideo;

  public PlayerService() {
  }

  public Video getCurrentVideo() {
    return currentVideo;
  }

  public void setCurrentVideo(Video currentVideo) {
    this.currentVideo = currentVideo;
  }

  @Override
  public void onCreate() {
    try {

      HandlerThread handlerThread = new HandlerThread(TAG);
      handlerThread.start();
      serviceHandler = new Handler(handlerThread.getLooper());
      notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      componentListener = new ComponentListener();
      initializePlayer();
      Log.i(TAG, "Player service created");

    } catch (SecurityException ex) {
      ex.printStackTrace();
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.ACTION_PLAYER_FAILED));
    }
  }

  private void initializePlayer() {
    if (player == null) {
      // a factory to create an AdaptiveVideoTrackSelection
      TrackSelection.Factory adaptiveTrackSelectionFactory =
          new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

      player = ExoPlayerFactory.newSimpleInstance(
          new DefaultRenderersFactory(this),
          new DefaultTrackSelector(adaptiveTrackSelectionFactory),
          new DefaultLoadControl());
      player.addListener(componentListener);

//    player = ExoPlayerFactory.newSimpleInstance(
//        new DefaultRenderersFactory(this),
//        new DefaultTrackSelector(), new DefaultLoadControl());
      player.addVideoDebugListener(componentListener);
      player.addAudioDebugListener(componentListener);
//      videoView.setPlayer(player);

      player.setPlayWhenReady(playWhenReady);
      player.seekTo(currentWindow, playbackPosition);
    }
  }

  private void releasePlayer() {
    if (player != null) {
      playbackPosition = player.getCurrentPosition();
      player.removeListener(componentListener);
      player.removeVideoDebugListener(componentListener);
      player.removeAudioDebugListener(componentListener);
      currentWindow = player.getCurrentWindowIndex();
      playWhenReady = player.getPlayWhenReady();
      player.release();
      player = null;
    }
  }

  private MediaSource buildMediaSourceList(Uri uri) {
    // these are reused for both media sources we create below
/*    DefaultExtractorsFactory extractorsFactory =
        new DefaultExtractorsFactory();
    DefaultHttpDataSourceFactory dataSourceFactory =
        new DefaultHttpDataSourceFactory("user-agent");
*/
    ExtractorMediaSource videoSource =
        new ExtractorMediaSource.Factory(
            new DefaultHttpDataSourceFactory("exoplayer-codelab")).
            createMediaSource(uri);

//    Uri audioUri = Uri.parse(uri);
/*    ExtractorMediaSource audioSource =
        new ExtractorMediaSource.Factory(
            new DefaultHttpDataSourceFactory("exoplayer-codelab")).
            createMediaSource(uri);*/

    return new ConcatenatingMediaSource(videoSource);
  }

  private MediaSource buildMediaSource(Uri uri) {
    DataSource.Factory manifestDataSourceFactory =
        new DefaultHttpDataSourceFactory("ua");
    DashChunkSource.Factory dashChunkSourceFactory =
        new DefaultDashChunkSource.Factory(
            new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
    return new DashMediaSource.Factory(dashChunkSourceFactory,
        manifestDataSourceFactory).createMediaSource(uri);
  }

//  private MediaSource buildLiveMediaSource(Uri uri) {
//    return new ExtractorMediaSource.Factory(
//        new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri);

//    RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
// Produces Extractor instances for parsing the media data.
//    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
//    MediaSource videoSource = new ExtractorMediaSource(uri,
//        rtmpDataSourceFactory, extractorsFactory, null, null);

//    return videoSource;
//  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(TAG, "Player service called");

    boolean startedFromNotification = false;
    String action = "";
    if (Empty.isNotEmpty(intent)) {
      startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false);
      action = intent.getAction();
    }
    if (Empty.isEmpty(action)) {
      return START_NOT_STICKY;
    }

    switch (action) {
      case ACTION_PLAY_LIVE:
        break;
      case ACTION_PAUSE_LIVE:
        pause();
        break;
    }

    // We got here because the user decided to remove location updates from the notification.
    if (startedFromNotification) {
//      removeLocationUpdates();
//      stopSelf();
    }
    // Tells the system to not try to recreate the service after it has been killed.

    return START_NOT_STICKY;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    changingConfiguration = true;
  }

  @Override
  public IBinder onBind(Intent intent) {
    // Called when a client (MainActivity in case of this sample) comes to the foreground
    // and binds with this service. The service should cease to be a foreground service
    // when that happens.
    Log.i(TAG, "in onBind()");
    stopForeground(true);
    changingConfiguration = false;
    return mBinder;
  }

  @Override
  public void onRebind(Intent intent) {
    // Called when a client (MainActivity in case of this sample) returns to the foreground
    // and binds once again with this service. The service should cease to be a foreground
    // service when that happens.
    Log.i(TAG, "in onRebind()");
    stopForeground(true);
    changingConfiguration = false;
    super.onRebind(intent);
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Log.i(TAG, "Last client unbound from service");

    // Called when the last client (MainActivity in case of this sample) unbinds from this
    // service. If this method is called due to a configuration change in MainActivity, we
    // do nothing. Otherwise, we make this service a foreground service.
    Log.i(TAG, "Starting foreground service");

    return true; // Ensures onRebind() is called when a client re-binds.
  }

  @Override
  public void onDestroy() {
    try {
      if (serviceHandler != null) {
        serviceHandler.removeCallbacksAndMessages(null);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns true if this is a foreground service.
   *
   * @param context The {@link Context}.
   */
  public boolean serviceIsRunningInForeground(Context context) {
    ActivityManager manager = (ActivityManager) context.getSystemService(
        Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
        Integer.MAX_VALUE)) {
      if (getClass().getName().equals(service.service.getClassName())) {
        if (service.foreground) {
          return true;
        }
      }
    }
    return false;
  }

  public void pause() {
    if (player != null) {
      player.setPlayWhenReady(false);
      playing = false;
      Log.d(TAG, "Player paused...");
    }
  }

  public void play() {
    if (player != null) {
      player.setPlayWhenReady(true);
      playing = true;
      Log.d(TAG, "Player playing...");
    }
  }

  public SimpleExoPlayer getPlayer() {
    return player;
  }

  public boolean playVideo(Video post) {

    if (currentVideo != null && currentVideo.equals(post)) {

      return true;
    }
    currentVideo = post;
    Uri uri = Uri.parse(currentVideo.getVideoUrl());
    MediaSource mediaSource = null;
    mediaSource = buildMediaSourceList(uri);

    player.setPlayWhenReady(false);
    playing = false;
    player.prepare(mediaSource, true, false);
    return false;
  }

  private MediaSource buildLiveVideoSource(Uri uri) {
    return new HlsMediaSource(uri, new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER),
        serviceHandler, null);
  }

  public void play(boolean playWhenReady) {
    playing = playWhenReady;
  }

  private class ComponentListener extends DefaultEventListener implements
      VideoRendererEventListener, AudioRendererEventListener {

    @Override
    public void onPlayerStateChanged(boolean playWhenReady,
        int playbackState) {
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

  /**
   * Class used for the client Binder.  Since this service runs in the same process as its
   * clients, we don't need to deal with IPC.
   */
  public class LocalBinder extends Binder {

    public PlayerService getService() {
      return PlayerService.this;
    }
  }
}
