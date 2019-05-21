package app.arash.androidcore.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.arash.androidcore.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IntroFragment extends Fragment {

  @BindView(R.id.intro_img)
  ImageView introImg;
  @BindView(R.id.intro_tv)
  TextView introTv;
  @BindView(R.id.detail_tv)
  TextView detailTv;
  private Context context;
  private int textId;
  private int imageId;
  private int position;

  public IntroFragment() {
    // Required empty public constructor
  }


  public static IntroFragment newInstance(Context context, int textId, int imageId, int position) {
    IntroFragment fragment = new IntroFragment();
    fragment.context = context;
    fragment.textId = textId;
    fragment.imageId = imageId;
    fragment.position = position;
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_intro, container, false);
    ButterKnife.bind(this, view);
    setData();
    return view;
  }

  private void setData() {
    introImg.setImageResource(imageId);
    introTv.setText(getString(textId));
    if (position == 0) {
      detailTv.setVisibility(View.VISIBLE);
    } else {
      detailTv.setVisibility(View.GONE);
    }
  }
}
