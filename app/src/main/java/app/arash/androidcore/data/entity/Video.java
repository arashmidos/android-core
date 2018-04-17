package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by arash on 3/1/18.
 */

public class Video implements Serializable{
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("category_id")
  @Expose
  private int categoryId;
  @SerializedName("length")
  @Expose
  private int length;
  @SerializedName("image_preview")
  @Expose
  private String imagePreview;
  @SerializedName("video_url")
  @Expose
  private String videoUrl;
  @SerializedName("body")
  @Expose
  private String body;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public String getImagePreview() {
    return imagePreview;
  }

  public void setImagePreview(String imagePreview) {
    this.imagePreview = imagePreview;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Video video = (Video) o;

    return id != null ? id.equals(video.id) : video.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
