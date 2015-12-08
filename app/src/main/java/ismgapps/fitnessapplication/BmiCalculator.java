package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        if (savedInstanceState != null){
            TextView r = (TextView)findViewById(R.id.Result);
            r.setText(savedInstanceState.getString("result"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set back button operation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView r = (TextView)findViewById(R.id.Result);
        String result = r.getText().toString();
        outState.putString("result", result);
        super.onSaveInstanceState(outState);
    }

    public void BmiCalculate (View view){
        if (view.getId() == R.id.calculate){

            // declear the variable
            EditText W = (EditText)findViewById(R.id.wight);
            EditText H = (EditText)findViewById(R.id.hight);
            TextView r = (TextView)findViewById(R.id.Result);

            try {
                // get a user input
                float UserHeight = Float.parseFloat(H.getText().toString());
                float UserWeight = Float.parseFloat(W.getText().toString());
                float bmiValue = BMI(UserWeight, UserHeight);

                // interpret the meaning of the bmi value
                String bmiInterpretation = interpretBMI(bmiValue);

                // show the result

                r.setText(bmiValue + "   " + bmiInterpretation);
            } catch (Exception e){}
        }
    }

    // check for http://en.wikipedia.org/wiki/Body_mass_index
    static float BMI (float weight, float height) {
        return (float) (weight * 4.88 / (height * height));
    }
    //  BMR = 66 + ( 6.23 x weight in pounds ) + ( 12.7 x height in inches ) - ( 6.8 x age in year )
    static float BMR (float weight, float height, float age){
        return (float) (66 + (6.23 * weight) + (12.7 * height) - ( 6.8 * age));
    }

    private String interpretBMI(float BmiResult) {
        if (BmiResult > 30)
        {
            return "You are Obese";
        }
        else if (BmiResult > 25)
        {
            return "You are Overweight";
        }
        else if (BmiResult > 18.5)
        {
            return "You are Normal";
        }
        else
        {
            return "You are Underweight";
        }
    }
}
