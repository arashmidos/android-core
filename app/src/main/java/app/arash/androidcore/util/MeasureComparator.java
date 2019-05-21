package app.arash.androidcore.util;

import app.arash.androidcore.data.entity.Measure;
import java.util.Comparator;

/**
 * Created by arash on 2/9/18.
 */

public class MeasureComparator implements Comparator<Measure> {

  @Override
  public int compare(Measure measure, Measure t1) {
    if (measure == null || t1 == null) {
      return 1;
    }
    return Integer.compare(measure.getValue(), t1.getValue());
  }
}
