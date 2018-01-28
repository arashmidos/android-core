package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import app.arash.androidcore.data.dao.DoctorDao;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.MedicDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class DoctorDaoImpl extends AbstractDao<Doctor, Long> implements DoctorDao {

  private Context context;

  public DoctorDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(Doctor entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(Doctor.COL_ID, entity.getId());
    contentValues.put(Doctor.COL_NAME, entity.getName());
    contentValues.put(Doctor.COL_EXPERTISE, entity.getExpertise());
    contentValues.put(Doctor.COL_PHONE, entity.getPhone());
    contentValues.put(Doctor.COL_ADDRESS, entity.getAddress());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return Doctor.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return Doctor.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        Doctor.COL_ID,
        Doctor.COL_NAME,
        Doctor.COL_EXPERTISE,
        Doctor.COL_PHONE,
        Doctor.COL_ADDRESS
    };
  }

  @Override
  protected Doctor createEntityFromCursor(Cursor cursor) {
    Doctor doctor = new Doctor();
    doctor.setId(cursor.getLong(0));
    doctor.setName(cursor.getString(1));
    doctor.setExpertise(cursor.getString(2));
    doctor.setPhone(cursor.getString(3));
    doctor.setAddress(cursor.getString(4));

    return doctor;
  }

  public List<Doctor> searchByName(String constraint) {
    String[] args = {constraint};
    String selection = Doctor.COL_NAME + " =? ";
    return retrieveAll(selection, args, null, null, null);
  }
}
