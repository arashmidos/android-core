package app.arash.androidcore.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class Visit {

  private String time;
  private String doctorName;
  private String leftTime;
  private String filed;

  public Visit(String time, String doctorName, String leftTime, String filed) {
    this.time = time;
    this.doctorName = doctorName;
    this.leftTime = leftTime;
    this.filed = filed;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public String getLeftTime() {
    return leftTime;
  }

  public void setLeftTime(String leftTime) {
    this.leftTime = leftTime;
  }

  public String getFiled() {
    return filed;
  }

  public void setFiled(String filed) {
    this.filed = filed;
  }

  public static List<Visit> getVisitList() {
    List<Visit> visits = new ArrayList<>();
    visits.add(
        new Visit("دوشنبه، 21 بهمن  20:30", "شکیب ایز د بست", "۵ روز دیگر", "متخصص زنان و زایمان"));
    visits
        .add(new Visit("دوشنبه، 21 بهمن  20:30", "جعفر رضایی", "۲ روز دیگر", "متخصص خرابی اعصاب"));
    visits.add(new Visit("دوشنبه، 21 بهمن  20:30", "حسن حسینی", "۳ روز دیگر", "متخصص گوش پا دهن"));
    return visits;
  }
}
