package app.arash.androidcore.data.event;

import app.arash.androidcore.data.entity.Video;
import java.util.List;

/**
 * Created by arash on 3/1/18.
 */

public class VideoEvent {

  private List<Video> videoList;

  public VideoEvent(List<Video> videoList) {
    this.videoList = videoList;
  }

  public List<Video> getVideoList() {
    return videoList;
  }

  public void setVideoList(List<Video> videoList) {
    this.videoList = videoList;
  }
}
