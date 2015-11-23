package ismgapps.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LogIn extends AppCompatActivity {

    private User user = MainActivity.user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

       Intent intent = getIntent();

        // this is a test yadda ydaa dadda
    }
    public void Register (View view){
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }
}
