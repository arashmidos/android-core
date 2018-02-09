package app.arash.androidcore.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.DoctorVisitDto;
import app.arash.androidcore.data.impl.DoctorVisitDaoImpl;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.util.DateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BootReceiver extends BroadcastReceiver {

  private static final String TAG = BootReceiver.class.getSimpleName();
  private AlarmManager alarmMgr;
  private PendingIntent visitAlarmIntent;

  public void showNotification(Context context, Intent intent) {
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    Intent notificationIntent = new Intent(context, MainActivity.class);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    stackBuilder.addParentStack(MainActivity.class);
    stackBuilder.addNextIntent(notificationIntent);

    PendingIntent pendingIntent = stackBuilder.getPendingIntent(
        101, PendingIntent.FLAG_UPDATE_CURRENT);

    int type = intent.getIntExtra(Constant.REMINDER_TYPE, -1);
    long id = intent.getLongExtra(Constant.REMINDER_ID, -1);
    String title = "";
    String content = "";
    DoctorVisitDaoImpl visitDao = new DoctorVisitDaoImpl(context);
    if (type == Constant.REMINDER_TYPE_VISIT && id != -1L) {
      DoctorVisitDto visit = visitDao.getVisitDto(id);
      title = String.format(Locale.US, "قرار ملاقات با دکتر %s", visit.getDoctor().getName());
      content = String.format(Locale.US, "ساعت %s", visit.getVisitTime());
    } else if (type == Constant.REMINDER_TYPE_DRUG) {

    } else {
      return;
    }

    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

    Notification notification = builder.setContentTitle(title)
        .setContentText(content).setAutoCancel(true)
        .setSound(alarmSound).setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentIntent(pendingIntent).build();

    NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(101, notification);

    visitDao.changeVisitStatus(id, Constant.STATUS_ALARM_RINGED);
    setVisitAlarm(context);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      if (intent != null && "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        setAlarm(context);
        return;
      }
      if (intent != null) {
        showNotification(context, intent);
      }
    } catch (Exception ignore) {
    }
  }

  public void setAlarm(Context context) {
    setVisitAlarm(context);
  }

  private void setVisitAlarm(Context context) {
    DoctorVisitDaoImpl doctorVisitDao = new DoctorVisitDaoImpl(context);
    DoctorVisitDto dto = doctorVisitDao.getLatestDoctorVisit();
    if (dto != null) {
      Date date = DateUtil
          .convertStringToDate(dto.getVisitDate(), DateUtil.FULL_FORMATTER_GREGORIAN_WITH_TIME,
              "EN");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);

      Log.i(TAG, "required information is available. trying to set alarm");
      alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      Intent intent = new Intent(context, BootReceiver.class);
      intent.putExtra(Constant.REMINDER_ID, dto.getId());
      intent.putExtra(Constant.REMINDER_TYPE, Constant.REMINDER_TYPE_VISIT);
      visitAlarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1364, intent,
          PendingIntent.FLAG_UPDATE_CURRENT);
//TODO: change this to calendar.getTimeInMillis
      alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), visitAlarmIntent);
      Log.d(TAG, "Alarm set successfully!");

      ComponentName receiver = new ComponentName(context, BootReceiver.class);
      PackageManager pm = context.getPackageManager();

      pm.setComponentEnabledSetting(receiver,
          PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

      doctorVisitDao.changeVisitStatus(dto.getId(), Constant.STATUS_ALARM_SET);
    }
  }

  public void cancelAlarm(Context context) {
    try {
      if (alarmMgr != null) {
        alarmMgr.cancel(visitAlarmIntent);
      }

      ComponentName receiver = new ComponentName(context, BootReceiver.class);
      PackageManager pm = context.getPackageManager();

      pm.setComponentEnabledSetting(receiver,
          PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
          PackageManager.DONT_KILL_APP);
    } catch (Exception ex) {
      Log.e(TAG, "error in canceling alarm", ex);
    }
  }
}