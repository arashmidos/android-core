package app.arash.androidcore.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by arash on 1/28/18.
 */

public class DrugAlarm extends BaseEntity<Long> implements Serializable {

  public static final String TABLE_NAME = "drug_alarm";

  public static final String COL_ID = "_id";
  public static final String COL_DRUG_ID = "drug_id";
  public static final String COL_TIMES_IN_DAY = "times_in_day";
  public static final String COL_DAYS = "days";
  public static final String COL_LAST_SERVED = "last_served";
  public static final String COL_INSTRUCTION = "instruction";

  private Long id;
  private Long drugId;
  private int timesInDay;
  private String days;
  private String lastServed;
  private String instruction;
  private List<DrugAlarmDetail> alarmDetail;

  public DrugAlarm(Long drugId, int timesInDay, String days, String lastServed,
      String instruction) {
    this.drugId = drugId;
    this.timesInDay = timesInDay;
    this.days = days;
    this.lastServed = lastServed;
    this.instruction = instruction;
  }

  public DrugAlarm(Long id, Long drugId, int timesInDay, String days, String lastServed,
      String instruction) {
    this.id = id;
    this.drugId = drugId;
    this.timesInDay = timesInDay;
    this.days = days;
    this.lastServed = lastServed;
    this.instruction = instruction;
  }

  public DrugAlarm() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDrugId() {
    return drugId;
  }

  public void setDrugId(Long drugId) {
    this.drugId = drugId;
  }

  public int getTimesInDay() {
    return timesInDay;
  }

  public void setTimesInDay(int timesInDay) {
    this.timesInDay = timesInDay;
  }

  public String getDays() {
    return days;
  }

  public void setDays(String days) {
    this.days = days;
  }

  public String getLastServed() {
    return lastServed;
  }

  public void setLastServed(String lastServed) {
    this.lastServed = lastServed;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }

  public List<DrugAlarmDetail> getAlarmDetail() {
    return alarmDetail;
  }

  public void setAlarmDetail(List<DrugAlarmDetail> alarmDetail) {
    this.alarmDetail = alarmDetail;
  }
}
