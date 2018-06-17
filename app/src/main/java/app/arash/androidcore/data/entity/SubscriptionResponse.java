package app.arash.androidcore.data.entity;

/**
 * Created by arash on 6/12/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionResponse {

  @SerializedName("is_user_part_of_Medic")
  @Expose
  private Integer isUserPartOfMedic;

  public Integer getIsUserPartOfMedic() {
    return isUserPartOfMedic;
  }

  public void setIsUserPartOfMedic(Integer isUserPartOfMedic) {
    this.isUserPartOfMedic = isUserPartOfMedic;
  }

}
