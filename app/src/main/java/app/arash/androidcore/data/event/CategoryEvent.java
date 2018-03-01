package app.arash.androidcore.data.event;

import app.arash.androidcore.data.entity.Category;
import java.util.List;

/**
 * Created by arash on 3/1/18.
 */

public class CategoryEvent {

  private List<Category> categoryList;

  public CategoryEvent(List<Category> categoryList) {
    this.categoryList = categoryList;
  }

  public List<Category> getCategoryList() {
    return categoryList;
  }

  public void setCategoryList(List<Category> categoryList) {
    this.categoryList = categoryList;
  }
}
