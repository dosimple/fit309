package fit309.callresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class CallResponder extends BroadcastReceiver {

    SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(! intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
            return;

        if(pref == null)
            pref = context.getSharedPreferences("CallResponder", Context.MODE_PRIVATE);

        String message = pref.getString("message", "");

        if(! pref.getBoolean("enable", false) || message.equals(""))
            return;

        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Toast.makeText(context, "Incomming: " + number + ", Message: " + message, Toast.LENGTH_LONG).show();
    }
}
