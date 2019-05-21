package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by arash on 1/28/18.
 */

public class DrugAlarmDetail extends BaseEntity<Long> implements Serializable {

  public static final String TABLE_NAME = "drug_alarm_detail";

  public static final String COL_ID = "_id";
  public static final String COL_ALARM_ID = "alarm_id";
  public static final String COL_DAY = "day";
  public static final String COL_TIME = "time";
  public static final String COL_NUMBER = "number";

  private Long id;
  private Long alarmId;
  private int day;
  private String time;
  private String number;

  public DrugAlarmDetail() {
  }

  public DrugAlarmDetail(Long alarmId, int day, String time, String number) {
    this.alarmId = alarmId;
    this.day = day;
    this.time = time;
    this.number = number;
  }

  public DrugAlarmDetail(Long id, Long alarmId, int day, String time, String number) {
    this.id = id;
    this.alarmId = alarmId;
    this.day = day;
    this.time = time;
    this.number = number;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(Long alarmId) {
    this.alarmId = alarmId;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }
}
