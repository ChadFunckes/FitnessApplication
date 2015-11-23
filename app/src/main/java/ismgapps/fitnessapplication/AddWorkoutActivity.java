package ismgapps.fitnessapplication;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class AddWorkoutActivity extends AppCompatActivity {
    private EditText nameInput, descInput, calInput;
    private RadioButton totalCal, perPoundCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameInput = (EditText) findViewById(R.id.workoutNameEditText);
        descInput = (EditText) findViewById(R.id.workOutEditText);
        calInput = (EditText) findViewById(R.id.calAmountEditText);
        //radio button selections
        totalCal = (RadioButton) findViewById(R.id.totalCalorieSelected);
        perPoundCal = (RadioButton) findViewById(R.id.perPoundSelected);
    }
    // do this when the workout button is clicked

    public void addWorkoutClicked(View view){
        ContentValues values = new ContentValues();
        values.put("Name", nameInput.getText().toString());
        values.put("Description", descInput.getText().toString());
        values.put("CAL_COUNT", Float.valueOf(calInput.getText().toString()));
        if (totalCal.isChecked()){
           values.put("IS_MULTIPLIER", 0);
        }else {
           values.put("IS_MULTIPLIER", 1);
        }
        int idInserted = MainActivity.dBhandler.insertWorkout(values); // put the data in the database, return the ID that was used
        WorkoutData thisData = new WorkoutData(idInserted, nameInput.getText().toString(),
                descInput.getText().toString() , values.getAsFloat("CAL_COUNT"), values.getAsInteger("IS_MULTIPLIER"));
        WorkoutData.workouts.add(thisData); // add to list
        finish();
    }

}
