package app.arash.androidcore.ui.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.ui.activity.NewVisitActivity;
import app.arash.androidcore.ui.adapter.DoctorSearchAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.List;

/**
 * Created by shkbhbb on 1/24/18.
 */

public class DoctorSearchDialogFragment extends DialogFragment {

  @BindView(R.id.search_img)
  ImageView searchImg;
  @BindView(R.id.search_edt)
  EditText searchEdt;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.no_item_lay)
  LinearLayout noItemLayout;

  private Unbinder unbinder;
  private NewVisitActivity newVisitActivity;
  private DoctorSearchAdapter doctorSearchAdapter;
  private String constraint;
  private DoctorDaoImpl doctorDaoImpl;

  public static DoctorSearchDialogFragment newInstance(NewVisitActivity newVisitActivity) {
    DoctorSearchDialogFragment fragment = new DoctorSearchDialogFragment();
    fragment.newVisitActivity = newVisitActivity;
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
    doctorDaoImpl = new DoctorDaoImpl(newVisitActivity);
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
        if (!TextUtils.isEmpty(constraint.trim())) {
          getSearchList();
        } else {
          if (doctorSearchAdapter != null) {
            doctorSearchAdapter.update(doctorDaoImpl.retrieveAll());
          }
        }
      }
    });
    setUpRecyclerView();
    return view;
  }

  private void getSearchList() {
    List<Doctor> searchList = doctorDaoImpl.searchByName(constraint);
    if (searchList.size() > 0) {
      recyclerView.setVisibility(View.VISIBLE);
      noItemLayout.setVisibility(View.GONE);
      doctorSearchAdapter.update(searchList);
    } else {
      recyclerView.setVisibility(View.GONE);
      noItemLayout.setVisibility(View.VISIBLE);
    }

  }

  private void setUpRecyclerView() {
    doctorSearchAdapter = new DoctorSearchAdapter(newVisitActivity, this,
        doctorDaoImpl.retrieveAll());
    LinearLayoutManager layoutManager = new LinearLayoutManager(newVisitActivity);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(),
        layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(doctorSearchAdapter);
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

  public void selectedItem(Doctor doctor) {
    newVisitActivity.setSelectedDoctor(doctor);
    getDialog().dismiss();
  }
}
