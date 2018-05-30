package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by shkbhbb on 5/30/18.
 */

public class VerifyCodeRequest {

  @Expose
  private String mobile;
  @Expose
  private String token;

  public VerifyCodeRequest(String mobile, String token) {
    this.mobile = mobile;
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
