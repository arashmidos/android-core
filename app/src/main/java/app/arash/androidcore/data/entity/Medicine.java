package app.arash.androidcore.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class Medicine {

  private long id;
  private String time;
  private String name;
  private int number;
  private int status;

  public Medicine(long id, String time, String name, int number, int status) {
    this.id = id;
    this.time = time;
    this.name = name;
    this.number = number;
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public static List<Medicine> getMedicineList() {
    List<Medicine> medicines = new ArrayList<>();
    medicines.add(new Medicine(1L, "06:30", "استامینوفن", 2, 1));
    medicines.add(new Medicine(2L, "09:30", "هماتروپین", 1, 0));
    medicines.add(new Medicine(3L, "12:50", "سوماتریپتان", 10, 2));
    medicines.add(new Medicine(4L, "13:30", "استامینوفن", 2, 1));
    medicines.add(new Medicine(5L, "18:30", "استامینوفن", 44, 1));
    medicines.add(new Medicine(6L, "06:30", "استامینوفن", 6, 0));
    medicines.add(new Medicine(7L, "06:30", "استامینوفن", 2, 1));
    medicines.add(new Medicine(8L, "06:30", "استامینوفن", 2, 1));
    return medicines;
  }

  public static List<Medicine> getMedicineTopThreeList() {
    List<Medicine> medicines = new ArrayList<>();
    medicines.add(new Medicine(1L, "06:30", "استامینوفن", 2, 1));
    medicines.add(new Medicine(2L, "09:30", "هماتروپین", 1, 0));
//    medicines.add(new Medicine(3L, "12:50", "سوماتریپتان", 10, 2));
    return medicines;
  }
}
