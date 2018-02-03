package app.arash.androidcore.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class ChartDetailActivity extends AppCompatActivity {

  @BindView(R.id.latest_measure_title)
  TextView latestMeasureTitle;
  @BindView(R.id.measure_label)
  TextView measureLabel;
  @BindView(R.id.chart)
  LineChart chart;
  @BindView(R.id.root)
  LinearLayout root;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart_detail);
    ButterKnife.bind(this);

    setData();
    setChart();
  }

  private void setData() {

  }

  private void setChart() {
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
    chart.setBackgroundColor(Color.LTGRAY);

    // add data
    setData(20, 30);

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

    XAxis xAxis = chart.getXAxis();
//    xAxis.setTypeface(mTfLight);
    xAxis.setTextSize(11f);
    xAxis.setTextColor(Color.WHITE);
    xAxis.setDrawGridLines(false);
    xAxis.setDrawAxisLine(false);

    YAxis leftAxis = chart.getAxisLeft();
//    leftAxis.setTypeface(mTfLight);
    leftAxis.setTextColor(ColorTemplate.getHoloBlue());
    leftAxis.setAxisMaximum(200f);
    leftAxis.setAxisMinimum(0f);
    leftAxis.setDrawGridLines(true);
    leftAxis.setGranularityEnabled(true);

    YAxis rightAxis = chart.getAxisRight();
//    rightAxis.setTypeface(mTfLight);
    rightAxis.setTextColor(Color.RED);
    rightAxis.setAxisMaximum(900);
    rightAxis.setAxisMinimum(-200);
    rightAxis.setDrawGridLines(false);
    rightAxis.setDrawZeroLine(false);
    rightAxis.setGranularityEnabled(false);
  }

  private void setData(int count, float range) {

    ArrayList<Entry> yVals1 = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      float mult = range / 2f;
      float val = (float) (Math.random() * mult) + 50;
      yVals1.add(new Entry(i, val));
    }

    ArrayList<Entry> yVals2 = new ArrayList<>();

    for (int i = 0; i < count - 1; i++) {
      float mult = range;
      float val = (float) (Math.random() * mult) + 450;
      yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
    }

    ArrayList<Entry> yVals3 = new ArrayList<Entry>();

    for (int i = 0; i < count; i++) {
      float mult = range;
      float val = (float) (Math.random() * mult) + 500;
      yVals3.add(new Entry(i, val));
    }

    LineDataSet set1, set2, set3;

    if (chart.getData() != null &&
        chart.getData().getDataSetCount() > 0) {
      set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
      set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
      set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
      set1.setValues(yVals1);
      set2.setValues(yVals2);
      set3.setValues(yVals3);
      chart.getData().notifyDataChanged();
      chart.notifyDataSetChanged();
    } else {
      // create a dataset and give it a type
      set1 = new LineDataSet(yVals1, "DataSet 1");

      set1.setAxisDependency(AxisDependency.LEFT);
      set1.setColor(ColorTemplate.getHoloBlue());
      set1.setCircleColor(Color.WHITE);
      set1.setLineWidth(2f);
      set1.setCircleRadius(3f);
      set1.setFillAlpha(65);
      set1.setFillColor(ColorTemplate.getHoloBlue());
      set1.setHighLightColor(Color.rgb(244, 117, 117));
      set1.setDrawCircleHole(false);
      //set1.setFillFormatter(new MyFillFormatter(0f));
      //set1.setDrawHorizontalHighlightIndicator(false);
      //set1.setVisible(false);
      //set1.setCircleHoleColor(Color.WHITE);

      // create a dataset and give it a type


      // create a data object with the datasets
      LineData data = new LineData(set1);
      data.setValueTextColor(Color.WHITE);
      data.setValueTextSize(9f);

      // set data
      chart.setData(data);
    }
  }


  @OnClick(R.id.back_img)
  public void onViewClicked() {
    onBackPressed();
  }

}
