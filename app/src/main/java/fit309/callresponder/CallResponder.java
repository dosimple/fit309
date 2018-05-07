package fit309.callresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;


public class CallResponder extends BroadcastReceiver {

    private static final String TAG = "CallResponder";
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

        try {
            endCall(context);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
        Toast.makeText(context, "Incomming call: " + number, Toast.LENGTH_LONG).show();
    }

    protected void endCall(Context context) throws Exception {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Method getITelephony = TelephonyManager.class.getDeclaredMethod("getITelephony");
        getITelephony.setAccessible(true);
        Object telephony = getITelephony.invoke(telephonyManager);
        Method endCall = telephony.getClass().getDeclaredMethod("endCall");
        endCall.invoke(telephony);
    }
}
