package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, age, password, email, phone, weight, height;
    private TextView BMI, BMR;
    boolean calcComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // get text references
        name = (EditText)findViewById(R.id.editTextUsername);
        age = (EditText) findViewById(R.id.textAge);
        password = (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextemail);
        phone = (EditText) findViewById(R.id.editTextPhone);
        weight = (EditText) findViewById(R.id.editTextWight);
        height = (EditText) findViewById(R.id.editTextHeight);
        BMI = (TextView) findViewById(R.id.textBMI);
        BMR = (TextView) findViewById(R.id.textBMR);

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float localHeight;
                int localAge;
                if (!height.getText().toString().equals("")) // if the height is not empty, set height
                    localHeight = Float.valueOf(height.getText().toString());
                else localHeight = 0; // else height did not exist

                if (!age.getText().toString().equals(""))
                    localAge = Integer.valueOf(age.getText().toString());
                else localAge = 0;

                if (!hasFocus && localHeight > 0 ) { // when lost focus calculate if weight has been filled in
                    BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
                    if (localAge > 0)
                        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
                }
            }
        });

        height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float localWeight;
                int localAge;
                if (!weight.getText().toString().equals(""))
                    localWeight = Float.valueOf(weight.getText().toString());
                else localWeight = 0;

                if (!age.getText().toString().equals(""))
                    localAge = Integer.valueOf(age.getText().toString());
                else localAge = 0;

                if (!hasFocus && localWeight > 0) {
                    BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
                    if (localAge > 0)
                        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
                }
            }
        });
    } // end onCreate

    @Override
    public void onBackPressed() { // this makes sure the user can not back out of this activity
    }

    public void calculate(View view){
        Toast toast;
        calcComplete = false;

        if (age.getText().toString().equals("")) {
            toast = Toast.makeText(this, "Must set an age for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (weight.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a weight for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (height.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a height for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
        calcComplete = true;
    }

    public void register(View view){
        Toast toast;
        calculate(view);

        if (!calcComplete)
            return;

        if (name.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a user name...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (password.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a user name...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        MainActivity.user.setName(name.getText().toString());
        MainActivity.user.setPassword(name.getText().toString());
        MainActivity.user.setWeight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setCur_weight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setStart_weight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setHeight(Float.valueOf(height.getText().toString()));
        MainActivity.user.setBMI(Float.valueOf(BMI.getText().toString()));
        MainActivity.user.setBMR(Float.valueOf(BMR.getText().toString()));
        MainActivity.user.setStart_lvl(0);
        MainActivity.user.setCur_lvl(0);
        MainActivity.user.setCal_needs(Float.valueOf(BMR.getText().toString()));
        MainActivity.user.setEmail(email.getText().toString());
        MainActivity.user.setPhone(phone.getText().toString());

        MainActivity.user.commitUserToDB();
        finish();
    }

}
