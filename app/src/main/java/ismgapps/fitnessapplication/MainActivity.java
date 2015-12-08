package ismgapps.fitnessapplication;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyFitness.OnFragmentInteractionListener,
        Today.OnFragmentInteractionListener, Recipies.OnFragmentInteractionListener {

    private final String TAG = "MainActivity"; // use this tag for log actions
    private int recipieButton = 0;
    public SharedPreferences sharedPreferences; // get access to the shared preferences
    public static Context mContext; // universal context item.
    // create the logged in user object
    static User user;
    // set up fragments
    public MyFitness fitnessFrag;
    public Today todayFrag;
    public Recipies recipieFrag;
    // fragment manager to switch fragments in main activity
    FragmentManager fragmentManager = getFragmentManager();
    // get database handling objects
    static DBhandler dBhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // set up the view and toolbars
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // get the sharedprefernces for use
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //make fragments
        fitnessFrag = new MyFitness();
        todayFrag = new Today();
        recipieFrag = new Recipies();
        // get database setup...
        dBhandler = new DBhandler(this);
        //dBhandler.destroyDB(); //@TODO this line clears the DB...DELETE AFTER TESTING
        // Instantiate the User
        if (user == null) user = new User(sharedPreferences); // build user based on stored preferences
        // check if the user is logged in...if name is null then no user is loggedIn, launch login activity
        if (sharedPreferences.getString("name", null) == null){
            Intent intent = new Intent(this, LogIn.class);
            startActivityForResult(intent, 0);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, todayFrag).addToBackStack(null).commit();
            fragmentManager.executePendingTransactions();
            Log.d(TAG, "no user data exists in shared prefs");
        }
        else{ // fill user from saved preferences ...
            user.fillUserFromPrefs();
            Log.d(TAG, "user data filled from preferences");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (savedInstanceState != null && savedInstanceState.getString("fragVis") == "recipe"){
                Bundle bundle = new Bundle();
                bundle.putInt("button", savedInstanceState.getInt("button"));
                recipieFrag.setArguments(bundle);
                ft.replace(R.id.mainFrame, recipieFrag).addToBackStack(null).commit();
            }
            else if (savedInstanceState !=null && savedInstanceState.getString("fragVis") == "fitness"){
                ft.replace(R.id.mainFrame, fitnessFrag).addToBackStack(null).commit();
            }
            else {
                ft.replace(R.id.mainFrame, todayFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
            }
        }
        Log.d(TAG, "name is: " + user.getName()); // if this line prints a user name to the log, user data was loaded successfully
    }
    // force a restart of the mainActivity (like for a logout, etc.)
    public void restartMain(){
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout){
            user.logOut(); // logout from shared prefs
            restartMain(); // restart to show change
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (id) {
            case R.id.my_fitness:
                Log.i(TAG, "fitness button hit...");
                ft.replace(R.id.mainFrame, fitnessFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
                break;

            case R.id.today:
                Log.i(TAG, "today button hit...");
                ft.replace(R.id.mainFrame, todayFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
                break;

            case R.id.workouts:
                Log.i(TAG, "workout button hit...");
                Intent intent = new Intent(this, WorkoutActivity.class);
                startActivity(intent);
                break;

            case R.id.recipies:
                Log.i(TAG, "recipie button hit...");
                ft.replace(R.id.mainFrame, recipieFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
                break;

            case R.id.BMI:
                Log.i(TAG, "BMI button hit....");
                Intent BMIintent = new Intent(this, BmiCalculator.class);
                startActivity(BMIintent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    // method handles the press of change weight button in today fragment
    public void changeWeight(View view){
        Log.d(TAG, "change weight button hit from Today Fragment");
        final EditText weightInput = new EditText(this);
        weightInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Current Weight");
        builder.setView(weightInput);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try { // try catch will cause dialog to bail if the input is not a valid float number
                    float weight = Float.valueOf(weightInput.getText().toString());
                    final String input = weightInput.getText().toString();
                    TextView weightText;
                    weightText = (TextView) findViewById(R.id.weight);
                    weightText.setText(input);
                    // change weight in user, sharedPrefs and database
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    final float BMI = BmiCalculator.BMI(weight, MainActivity.user.getHeight());
                    final double age = (66 + (6.23 * MainActivity.user.getCur_weight()) + (12.7 * MainActivity.user.getHeight() - MainActivity.user.getCal_needs()))/6.8;
                    final float BMR = BmiCalculator.BMR(weight, MainActivity.user.getHeight(),(float) age);
                    user.setCur_weight(weight); // set main user current weight
                    user.setBMI(BMI);
                    user.setBMR(BMR);
                    user.setCal_needs(BMR);
                    editor.putFloat("Current_Weight", weight);
                    editor.putFloat("BMI", BMI);
                    editor.putFloat("BMR", BMR);
                    editor.putFloat("Cal_Needs", BMR);
                    editor.commit();
                    dBhandler.updateUserWeight(user.getName(), weight, BMI, BMR);
                    onFragmentInteraction(1); // call to restart fragment ;)
                } catch (Exception e) { // exception will toast the error to the user
                    Toast.makeText(MainActivity.mContext, "Input must be a number...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String fragVis;
        if (recipieFrag.isVisible()) {
            fragVis = "recipe";
            savedInstanceState.putInt("button", recipieButton);
        }
        else if (fitnessFrag.isVisible())
            fragVis = "fitness";
        else
            fragVis = "today";
        savedInstanceState.putString("fragVis", fragVis);
        super.onSaveInstanceState(savedInstanceState);
    }
    // today interaction listner
    @Override
    public void onFragmentInteraction(int test) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        todayFrag = null;
        todayFrag = new Today();
        ft.replace(R.id.mainFrame, todayFrag).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
    }
    // recipe interaction listener
    @Override
    public void onFragmentInteraction(int x, int y){
        recipieButton = y; // y is the button selected in recipe frag
    }
}
