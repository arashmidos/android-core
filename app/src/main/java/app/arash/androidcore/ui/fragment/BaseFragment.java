package app.arash.androidcore.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Mahyar on 6/3/2015.
 */
public abstract class BaseFragment extends Fragment {

  protected void runOnUiThread(Runnable action) {
    if (getActivity() != null) {
      getActivity().runOnUiThread(action);
    }
  }

  public String getFragmentTag() {
    return this.getClass().getSimpleName();
  }

  public abstract int getFragmentId();
}
