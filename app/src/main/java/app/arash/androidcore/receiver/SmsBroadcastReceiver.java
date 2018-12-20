package app.arash.androidcore.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import app.arash.androidcore.BuildConfig;
import app.arash.androidcore.util.Empty;
import app.arash.androidcore.util.Utils;

public class SmsBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = "SmsBroadcastReceiver";

  private final String serviceProviderNumber;

  private Listener listener;

  public SmsBroadcastReceiver(String serviceProviderNumber) {
    this.serviceProviderNumber = serviceProviderNumber;
  }

  public SmsBroadcastReceiver() {
    this.serviceProviderNumber = BuildConfig.SERVICE_NUMBER;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      if (intent.getAction() != null && intent.getAction()
          .equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
        String smsSender = "";
        String smsBody = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            smsSender = smsMessage.getDisplayOriginatingAddress();
            smsBody += smsMessage.getMessageBody();
          }
        } else {
          Bundle smsBundle = intent.getExtras();
          if (smsBundle != null) {
            Object[] pdus = (Object[]) smsBundle.get("pdus");
            if (pdus == null) {
              // Display some error to the user
              Log.e(TAG, "SmsBundle had no pdus key");
              return;
            }
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
              messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
              smsBody += messages[i].getMessageBody();
            }
            smsSender = messages[0].getOriginatingAddress();
          }
        }

        if (smsSender != null && smsSender.length() >= 6
            && smsSender.substring(smsSender.length() - 6).equals(serviceProviderNumber)) {

          String confirmCode = Utils.extractConfirmCode(smsBody);
          if (Empty.isNotEmpty(confirmCode) && listener != null) {
            listener.onTextReceived(confirmCode);
          }
        }
      }
    } catch (Exception ignore) {

    }
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public interface Listener {

    void onTextReceived(String text);
  }
}
