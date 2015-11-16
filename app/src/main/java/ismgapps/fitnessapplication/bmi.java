package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class bmi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
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
        if (view.getId() == R.id.button4){

            // declear the variable
            EditText w = (EditText)findViewById(R.id.wight);
            EditText h = (EditText)findViewById(R.id.hight);
            TextView r = (TextView)findViewById(R.id.Result);

            // get a user input
            float weight = Float.parseFloat(w.getText().toString());
            float height = Float.parseFloat(h.getText().toString());

            float bmiValue = calculateBMI(weight, height);

            // interpret the meaning of the bmi value
            String bmiInterpretation = interpretBMI(bmiValue);

            // now set the value in the result text

            r.setText(bmiValue + "-" + bmiInterpretation);
        }
    }

    // the formula to calculate the BMI index

    // check for http://en.wikipedia.org/wiki/Body_mass_index
    private float calculateBMI (float weight, float height) {

        return (float) (weight * 4.88 / (height * height));
    }




    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }

    }
}