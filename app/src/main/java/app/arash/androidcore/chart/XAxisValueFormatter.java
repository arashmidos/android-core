package app.arash.androidcore.chart;

import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Arash
 */
public class XAxisValueFormatter implements IAxisValueFormatter {

  private final List<Measure> list;
  private DecimalFormat mFormat;

  public XAxisValueFormatter(List<Measure> list) {
    this.list = list;
  }

  @Override
  public String getFormattedValue(float value, AxisBase axis) {
    try {
      String description = list.get((int) value).getTimestamp();
      Date date = DateUtil
          .convertStringToDate(description, DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME, "EN");
      String persianDate = DateUtil.getChartDate(date);
      return NumberUtil.digitsToPersian(persianDate);
    } catch (Exception ignore) {
      return "";
    }
  }
}
