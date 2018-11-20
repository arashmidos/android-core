package app.arash.androidcore.data.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by shkbhbb on 4/26/18.
 */

public class GeneralResponse implements Serializable {

  @SerializedName("message")
  private int message;

  public GeneralResponse() {
  }

  public GeneralResponse(int message) {

    this.message = message;
  }

  public int getMessage() {

    return message;
  }

  public void setMessage(int message) {
    this.message = message;
  }
}
