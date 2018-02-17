package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import app.arash.androidcore.data.dao.DrugAlarmDao;
import app.arash.androidcore.data.entity.DrugAlarm;
import app.arash.androidcore.data.entity.DrugAlarmModel;
import app.arash.androidcore.data.entity.MedicDatabaseHelper;
import app.arash.androidcore.util.DateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class DrugAlarmDaoImpl extends AbstractDao<DrugAlarm, Long> implements DrugAlarmDao {

  private Context context;

  public DrugAlarmDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(DrugAlarm entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(DrugAlarm.COL_ID, entity.getId());
    contentValues.put(DrugAlarm.COL_DRUG_ID, entity.getDrugId());
    contentValues.put(DrugAlarm.COL_TIMES_IN_DAY, entity.getTimesInDay());
    contentValues.put(DrugAlarm.COL_DAYS, entity.getDays());
    contentValues.put(DrugAlarm.COL_LAST_SERVED, entity.getLastServed());
    contentValues.put(DrugAlarm.COL_INSTRUCTION, entity.getInstruction());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return DrugAlarm.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return DrugAlarm.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        DrugAlarm.COL_ID,
        DrugAlarm.COL_DRUG_ID,
        DrugAlarm.COL_TIMES_IN_DAY,
        DrugAlarm.COL_DAYS,
        DrugAlarm.COL_LAST_SERVED,
        DrugAlarm.COL_INSTRUCTION
    };
  }

  @Override
  protected DrugAlarm createEntityFromCursor(Cursor cursor) {
    DrugAlarm drugAlarm = new DrugAlarm();
    drugAlarm.setId(cursor.getLong(0));
    drugAlarm.setDrugId(cursor.getLong(1));
    drugAlarm.setTimesInDay(cursor.getInt(2));
    drugAlarm.setDays(cursor.getString(3));
    drugAlarm.setLastServed(cursor.getString(4));
    drugAlarm.setInstruction(cursor.getString(5));

    return drugAlarm;
  }

  public DrugAlarm getAlarm(Long drugId) {
    String[] args = {String.valueOf(drugId)};
    String selection = DrugAlarm.COL_DRUG_ID + "=?";
    List<DrugAlarm> list = retrieveAll(selection, args, null, null, null, "1");
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  public List<DrugAlarmModel> getTodayDrugList() {

    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();

    String sql = "SELECT" +
        " d.name_fa," +//0
        " dd.time," +//1
        " dd.number" +//2
        " FROM drug_alarm_detail dd" +
        " INNER JOIN drug_alarm da on dd.alarm_id = da._id" +
        " INNER JOIN drug d on da.drug_id = d._id " +
        " where dd.day = ?";

    int today = DateUtil.getToday();
    if (today == 7) {
      today = 0;
    }
    String[] args = {String.valueOf(today)};
    Cursor cursor = db.rawQuery(sql, args);

    ArrayList<DrugAlarmModel> returnList = new ArrayList<>();

    while (cursor.moveToNext()) {
      returnList
          .add(new DrugAlarmModel(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
    }

    Collections.sort(returnList);
    return returnList;
  }
}
