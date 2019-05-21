package app.arash.androidcore.data.entity;

import android.support.annotation.NonNull;
import app.arash.androidcore.util.NumberUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arash on 2/17/18.
 */

public class DrugAlarmModel implements Comparable<DrugAlarmModel> {

  private String drugName;
  private String time;
  private String number;
  private int hour;
  private int minute;

  public DrugAlarmModel(String drugName, String time, String number) {
    this.drugName = drugName;
    this.time = time;
    this.number = number;
    String[] times = time.split(":");
    hour = Integer.parseInt(NumberUtil.digitsToEnglish(times[0]));
    minute = Integer.parseInt(NumberUtil.digitsToEnglish(times[1]));
  }

  public DrugAlarmModel() {

  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public String getDrugName() {
    return drugName;
  }

  public void setDrugName(String drugName) {
    this.drugName = drugName;
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
  public int compareTo(@NonNull DrugAlarmModel model) {

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      Date thisDate = sdf.parse(NumberUtil.digitsToEnglish(time));
      Date otherDate = sdf.parse(NumberUtil.digitsToEnglish(model.getTime()));
      return thisDate.compareTo(otherDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return 0;
  }

  public int getStatus() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    try {
      Date thisDate = sdf.parse(NumberUtil.digitsToEnglish(time));
      Date morning = sdf.parse("05:00");
      Date mid = sdf.parse("12:00");
      Date night = sdf.parse("19:00");
      if (thisDate.after(morning) && thisDate.before(mid)) {
        return 0;
      }
      if (thisDate.equals(mid) || (thisDate.after(mid) && thisDate.before(night))) {
        return 1;
      }
      return 2;
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return 2;
  }
}
