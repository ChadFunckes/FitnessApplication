package ismgapps.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LogIn extends AppCompatActivity {
    private static final String TAG = "Login Activity";
    //private User user = MainActivity.user;
    private EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
    }
    public void Register (View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void Login (View view){
        Log.d(TAG, "Login Button Selected");
        int loginAnswer; // answer will be 0 for sucess, 1 for failed name, 2 for failed password
        loginAnswer = MainActivity.dBhandler.checkUserLogin(name.getText().toString(), password.getText().toString());
        switch (loginAnswer){
            case (0):
                Log.d(TAG, "User and passord correct");
                finish();
                break;
            case (1):
                Log.d(TAG, "User Name was not found");
                break;
            case (2):
                Log.d(TAG, "Password was not found");
                break;
        }
    }
}
