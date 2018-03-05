package app.arash.androidcore.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.ui.adapter.VideoListAdapter.ViewHolder;
import app.arash.androidcore.util.NumberUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoListAdapter extends Adapter<ViewHolder> {

  private AppCompatActivity context;
  private List<Video> videos = new ArrayList<>();
  private LayoutInflater layoutInflater;

  public VideoListAdapter(AppCompatActivity context, List<Video> videos) {
    this.context = context;
    this.videos = videos;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_video, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return videos.size();
  }

  public void updateList(List<Video> myDrugs) {
    this.videos = myDrugs;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_title_tv)
    TextView videoTitleTv;
    @BindView(R.id.video_preview)
    ImageView videoPreview;
    @BindView(R.id.video_time)
    TextView videoTime;

    private Video video;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.video = videos.get(position);
      if (!TextUtils.isEmpty(video.getTitle())) {
        videoTitleTv.setText(video.getTitle().trim());
      }
      videoTime.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%02d:%02d",
          video.getLength() / 60, video.getLength() % 60)));
      Glide.with(context).load(video.getImagePreview()).into(videoPreview);
    }

    @OnClick({R.id.root_layout, R.id.video_title_tv})
    public void onViewClicked(View view) {
      switch (view.getId()) {
        case R.id.category_name_tv:

        case R.id.root_layout:
          Toast.makeText(context, "TIME" + video.getLength(), Toast.LENGTH_SHORT).show();
//          Intent intent = new Intent(context, VideoListActivity.class);
//          intent.putExtra(Constant.VIDEO_CATEGORY, video);
//          context.startActivity(intent);
          break;
      }
    }
  }
}
