package app.arash.androidcore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.chart.MeasureValueFormatter;
import app.arash.androidcore.chart.XAxisValueFormatter;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Measure;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.data.impl.MeasureDaoImpl;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChartDetailActivity extends AppCompatActivity {

  @BindView(R.id.latest_measure_title)
  TextView latestMeasureTitle;
  @BindView(R.id.latest_measure_value)
  TextView latestMeasureValue;
  @BindView(R.id.measure_label)
  TextView measureLabel;
  @BindView(R.id.chart)
  LineChart chart;
  @BindView(R.id.root)
  LinearLayout root;
  @BindView(R.id.title)
  TextView title;
  private Measure measure;
  private MeasureDetailType type;
  private MeasureDaoImpl measureDaoImpl;
  private List<Measure> list;
  private int max = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart_detail);
    ButterKnife.bind(this);
    measureDaoImpl = new MeasureDaoImpl(this);
    Intent intent = getIntent();
    if (intent != null) {
      measure = (Measure) intent.getSerializableExtra(Constant.MEASURE);
      setData();
      setChart();
    }
  }

  private void setData() {
    type = MeasureDetailType.getByTypeId(measure.getType());
    String title = getString(type.getType());
    this.title.setText(title);
    latestMeasureTitle.setText(String.format(Locale.US, "آخرین %s ثبت شده", title));
    latestMeasureValue
        .setText(String.format(Locale.US, "%s %s", NumberUtil.digitsToPersian(measure.getValue()),
            getString(type.getUnit())));
  }

  private void setChart() {
    list = measureDaoImpl.retriveAllByType(measure.getType());
    Collections.reverse(list);

    for (int i = 0; i < list.size(); i++) {

      int value = list.get(i).getValue();
      if (value >= max) {
        max = (int) (value + value * 0.4);
      }
    }
//    chart.setOnChartValueSelectedListener(this);

    // no description text
    chart.getDescription().setEnabled(false);

    // enable touch gestures
    chart.setTouchEnabled(true);

    chart.setDragDecelerationFrictionCoef(0.9f);

    // enable scaling and dragging
    chart.setDragEnabled(true);
    chart.setScaleEnabled(true);
    chart.setDrawGridBackground(false);
    chart.setHighlightPerDragEnabled(true);

    // if disabled, scaling can be done on x- and y-axis separately
    chart.setPinchZoom(true);

    // set an alternative background color
    chart.setBackgroundColor(Color.WHITE);

    // add data
    setChartData();

    chart.animateX(2500);

    // get the legend (only possible after setting data)
    Legend l = chart.getLegend();

    // modify the legend ...
    l.setForm(LegendForm.LINE);
//    l.setTypeface(mTfLight);
    l.setTextSize(11f);
    l.setTextColor(Color.WHITE);
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    l.setDrawInside(false);
//        l.setYOffset(11f);

    IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(list);

    XAxis xAxis = chart.getXAxis();
//    xAxis.setTypeface(mTfLight);
    xAxis.setTextSize(11f);
    xAxis.setTextColor(Color.WHITE);
    xAxis.setDrawGridLines(false);
    xAxis.setDrawAxisLine(false);
    xAxis.setValueFormatter(xAxisFormatter);
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setTextColor(Color.GRAY);
    xAxis.setLabelRotationAngle(45);

    YAxis leftAxis = chart.getAxisLeft();
//    leftAxis.setTypeface(mTfLight);
    leftAxis.setTextColor(Color.GRAY);
    leftAxis.setAxisMaximum(max);
    leftAxis.setAxisMinimum(0f);
    leftAxis.setDrawGridLines(true);
    leftAxis.setGranularityEnabled(true);

    YAxis rightAxis = chart.getAxisRight();
//    rightAxis.setTypeface(mTfLight);
    rightAxis.setTextColor(Color.GRAY);
    rightAxis.setAxisMaximum(max);
    rightAxis.setAxisMinimum(0);
    rightAxis.setDrawGridLines(false);
    rightAxis.setDrawZeroLine(false);
    rightAxis.setGranularityEnabled(false);
  }

  private void setChartData() {
    ArrayList<Entry> values = new ArrayList<>();

    for (int i = 0; i < list.size(); i++) {
      values.add(new Entry(i, list.get(i).getValue()));
    }

    LineDataSet set1;

    if (chart.getData() != null &&
        chart.getData().getDataSetCount() > 0) {
      set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
      set1.setValues(values);
      chart.getData().notifyDataChanged();
      chart.notifyDataSetChanged();
    } else {
      // create a dataset and give it a type
      set1 = new LineDataSet(values, "");

      set1.setDrawIcons(false);

      // set the line to be drawn like this "- - - - - -"
//      set1.enableDashedLine(10f, 5f, 0f);
//      set1.enableDashedHighlightLine(10f, 5f, 0f);
      set1.setColor(Color.rgb(0, 131, 143));
      set1.setCircleColor(Color.rgb(0, 131, 143));
      set1.setLineWidth(1f);
      set1.setCircleRadius(1f);
      set1.setDrawCircleHole(false);
      set1.setValueTextSize(9f);
      set1.setDrawFilled(false);
      set1.setFormLineWidth(1f);
//      set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
      set1.setFormSize(15.f);

      set1.setFillColor(Color.BLACK);
      set1.setValueFormatter(new MeasureValueFormatter());
      ArrayList<ILineDataSet> dataSets = new ArrayList<>();
      dataSets.add(set1); // add the datasets

      // create a data object with the datasets
      LineData data = new LineData(dataSets);

      // set data
      chart.setData(data);
    }
  }


  @OnClick(R.id.back_img)
  public void onViewClicked() {
    onBackPressed();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

}
