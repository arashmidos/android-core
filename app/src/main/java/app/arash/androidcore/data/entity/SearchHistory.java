package app.arash.androidcore.data.entity;

/**
 * Created by arash on 1/28/18.
 */

public class SearchHistory extends BaseEntity<Long> {

  public static final String TABLE_NAME = "search";

  public static final String COL_ID = "_id";
  public static final String COL_TYPE = "type";
  public static final String COL_CONSTRAINT = "search_constraint";

  private Long id;
  private int type;
  private String constraint;

  public SearchHistory(int type, String nameFa) {
    this.type = type;
    this.constraint = nameFa;
  }

  public SearchHistory() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getConstraint() {
    return constraint;
  }

  public void setConstraint(String constraint) {
    this.constraint = constraint;
  }

  @Override
  public Long getPrimaryKey() {
    return id;
  }
}
