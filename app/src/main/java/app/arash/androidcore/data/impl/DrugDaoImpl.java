package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import app.arash.androidcore.data.dao.DrugDao;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.data.entity.MedicDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class DrugDaoImpl extends AbstractDao<Drug, Long> implements DrugDao {

  private Context context;

  public DrugDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(Drug entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(Drug.COL_ID, entity.getId());
    contentValues.put(Drug.COL_CATEGORY_FA, entity.getCategoryFa());
    contentValues.put(Drug.COL_CATEGORY_EN, entity.getCategoryEn());
    contentValues.put(Drug.COL_NAME_FA, entity.getNameFa());
    contentValues.put(Drug.COL_NAME_EN, entity.getNameEn());
    contentValues.put(Drug.COL_USAGE, entity.getUsage());//5
    contentValues.put(Drug.COL_MECHANISM, entity.getMechanism());
    contentValues.put(Drug.COL_FARM, entity.getFarm());
    contentValues.put(Drug.COL_NOT_USAGE, entity.getNotUsage());
    contentValues.put(Drug.COL_SIDE_EFFECT, entity.getSideEffect());
    contentValues.put(Drug.COL_CONFLICT, entity.getDrugConflict());//10
    contentValues.put(Drug.COL_CAUTIONS, entity.getCautions());
    contentValues.put(Drug.COL_TOSIE, entity.getInstruction());
    contentValues.put(Drug.COL_ASHKAL, entity.getAshkal());
    contentValues.put(Drug.COL_STAR, entity.isStared() ? 1 : 0);
    contentValues.put(Drug.COL_ALARM, entity.isHasAlarmSet() ? 1 : 0);//15
    contentValues.put(Drug.COL_MY, entity.isMyDrug() ? 1 : 0);//16

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return Drug.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return Drug.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        Drug.COL_ID,
        Drug.COL_CATEGORY_FA,
        Drug.COL_CATEGORY_EN,
        Drug.COL_NAME_FA,
        Drug.COL_NAME_EN,
        Drug.COL_USAGE,//5
        Drug.COL_MECHANISM,
        Drug.COL_FARM,
        Drug.COL_NOT_USAGE,
        Drug.COL_SIDE_EFFECT,
        Drug.COL_CONFLICT,//10
        Drug.COL_CAUTIONS,
        Drug.COL_TOSIE,
        Drug.COL_ASHKAL,
        Drug.COL_STAR,
        Drug.COL_ALARM, //15
        Drug.COL_MY //16
    };
  }

  @Override
  protected Drug createEntityFromCursor(Cursor cursor) {
    Drug drug = new Drug();
    drug.setId(cursor.getLong(0));
    drug.setCategoryFa(cursor.getString(1));
    drug.setCategoryEn(cursor.getString(2));
    drug.setNameFa(cursor.getString(3));
    drug.setNameEn(cursor.getString(4));
    drug.setUsage(cursor.getString(5));
    drug.setMechanism(cursor.getString(6));
    drug.setFarm(cursor.getString(7));
    drug.setNotUsage(cursor.getString(8));
    drug.setSideEffect(cursor.getString(9));
    drug.setDrugConflict(cursor.getString(10));
    drug.setCautions(cursor.getString(11));
    drug.setInstruction(cursor.getString(12));
    drug.setAshkal(cursor.getString(13));
    drug.setStared(cursor.getInt(14) == 1);
    drug.setHasAlarmSet(cursor.getInt(15) == 1);
    drug.setMyDrug(cursor.getInt(16) == 1);

    return drug;
  }

  public List<String> getAllCategory() {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();

    String[] projections = {Drug.COL_CATEGORY_FA};
    Cursor cursor = db
        .query(getTableName(), projections, null, null, Drug.COL_CATEGORY_FA, null, null);
    List<String> list = new ArrayList<>();

    while (cursor.moveToNext()) {
      list.add(cursor.getString(0));
    }
    cursor.close();

    return list;
  }

  public List<Drug> getAllFavourites() {
    String[] args = {"1"};
    String selection = Drug.COL_STAR + " = ? ";
    return retrieveAll(selection, args, null, null, null);
  }

  public List<Drug> getAllMyDrug() {
    String[] args = {"1"};
    String selection = Drug.COL_MY + " = ? ";
    return retrieveAll(selection, args, null, null, null);
  }

  public List<Drug> getAllDrugsByCategory(String category) {
    String[] args = {category};
    String selection = Drug.COL_CATEGORY_FA + " = ? ";
    return retrieveAll(selection, args, null, null, null);
  }

  public Drug retriveByName(String drugName) {
    String[] args = {drugName};
    String selection = Drug.COL_NAME_FA + " = ? ";
    List<Drug> drugList = retrieveAll(selection, args, null, null, null);
    if (drugList.size() > 0) {
      return drugList.get(0);
    }
    return null;
  }

  public List<String> searchByName(String constraint) {
    String[] args = {"%" + constraint + "%", "%" + constraint + "%"};
    String selection = Drug.COL_NAME_FA + " LIKE ? OR " + Drug.COL_NAME_EN + " LIKE ? ";
    List<Drug> list = retrieveAll(selection, args, null, null, null);
    ArrayList<String> drugList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      drugList.add(list.get(i).getNameFa());
    }
    return drugList;
  }
}
