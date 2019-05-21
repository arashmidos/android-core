package app.arash.androidcore.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import java.util.List;

/**
 * Created by Shakib on 03/09/2017
 */
public class SpinnerHintArrayAdapter extends ArrayAdapter<String> {

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource) {
    super(context, resource);
  }

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
    super(context, resource, textViewResourceId);
  }

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource,
      @NonNull String[] objects) {
    super(context, resource, objects);
  }

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource, int textViewResourceId,
      @NonNull String[] objects) {
    super(context, resource, textViewResourceId, objects);
  }

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource,
      @NonNull List<String> objects) {
    super(context, resource, objects);
  }

  public SpinnerHintArrayAdapter(@NonNull Context context, int resource, int textViewResourceId,
      @NonNull List<String> objects) {
    super(context, resource, textViewResourceId, objects);
  }

  @Override
  public int getCount() {
    // don't display last item. It is used as hint.
    int count = super.getCount();
    return count > 0 ? count - 1 : count;
  }
}
