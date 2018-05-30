package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by shkbhbb on 5/30/18.
 */

public class SendSmsRequest {

  @Expose
  private String mobile;

  public SendSmsRequest(String mobile) {
    this.mobile = mobile;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
