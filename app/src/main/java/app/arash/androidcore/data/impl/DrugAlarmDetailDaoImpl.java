package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.arash.androidcore.data.dao.DrugAlarmDetailDao;
import app.arash.androidcore.data.entity.DrugAlarmDetail;

/**
 * Created by Arash on 1/16/2018.
 */
public class DrugAlarmDetailDaoImpl extends AbstractDao<DrugAlarmDetail, Long> implements
    DrugAlarmDetailDao {

  private Context context;

  public DrugAlarmDetailDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(DrugAlarmDetail entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(DrugAlarmDetail.COL_ID, entity.getId());
    contentValues.put(DrugAlarmDetail.COL_ALARM_ID, entity.getAlarmId());
    contentValues.put(DrugAlarmDetail.COL_DAY, entity.getDay());
    contentValues.put(DrugAlarmDetail.COL_TIME, entity.getTime());
    contentValues.put(DrugAlarmDetail.COL_NUMBER, entity.getNumber());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return DrugAlarmDetail.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return DrugAlarmDetail.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        DrugAlarmDetail.COL_ID,
        DrugAlarmDetail.COL_ALARM_ID,
        DrugAlarmDetail.COL_DAY,
        DrugAlarmDetail.COL_TIME,
        DrugAlarmDetail.COL_NUMBER
    };
  }

  @Override
  protected DrugAlarmDetail createEntityFromCursor(Cursor cursor) {
    DrugAlarmDetail drugAlarmDetail = new DrugAlarmDetail();
    drugAlarmDetail.setId(cursor.getLong(0));
    drugAlarmDetail.setAlarmId(cursor.getLong(1));
    drugAlarmDetail.setDay(cursor.getInt(2));
    drugAlarmDetail.setTime(cursor.getString(3));
    drugAlarmDetail.setNumber(cursor.getString(4));

    return drugAlarmDetail;
  }
}
