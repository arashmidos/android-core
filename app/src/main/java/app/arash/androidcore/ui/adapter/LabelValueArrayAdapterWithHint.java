package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.arash.androidcore.R;
import java.util.List;

/**
 * Created by Shakib on 03/09/2017
 */
public class LabelValueArrayAdapterWithHint extends BaseAdapter {

  private LayoutInflater mLayoutInflater;
  private List<String> items;

  public LabelValueArrayAdapterWithHint(Context context, List<String> items) {
    this.items = items;
    mLayoutInflater = (LayoutInflater) context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LabelValueViewHolder holder;

    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.row_layout_label_value_spinner, null);
      holder = new LabelValueViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.labelTxt);
      convertView.setTag(holder);
    } else {
      holder = (LabelValueViewHolder) convertView.getTag();
    }
    if (position == getCount()) {
      holder.text.setText("");
      holder.text.setHint(items.get(getCount()));
    } else {
      holder.text.setText(items.get(position));
    }
    return convertView;
  }

  @Override
  public int getCount() {
    return items.size() - 1;
  }

  @Override
  public Object getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  private static class LabelValueViewHolder {

    TextView text;
  }
}
