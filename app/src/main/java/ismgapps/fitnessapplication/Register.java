package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {


    //Link variable with layout
    //declare variable
    EditText username = (EditText)findViewById(R.id.editTextUsername);
    EditText email = (EditText)findViewById(R.id.editTextemail);
    EditText phone = (EditText)findViewById(R.id.editTextPhone);
    EditText weight = (EditText)findViewById(R.id.editTextWight);
    EditText height = (EditText)findViewById(R.id.editTextHeight);
    EditText bmi = (EditText)findViewById(R.id.editTextBMI);
    Button register = (Button)findViewById(R.id.Register);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
