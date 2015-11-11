package ismgapps.fitnessapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// @TODO clean up interaction listeners....
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyFitness.OnFragmentInteractionListener,
        Today.OnFragmentInteractionListener, Workouts.OnFragmentInteractionListener, Recipies.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity"; // use this tag for log actions
    SharedPreferences sharedPreferences;

    // @TODO make user class object here
    User user;

    // set up fragments
    public MyFitness fitnessFrag;
    public Today todayFrag;
    public Workouts workoutFrag;
    public Recipies recipieFrag;

    // get database handeling objects
    SQLiteDatabase dbWrite = null;
    DBhandler dBhandler; // database helper object
    // fragment manager to switch fragments in main activity
    FragmentManager fragmentManager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        sharedPreferences = this.getSharedPreferences("user_info", MODE_PRIVATE);

        //make fragments
        fitnessFrag = new MyFitness();
        todayFrag = new Today();
        workoutFrag = new Workouts();
        recipieFrag = new Recipies();

        // get database setup...
        dBhandler = new DBhandler(this);
        // dBhandler.destroyDB(); // @TODO remove this line, it was for testing....
        dbWrite = dBhandler.getWritableDatabase(); // this will run onCreate in DBhandler...
        // Instantiate the User
        user = new User();
        // @TODO build dummy gets a fake user from the database...delete this agter login section is working
        //user.buildDummy(dbWrite, sharedPreferences);

        // check if the user is logged in...if name is null then no user is loggedIn
        /* TODO reactivate this block of code after dummy testing to start login activity
        if (sharedPreferences.getString("name", null) == null){
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }*/

    }

    public void openLogin(View view){ // starts a login instance....
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("xx", "settings button selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings("StatementWithEmptyBody")
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
                ft.replace(R.id.mainFrame, workoutFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
                break;

            case R.id.recipies:
                Log.i(TAG, "recipie button hit...");
                ft.replace(R.id.mainFrame, recipieFrag).addToBackStack(null).commit();
                fragmentManager.executePendingTransactions();
                break;

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
