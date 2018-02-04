package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.arash.androidcore.R;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.data.impl.DrugDaoImpl;
import app.arash.androidcore.data.impl.SearchDaoImpl;
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
  @BindView(R.id.no_item_lay)
  LinearLayout noItemLayout;

  private Unbinder unbinder;
  private Context context;
  private DrugSearchAdapter drugSearchAdapter;
  private SearchDaoImpl searchDaoImpl;
  private String constraint;
  private boolean isDrug;
  private DrugDaoImpl drugDaoImpl;
  private DoctorDaoImpl doctorDaoImpl;

  public static SearchDialogFragment newInstance(Context context, boolean isDrug) {
    SearchDialogFragment fragment = new SearchDialogFragment();
    fragment.context = context;
    fragment.isDrug = isDrug;
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
    View view = inflater.inflate(R.layout.fragment_dialog_search_drugs, container, false);
    unbinder = ButterKnife.bind(this, view);
    searchDaoImpl = new SearchDaoImpl(context);
    drugDaoImpl = new DrugDaoImpl(context);
    doctorDaoImpl = new DoctorDaoImpl(context);

    searchEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        constraint = editable.toString();
        if (!constraint.isEmpty()) {
          getSearchList();
        } else {
          drugSearchAdapter.update(getSearchHistoryList(), true);
        }
      }
    });
    setUpRecyclerView();
    return view;
  }

  private void getSearchList() {
    List<String> searchList = new ArrayList<>();
    if (isDrug) {
      searchList = drugDaoImpl.searchByName(constraint);
    }else{
//      searchList = doctorDaoImpl.searchByName(constraint);
    }
    if (searchList.size() > 0) {
      recyclerView.setVisibility(View.VISIBLE);
      noItemLayout.setVisibility(View.GONE);
      drugSearchAdapter.update(searchList, false);
    } else {
      recyclerView.setVisibility(View.GONE);
      noItemLayout.setVisibility(View.VISIBLE);
    }

  }

  private void setUpRecyclerView() {
    drugSearchAdapter = new DrugSearchAdapter(context, getSearchHistoryList());
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(drugSearchAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private List<String> getSearchHistoryList() {
    return searchDaoImpl.getLatestDrugSearch();
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
