package app.arash.androidcore.data.event;

import java.util.List;
import java.util.Locale.Category;

/**
 * Created by arash on 3/1/18.
 */

public class CategoryEvent {

  private final List<Category> categoryList;

  public CategoryEvent(List<Category> categoryList) {
    this.categoryList = categoryList;
  }
}
