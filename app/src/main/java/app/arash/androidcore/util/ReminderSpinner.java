package app.arash.androidcore.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by shkbhbb on 2/15/18.
 */

public class ReminderSpinner extends Spinner {
  OnItemSelectedListener listener;

  public ReminderSpinner(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void setSelection(int position) {
    super.setSelection(position);
    if (listener != null)
      listener.onItemSelected(null, null, position, 0);
  }

  public void setOnItemSelectedEvenIfUnchangedListener(
      OnItemSelectedListener listener) {
    this.listener = listener;
  }
}
