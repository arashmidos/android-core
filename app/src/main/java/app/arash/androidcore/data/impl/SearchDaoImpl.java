package app.arash.androidcore.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.arash.androidcore.data.dao.SearchDao;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.SearchHistory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 1/16/2018.
 */
public class SearchDaoImpl extends AbstractDao<SearchHistory, Long> implements SearchDao {

  private Context context;

  public SearchDaoImpl(Context context) {
    this.context = context;
  }

  @Override
  protected Context getContext() {
    return context;
  }

  @Override
  protected ContentValues getContentValues(SearchHistory entity) {

    ContentValues contentValues = new ContentValues();
    contentValues.put(SearchHistory.COL_ID, entity.getId());
    contentValues.put(SearchHistory.COL_TYPE, entity.getType());
    contentValues.put(SearchHistory.COL_CONSTRAINT, entity.getConstraint());

    return contentValues;
  }

  @Override
  protected String getTableName() {
    return SearchHistory.TABLE_NAME;
  }

  @Override
  protected String getPrimaryKeyColumnName() {
    return SearchHistory.COL_ID;
  }

  @Override
  protected String[] getProjection() {
    return new String[]{
        SearchHistory.COL_ID,
        SearchHistory.COL_TYPE,
        SearchHistory.COL_CONSTRAINT
    };
  }

  @Override
  protected SearchHistory createEntityFromCursor(Cursor cursor) {
    SearchHistory searchHistory = new SearchHistory();
    searchHistory.setId(cursor.getLong(0));
    searchHistory.setType(cursor.getInt(1));
    searchHistory.setConstraint(cursor.getString(2));

    return searchHistory;
  }

  public List<String> getLatestDrugSearch() {
    String selection = SearchHistory.COL_TYPE + " = ?";
    String[] args = {Constant.SEARCH_DRUG};
    List<SearchHistory> list = retrieveAll(selection, args, SearchHistory.COL_CONSTRAINT, null,
        SearchHistory.COL_ID + " DESC", Constant.MAX_DRUG_HISTORY);
    List<String> searchList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      searchList.add(list.get(i).getConstraint());
    }
    return searchList;
  }

  public List<SearchHistory> getLatestDoctorSearch() {
    String selection = SearchHistory.COL_TYPE + " = ?";
    String[] args = {Constant.SEARCH_DOCTOR};
    return retrieveAll(selection, args, SearchHistory.COL_CONSTRAINT, null,
        SearchHistory.COL_ID + " DESC ", Constant.MAX_DRUG_HISTORY);
  }
}
