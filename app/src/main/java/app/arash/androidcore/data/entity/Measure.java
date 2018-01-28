package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by arash on 1/28/18.
 */

public class Measure extends BaseEntity<Long> implements Serializable {

  public static final String TABLE_NAME = "measure";

  public static final String COL_ID = "_id";
  public static final String COL_TYPE = "type";
  public static final String COL_VALUE = "value";
  public static final String COL_TIMESTAMP = "timestamp";

  private Long id;
  private int type;
  private int value;
  private String timestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }
}
