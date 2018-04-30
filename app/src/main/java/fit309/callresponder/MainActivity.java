package fit309.callresponder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    CheckBox enable;
    EditText message;
    Button save;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("CallResponder", Context.MODE_PRIVATE);

        enable = findViewById(R.id.enable);
        message = findViewById(R.id.message);
        save = findViewById(R.id.save);

        enable.setChecked(pref.getBoolean("enable", false));
        message.setText(pref.getString("message", ""));

        save.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("message", message.getText().toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "Message saved.", Toast.LENGTH_SHORT).show();
        });

        enable.setOnCheckedChangeListener((v, checked) -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("enable", checked);
            editor.commit();
        });
    }
}
