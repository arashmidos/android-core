package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import app.arash.androidcore.R;
import app.arash.androidcore.ui.fragment.IntroFragment;

public class IntroPagerAdapter extends FragmentStatePagerAdapter {

  private final int[] texts = {R.string.intro_text_0, R.string.intro_text_1, R.string.intro_text_2,
      R.string.intro_text_3};
  private final int[] images = {R.drawable.im_slide_1, R.drawable.im_slide_2,
      R.drawable.im_slide_3, R.drawable.im_slide_4};
  private final Context context;

  public IntroPagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    this.context = context;
  }

  @Override
  public Fragment getItem(int position) {
    return IntroFragment.newInstance(context, texts[position], images[position], position);
  }

  @Override
  public int getCount() {
    return texts.length;
  }

}
