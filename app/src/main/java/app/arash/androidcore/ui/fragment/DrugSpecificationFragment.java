package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Drug;
import app.arash.androidcore.ui.adapter.DrugSpecificationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.List;


public class DrugSpecificationFragment extends Fragment {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private Unbinder unbinder;
  private Drug drug;
  private LinearLayoutManager layoutManager;

  public DrugSpecificationFragment() {
    // Required empty public constructor
  }


  public static DrugSpecificationFragment newInstance(Drug drug) {
    DrugSpecificationFragment fragment = new DrugSpecificationFragment();
    fragment.drug = drug;
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drug_specification, container, false);
    unbinder = ButterKnife.bind(this, view);
    setUpRecyclerView();
    return view;
  }

  private void setUpRecyclerView() {
    DrugSpecificationAdapter drugSpecificationAdapter = new DrugSpecificationAdapter(getActivity(),
        getItems(), this);
    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setAdapter(drugSpecificationAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  public void scrollToPosition(int position) {
    layoutManager.scrollToPositionWithOffset(position, 0);
  }

  private List<String> getItems() {
    List<String> pre = new ArrayList<>();
    List<String> post = new ArrayList<>();

    if (drug.getNameFa() != null) {
      pre.add(drug.getNameFa());
      post.add(drug.getNameFa());
    }
    if (drug.getUsage() != null) {
      pre.add(getString(R.string.usage));
      post.add(drug.getUsage());
    }
    if (drug.getNotUsage() != null) {
      pre.add(getString(R.string.not_usage));
      post.add(drug.getNotUsage());
    }
    if (drug.getSideEffect() != null) {
      pre.add(getString(R.string.side_effect));
      post.add(drug.getSideEffect());
    }
    if (drug.getDrugConflict() != null) {
      pre.add(getString(R.string.drug_conflict));
      post.add(drug.getDrugConflict());
    }
    if (drug.getPregnancy() != null) {
      pre.add(getString(R.string.pregnancy));
      post.add(drug.getPregnancy());
    }
    if (drug.getInstruction() != null) {
      pre.add(getString(R.string.instruction));
      post.add(drug.getInstruction());
    }
    pre.addAll(post);
    return pre;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
