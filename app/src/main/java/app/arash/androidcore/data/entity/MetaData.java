package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;

public class MetaData {

  @Expose
  private String reference;
  @Expose
  private int app_id;

  public MetaData(String reference, int app_id) {
    this.reference = reference;
    this.app_id = app_id;
  }

  public MetaData() {

  }

  public String getReference() {

    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public int getApp_id() {
    return app_id;
  }

  public void setApp_id(int app_id) {
    this.app_id = app_id;
  }
}

