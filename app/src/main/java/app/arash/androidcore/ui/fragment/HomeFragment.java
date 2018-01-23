package app.arash.androidcore.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.arash.androidcore.R;
import app.arash.androidcore.ui.activity.MainActivity;

public class HomeFragment extends BaseFragment {

  public HomeFragment() {
    // Required empty public constructor
  }


  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    return view;
  }

  @Override
  public int getFragmentId() {
    return MainActivity.HOME_FRAGMENT;
  }
}
