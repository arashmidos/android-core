package app.arash.androidcore.data.entity;

import app.arash.androidcore.BuildConfig;
import com.google.gson.annotations.Expose;

/**
 * Created by shkbhbb on 5/30/18.
 */

public class SendSmsRequest {

  @Expose
  private String mobile;
  @Expose
  private MetaData metadata;

  public SendSmsRequest(String mobile) {
    this.mobile = mobile;
    this.metadata = new MetaData("app", BuildConfig.app_id);
  }

  public MetaData getMetadata() {
    return metadata;
  }

  public void setMetadata(MetaData metadata) {
    this.metadata = metadata;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
