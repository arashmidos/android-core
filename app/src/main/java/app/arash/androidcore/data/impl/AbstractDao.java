package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;
import app.arash.androidcore.data.entity.BaseEntity;
import app.arash.androidcore.data.entity.MedicDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 2018-01-27
 */
public abstract class AbstractDao<T extends BaseEntity, PK extends Long> {

  protected static SQLiteDatabase writableDb;

  public PK create(T entity) {
    try {
      MedicDatabaseHelper databaseHelper = new MedicDatabaseHelper(getContext());
      SQLiteDatabase db = databaseHelper.getWritableDatabase();
      db.beginTransaction();
      Long id = db.insert(getTableName(), null, getContentValues(entity));
      db.setTransactionSuccessful();
      db.endTransaction();
      return (PK) id;
    } catch (SQLiteDatabaseLockedException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public void bulkInsert(List<T> list) {
    MedicDatabaseHelper databaseHelper = new MedicDatabaseHelper(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    try {
      for (T entity : list) {
        db.insert(getTableName(), null, getContentValues(entity));
      }
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  public T retrieve(PK id) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String selection = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(id)};
    Cursor cursor = db.query(getTableName(), getProjection(), selection, args, null, null, null);
    T entity = null;
    if (cursor.moveToFirst()) {
      entity = createEntityFromCursor(cursor);
    }
    cursor.close();
    return entity;
  }

  public void update(T entity) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    String whereClause = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(entity.getPrimaryKey())};
    db.update(getTableName(), getContentValues(entity), whereClause, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void update(String where, String[] args, String updateColumn, String newValue) {
    SQLiteDatabase db = MedicDatabaseHelper.getInstance(getContext()).openDataBase();
    db.beginTransaction();
    ContentValues contentValues = new ContentValues();
    contentValues.put(updateColumn, newValue);
    int rows = db.update(getTableName(), contentValues, where, args);
    Log.d(getTableName(), ": row updated " + rows);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void delete(PK id) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    String whereClause = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(id)};
    db.delete(getTableName(), whereClause, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public List<T> retrieveAll() {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db.query(getTableName(), getProjection(), null, null, null, null, null);
    List<T> entities = new ArrayList<T>();
    while (cursor.moveToNext()) {
      entities.add(createEntityFromCursor(cursor));
    }
    cursor.close();
    return entities;
  }

  public List<T> retrieveAll(String selection, String[] args, String groupBy, String having,
      String orderBy) {
    return retrieveAll(selection, args, groupBy, having, orderBy, null);
  }

  public List<T> retrieveAll(String selection, String[] args, String groupBy, String having,
      String orderBy, String limit) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db
        .query(getTableName(), getProjection(), selection, args, groupBy, having, orderBy, limit);
    List<T> entities = new ArrayList<T>();
    while (cursor.moveToNext()) {
      entities.add(createEntityFromCursor(cursor));
    }
    cursor.close();
    return entities;
  }

  public void deleteAll() {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    db.execSQL("DELETE FROM " + getTableName());
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void deleteAll(String column, String condition) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    db.execSQL(String.format("DELETE FROM %s WHERE %s = %s", getTableName(), column, condition));
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void delete(String selection, String[] args) {
    MedicDatabaseHelper databaseHelper = MedicDatabaseHelper.getInstance(getContext());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    db.delete(getTableName(), selection, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  protected abstract Context getContext();

  protected abstract ContentValues getContentValues(T entity);

  protected abstract String getTableName();

  protected abstract String getPrimaryKeyColumnName();

  protected abstract String[] getProjection();

  protected abstract T createEntityFromCursor(Cursor cursor);
}
