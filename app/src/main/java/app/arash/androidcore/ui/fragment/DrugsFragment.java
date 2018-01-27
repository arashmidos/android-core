package app.arash.androidcore.ui.fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.activity.MyDrugsActivity;
import app.arash.androidcore.ui.adapter.DrugsViewPagerAdapter;
import app.arash.androidcore.ui.fragment.dialog.SearchDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DrugsFragment extends BaseFragment {

  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.view_pager)
  ViewPager viewPager;

  private Unbinder unbinder;
  private MainActivity mainActivity;

  public DrugsFragment() {
    // Required empty public constructor
  }

  public static DrugsFragment newInstance() {
    DrugsFragment fragment = new DrugsFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drugs, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    tabLayout.setupWithViewPager(viewPager);
    setUpViewPager();
    viewPager.setCurrentItem(2);
    return view;
  }

  private void setUpViewPager() {
    DrugsViewPagerAdapter viewPagerAdapter = new DrugsViewPagerAdapter(getChildFragmentManager());
    viewPagerAdapter.add(StaredDrugsFragment.newInstance(), getString(R.string.stared));
    viewPagerAdapter.add(DrugCategoryFragment.newInstance(), getString(R.string.category));
    viewPagerAdapter.add(DrugListFragment.newInstance(), getString(R.string.drugs_list));
    viewPager.setAdapter(viewPagerAdapter);
  }

  private void showSearchDialog() {
    FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
    SearchDialogFragment searchDialogFragment = SearchDialogFragment.newInstance(mainActivity);
    searchDialogFragment.show(ft, "search");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.search_img, R.id.my_drugs_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.search_img:
        showSearchDialog();
        break;
      case R.id.my_drugs_tv:
        startActivity(new Intent(mainActivity, MyDrugsActivity.class));
        break;
    }
  }

  @Override
  public int getFragmentId() {
    return MainActivity.DRUGS_FRAGMENT;
  }
}
