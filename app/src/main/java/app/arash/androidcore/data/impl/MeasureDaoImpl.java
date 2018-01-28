package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import app.arash.androidcore.data.dao.DoctorDao;
import app.arash.androidcore.data.dao.MeasureDao;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.data.entity.MedicDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class MeasureDaoImpl extends AbstractDao<Measure, Long> implements MeasureDao {

  private Context context;

  public MeasureDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(Measure entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(Measure.COL_ID, entity.getId());
    contentValues.put(Measure.COL_TYPE, entity.getType());
    contentValues.put(Measure.COL_VALUE, entity.getValue());
    contentValues.put(Measure.COL_TIMESTAMP, entity.getTimestamp());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return Measure.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return Measure.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        Measure.COL_ID,
        Measure.COL_TYPE,
        Measure.COL_VALUE,
        Measure.COL_TIMESTAMP
    };
  }

  @Override
  protected Measure createEntityFromCursor(Cursor cursor) {
    Measure measure = new Measure();
    measure.setId(cursor.getLong(0));
    measure.setType(cursor.getInt(1));
    measure.setValue(cursor.getInt(2));
    measure.setTimestamp(cursor.getString(3));

    return measure;
  }
}
