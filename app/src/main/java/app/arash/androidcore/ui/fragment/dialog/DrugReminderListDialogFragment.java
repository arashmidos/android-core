package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.DrugAlarmModel;
import app.arash.androidcore.data.entity.MeasureDetailType;
import app.arash.androidcore.data.impl.DrugAlarmDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.MedicineAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.List;

/**
 * Created by Arash on 1/24/18.
 */

public class DrugReminderListDialogFragment extends DialogFragment {

  @BindView(R.id.root)
  LinearLayout root;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.list)
  RecyclerView list;

  private Unbinder unbinder;
  private MainActivity context;
  private MedicineAdapter adapter;

  public static DrugReminderListDialogFragment newInstance(MainActivity context) {
    DrugReminderListDialogFragment fragment = new DrugReminderListDialogFragment();
    fragment.context = context;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.myDialog);
    setRetainInstance(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dialog_measure_list, container, false);
    unbinder = ButterKnife.bind(this, view);

    setData();
    setupRecyclerView();
    return view;
  }

  private void setupRecyclerView() {

    DrugAlarmDaoImpl drugAlarmDao = new DrugAlarmDaoImpl(context);
    List<DrugAlarmModel> alarms = drugAlarmDao.getTodayDrugList();
    adapter = new MedicineAdapter(context, alarms);
    list.setLayoutManager(new LinearLayoutManager(context));
    list.setAdapter(adapter);
  }

  private void setData() {
    title.setText("یادآور دارو");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.close_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.done_img:
      case R.id.close_img:
        getDialog().dismiss();
        break;
    }
  }

  public interface OnNewMeasureSelected {

    void addNewMeasure(MeasureDetailType type);
  }
}
