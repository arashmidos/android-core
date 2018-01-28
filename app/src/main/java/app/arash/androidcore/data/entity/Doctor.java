package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by arash on 1/28/18.
 */

public class Doctor extends BaseEntity<Long> implements Serializable{

  public static final String TABLE_NAME = "doctor";

  public static final String COL_ID = "_id";
  public static final String COL_NAME = "name";
  public static final String COL_EXPERTISE = "expertise";
  public static final String COL_PHONE = "phone";
  public static final String COL_ADDRESS = "address";

  private Long id;
  private String name;
  private String expertise;
  private String phone;
  private String address;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExpertise() {
    return expertise;
  }

  public void setExpertise(String expertise) {
    this.expertise = expertise;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }
}
