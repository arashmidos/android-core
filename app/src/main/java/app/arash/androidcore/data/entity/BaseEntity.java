package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by Arash on 2018-01-21
 */
public abstract class BaseEntity<PK extends Serializable> implements Serializable {

  public static final String COL_CREATE_DATE_TIME = "CREATE_DATE_TIME";
  public static final String COL_UPDATE_DATE_TIME = "UPDATE_DATE_TIME";

  protected String createDateTime;
  protected String updateDateTime;

  public String getCreateDateTime() {
    return createDateTime;
  }

  public void setCreateDateTime(String createDateTime) {
    this.createDateTime = createDateTime;
  }

  public String getUpdateDateTime() {
    return updateDateTime;
  }

  public void setUpdateDateTime(String updateDateTime) {
    this.updateDateTime = updateDateTime;
  }

  public abstract PK getPrimaryKey();
}
