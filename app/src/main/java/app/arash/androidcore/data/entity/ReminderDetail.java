package app.arash.androidcore.data.entity;

/**
 * Created by shkbhbb on 2/15/18.
 */

public class ReminderDetail {

  private String numberInDay = "۱ عدد";
  private String time = "۸:۰۰";

  public ReminderDetail(String numberInDay, String time) {
    this.numberInDay = numberInDay;
    this.time = time;
  }

  public ReminderDetail() {
  }

  public String getNumberInDay() {
    return numberInDay;
  }

  public void setNumberInDay(String numberInDay) {
    this.numberInDay = numberInDay;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
