package app.arash.androidcore.data.entity;

/**
 * Created by arash on 1/28/18.
 */

public class DoctorDeleteEvent {

  private Doctor doctor;

  public DoctorDeleteEvent(Doctor doctor) {
    this.doctor = doctor;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }
}
