package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendCodePage {

  @SerializedName("mid")
  @Expose
  private String mid;
  @SerializedName("back")
  @Expose
  private String back;
  @SerializedName("up")
  @Expose
  private String up;
  @SerializedName("down")
  @Expose
  private String down;

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getBack() {
    return back;
  }

  public void setBack(String back) {
    this.back = back;
  }

  public String getUp() {
    return up;
  }

  public void setUp(String up) {
    this.up = up;
  }

  public String getDown() {
    return down;
  }

  public void setDown(String down) {
    this.down = down;
  }

}
