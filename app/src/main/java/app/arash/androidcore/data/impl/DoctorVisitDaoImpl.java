package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.arash.androidcore.data.dao.DoctorVisitDao;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.data.entity.DoctorVisitDto;
import app.arash.androidcore.util.DateUtil;
import java.util.Date;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class DoctorVisitDaoImpl extends AbstractDao<DoctorVisit, Long> implements DoctorVisitDao {

  private Context context;

  public DoctorVisitDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(DoctorVisit entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(DoctorVisit.COL_ID, entity.getId());
    contentValues.put(DoctorVisit.COL_VISIT_DATE, entity.getVisitDate());
    contentValues.put(DoctorVisit.COL_VISIT_TIME, entity.getVisitTime());
    contentValues.put(DoctorVisit.COL_DESCRIPTION, entity.getDescription());
    contentValues.put(DoctorVisit.COL_DOCTOR_ID, entity.getDoctorId());
    contentValues.put(DoctorVisit.COL_STATUS, entity.getStatus());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return DoctorVisit.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return DoctorVisit.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        DoctorVisit.COL_ID,
        DoctorVisit.COL_VISIT_DATE,
        DoctorVisit.COL_VISIT_TIME,
        DoctorVisit.COL_DESCRIPTION,
        DoctorVisit.COL_DOCTOR_ID,
        DoctorVisit.COL_STATUS
    };
  }

  @Override
  protected DoctorVisit createEntityFromCursor(Cursor cursor) {

    DoctorVisit doctorVisit = new DoctorVisit();
    doctorVisit.setId(cursor.getLong(0));
    doctorVisit.setVisitDate(cursor.getString(1));
    doctorVisit.setVisitTime(cursor.getString(2));
    doctorVisit.setDescription(cursor.getString(3));
    doctorVisit.setDoctorId(cursor.getLong(4));
    doctorVisit.setStatus(cursor.getInt(5));

    return doctorVisit;
  }

  public List<DoctorVisit> getAllVisitByDoctorId(long doctorId) {
    String[] args = {String.valueOf(doctorId)};
    String selection = DoctorVisit.COL_DOCTOR_ID + " =? ";
    return retrieveAll(selection, args, null, null, DoctorVisit.COL_VISIT_DATE);
  }

  public List<DoctorVisit> retrieveAllByDate() {

    String selection = DoctorVisit.COL_VISIT_DATE + " > ? ";

    Date date = new Date();
    date = DateUtil.startOfDay(date);
    String select = DateUtil.convertDate(date, DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");

    String[] args = {select};
    return retrieveAll(selection, args, null, null, DoctorVisit.COL_VISIT_DATE);
  }

  public DoctorVisitDto getLatestDoctorVisit() {

    String selection = DoctorVisit.COL_VISIT_DATE + " > ? AND " + DoctorVisit.COL_STATUS + " != ?";

    Date date = new Date();
    date = DateUtil.startOfDay(date);
    String select = DateUtil.convertDate(date, DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");

    String[] args = {select, String.valueOf(Constant.STATUS_ALARM_RINGED)};
    List<DoctorVisit> list = retrieveAll(selection, args, null, null, DoctorVisit.COL_VISIT_DATE,
        "1");
    DoctorVisitDto doctorVisitDto = null;
    if (list.size() > 0) {
      DoctorVisit visit = list.get(0);
      doctorVisitDto = new DoctorVisitDto(visit.getId(), visit.getVisitDate(), visit.getVisitTime(),
          visit.getDescription(), visit.getStatus());
      DoctorDaoImpl doctorDao = new DoctorDaoImpl(context);
      Doctor doctor = doctorDao.retrieve(visit.getDoctorId());
      doctorVisitDto.setDoctor(doctor);
    }

    return doctorVisitDto;
  }

  public void changeVisitStatus(Long visitId, int status) {
    update(DoctorVisit.COL_ID + " = ? ", new String[]{String.valueOf(visitId)},
        DoctorVisit.COL_STATUS, String.valueOf(status));
  }

  public DoctorVisitDto getVisitDto(long id) {
    DoctorVisit visit = retrieve(id);
    DoctorVisitDto doctorVisitDto = new DoctorVisitDto(visit.getId(), visit.getVisitDate(),
        visit.getVisitTime(), visit.getDescription(), visit.getStatus());
    DoctorDaoImpl doctorDao = new DoctorDaoImpl(context);
    Doctor doctor = doctorDao.retrieve(visit.getDoctorId());
    doctorVisitDto.setDoctor(doctor);

    return doctorVisitDto;
  }
}
