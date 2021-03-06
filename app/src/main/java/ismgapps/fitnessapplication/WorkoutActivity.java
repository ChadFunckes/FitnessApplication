package ismgapps.fitnessapplication;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class WorkoutActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {
    private static final String TAG = "Workout Activity";
    static int workoutIDSelected; // this is id FROM THE DB, not relative position in the list!!  set from WorkoutDetailFragment after selection
    static int workoutPosition; // a workout position in the selection list
    private WorkoutDetailFragment details;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    // call to add workout
    public void addWorkout(View view){
        Log.d(TAG, "add workout pressed");
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivityForResult(intent, 1);
        Log.d(TAG, "FINISH");
    }
    // result used to stop and refresh the WorkoutActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        finish();
        startActivity(getIntent());
    }
    // call to remove workout
    public void removeWorkout(View view){
        Log.d(TAG, "remove workout pressed");
        /// reminder that this class's element workoutIDSelected contains the _ID from the database to delete and workoutPosition is the position in the list
        if (details != null && details.isVisible()){ // if there was a details fragment in the frame an item was selected
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete:\n" + WorkoutData.workouts.get(workoutPosition).getName())
                    .setPositiveButton("yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            Log.d(TAG, "yes button selected");
                            MainActivity.dBhandler.deleteWorkout(workoutIDSelected); // delete the selection from the database
                            WorkoutData.workouts.remove(workoutPosition); // delete the selection from the workoutlist
                            /// finish and restart activity to refresh screen
                            finish();
                            startActivity(getIntent());
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "cancel hit");
                }
            }).show();
        }
        else {
            Toast toast = Toast.makeText(this, "Please Select a Workout to delete", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    // call to schedule workout
    public void scheduleWorkout(View view){
        Log.d(TAG, "schedule workout pressed");
        if (details != null && details.isVisible()) {
            MainActivity.dBhandler.addWorkoutToToday(workoutIDSelected); // add workout with the ID selected
        }
        else Toast.makeText(this, "Please Select and item to schedule", Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemClicked(long id) {
        details = new WorkoutDetailFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        details.setWorkout(id);
        ft.replace(R.id.fragment_container, details);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
