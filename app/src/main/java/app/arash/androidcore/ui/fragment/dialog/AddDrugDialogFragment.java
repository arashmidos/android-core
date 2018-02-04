package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import app.arash.androidcore.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arash on 1/24/18.
 */

public class AddDrugDialogFragment extends DialogFragment {


  @BindView(R.id.spinner)
  Spinner spinner;
  private AppCompatActivity context;

  public static AddDrugDialogFragment newInstance(AppCompatActivity context) {
    AddDrugDialogFragment fragment = new AddDrugDialogFragment();
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
    View view = inflater.inflate(R.layout.fragment_dialog_add_drug, container, false);
    ButterKnife.bind(this, view);
    setData();
    return view;
  }

  private void setData() {
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, getNumberOfUse());
    spinner.setAdapter(adapter);
  }

  private List<String> getNumberOfUse() {
    List<String> item = new ArrayList<>();
    item.add("۱ بار در روز");
    item.add("۲ بار در روز");
    item.add("۳ بار در روز");
    item.add("۴ بار در روز");
    item.add("۵ بار در روز");
    return item;
  }

}
