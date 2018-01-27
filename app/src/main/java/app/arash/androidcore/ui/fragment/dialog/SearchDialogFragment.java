package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.adapter.DrugCategoryAdapter;
import app.arash.androidcore.ui.adapter.DrugSearchAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class SearchDialogFragment extends DialogFragment {

  @BindView(R.id.search_img)
  ImageView searchImg;
  @BindView(R.id.search_edt)
  EditText searchEdt;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private Unbinder unbinder;
  private Context context;

  public static SearchDialogFragment newInstance(Context context) {
    SearchDialogFragment fragment = new SearchDialogFragment();
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
    View view = inflater.inflate(R.layout.fragment_dialog_search, container, false);
    unbinder = ButterKnife.bind(this, view);
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {
    DrugSearchAdapter drugSearchAdapter = new DrugSearchAdapter(context, Drug.getDrugList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugSearchAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.close_img)
  public void onViewClicked() {
    getDialog().dismiss();
  }
}
