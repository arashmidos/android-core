package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by arash on 1/28/18.
 */

public class DoctorVisitDto implements Serializable {

  private Long id;
  private String visitDate;
  private String visitTime;
  private String description;
  private Doctor doctor;
  private int status;

  public DoctorVisitDto() {
  }

  public DoctorVisitDto(Long id, String visitDate, String visitTime, String description,
      int status) {
    this.id = id;
    this.visitDate = visitDate;
    this.visitTime = visitTime;
    this.description = description;
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getVisitDate() {
    return visitDate;
  }

  public void setVisitDate(String visitDate) {
    this.visitDate = visitDate;
  }

  public String getVisitTime() {
    return visitTime;
  }

  public void setVisitTime(String visitTime) {
    this.visitTime = visitTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }
}
