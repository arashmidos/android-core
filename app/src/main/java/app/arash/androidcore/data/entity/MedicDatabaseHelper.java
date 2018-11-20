package app.arash.androidcore.data.entity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Arashmidos on 2018-01-21
 */
public class MedicDatabaseHelper extends SQLiteOpenHelper {

  public static final String TAG = MedicDatabaseHelper.class.getSimpleName();
  private static final String DATABASE_NAME = "medic.db";
  private static final Integer DATABASE_VERSION = 2;
  private static final String SQL_ADD_COLUMN = "ALTER TABLE %s ADD COLUMN %s %s ";
  private static String DATABASE_PATH = "/data/data/ir.rasaaa.myclinic/databases/";
  private static MedicDatabaseHelper sInstance;
  private final Context context;
  private SQLiteDatabase myDataBase;

  public MedicDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
    openDataBase();
  }

  public static synchronized MedicDatabaseHelper getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new MedicDatabaseHelper(context.getApplicationContext());
    }
    return sInstance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

  public void createDataBase() {

    boolean dbExist = checkDataBase();

    if (!dbExist) {

      //By calling this method and empty database will be created into the default system path
      //of your application so we are gonna be able to overwrite that database with our database.
      this.getReadableDatabase();

      try {
        copyDataBase();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean checkDataBase() {

    SQLiteDatabase checkDB = null;

    try {
      String myPath = DATABASE_PATH + DATABASE_NAME;
      checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    } catch (SQLiteException e) {
      Log.i(TAG, "Database not exist. Copying...");
    }

    if (checkDB != null) {
      checkDB.close();
    }

    return checkDB != null;
  }

  private void copyDataBase() throws IOException {

    //Open your local db as the input stream
    InputStream myInput = context.getAssets().open("databases/"+DATABASE_NAME);

    // Path to the just created empty db
    String outFileName = DATABASE_PATH + DATABASE_NAME;

    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);

    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer)) > 0) {
      myOutput.write(buffer, 0, length);
    }

    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
  }

  public SQLiteDatabase openDataBase() throws SQLException{

    if (myDataBase == null) {
      //Open the database
      createDataBase();
      String myPath = DATABASE_PATH + DATABASE_NAME;
      myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    return myDataBase;
  }

  @Override
  public synchronized void close() {

    if (myDataBase != null) {
      myDataBase.close();
    }

    super.close();
  }
}
