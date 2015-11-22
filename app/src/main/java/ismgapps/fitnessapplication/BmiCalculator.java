package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
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

    public void BmiCalculate (View view){
        if (view.getId() == R.id.calculate){

            // declear the variable
            EditText W = (EditText)findViewById(R.id.wight);
            EditText H = (EditText)findViewById(R.id.hight);
            TextView r = (TextView)findViewById(R.id.Result);


            // get a user input
            float UserHeight = Float.parseFloat(H.getText().toString());
            float UserWeight = Float.parseFloat(W.getText().toString());
            float bmiValue = BMI(UserWeight, UserHeight);

            // interpret the meaning of the bmi value
            String bmiInterpretation = interpretBMI(bmiValue);

            // show the result

            r.setText(bmiValue + "   " + bmiInterpretation);
        }
    }

    // check for http://en.wikipedia.org/wiki/Body_mass_index
    private float BMI (float weight, float height) {

        return (float) (weight * 4.88 / (height * height));
    }




    private String interpretBMI(float bmiResult) {

        if (bmiResult < 16) {
            return " You are Severely underweight ";
        } else if (bmiResult < 18.5) {

            return "You are Underweight";
        } else if (bmiResult < 25) {

            return "You are Normal";
        } else if (bmiResult < 30) {

            return "You are Overweight";
        } else {
            return "You are Obese";
        }



    }
}
