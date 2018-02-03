package app.arash.androidcore.data.entity;

import java.io.Serializable;

/**
 * Created by arash on 1/28/18.
 */

public class DoctorVisit extends BaseEntity<Long> implements Serializable {

  public static final String TABLE_NAME = "doctor_visit";

  public static final String COL_ID = "_id";
  public static final String COL_VISIT_DATE = "visit_date";
  public static final String COL_VISIT_TIME = "visit_time";
  public static final String COL_DESCRIPTION = "description";
  public static final String COL_DOCTOR_ID = "doctor_id";
  private Long id;
  private String visitDate;
  private String visitTime;
  private String description;
  private Long doctorId;
  public DoctorVisit(String visitDate, String visitTime, String description, Long doctorId) {
    this.visitDate = visitDate;
    this.visitTime = visitTime;
    this.description = description;
    this.doctorId = doctorId;
  }

  public DoctorVisit(Long id, String visitDate, String visitTime, String description,
      Long doctorId) {
    this(visitDate, visitTime, description, doctorId);
    this.id = id;
  }

  public DoctorVisit() {
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

  public Long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(Long doctorId) {
    this.doctorId = doctorId;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }
}
