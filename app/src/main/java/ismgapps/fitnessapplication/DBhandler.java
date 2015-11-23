package ismgapps.fitnessapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBhandler extends SQLiteOpenHelper{
    private final String TAG = "SQL DBhelper Class"; // debug tag
    private SQLiteDatabase db;
    private Context dbContext;  // universal context item

    private static final int DATABASE_VERSION = 1;          //db version number
    private static final String DATABASE_NAME = "fitness";  // name for database
    private static final String TABLE_USER = "Users";       // name for users table
    private static final String TABLE_RECIPIES = "Recipies";// name for table to hold recipie info
    private static final String TABLE_WORKOUTS = "Workouts";// name for table to hold workout details
    private static final String TABLE_FITNESS_RECORD = "Record";// table to keep track of daily workouts record

    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        createUserTable(); // make the users table
        createTestPerson(); // make test person
        // @TODO make the recipie table
        createWorkoutTable();
        fillWorkoutTable();
        // @TODO make the records table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no upgrade instructions yet
    }

    // this will create the users table, method to be used in onCreate
    private void createUserTable(){
        Log.d(TAG, "Creating user table...");
        String CMD = "CREATE TABLE IF NOT EXISTS "+ TABLE_USER +
                " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "Name TEXT NOT NULL, " +
                "Weight FLOAT NOT NULL, " +
                "Height FLOAT NOT NULL, " +
                "BMI FLOAT NOT NULL, " +
                "BMR FLOAT NOT NULL, " +
                "Starting_Weight FLOAT NOT NULL, " +
                "Start_LVL INTEGER NOT NULL, " +
                "Cal_Needs FLOAT NOT NULL, " +
                "Cur_Weight FLOAT NOT NULL, " +
                "Cur_Level INTEGER NOT NULL, " +
                "Email TEXT, " +
                "Phone TEXT);";
        db.execSQL(CMD);
    }
    // this will create a dummy person for use
    private void createTestPerson(){
        Log.d(TAG, "Creating Test Person");
        // to create a record, create a values object, put in the values paid and insert(TABLE, null, values)
        ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put("Name", "Test User");
        values.put("Height", 67);
        values.put("Weight", 200);
        values.put("BMI", 31.32);
        values.put("BMR", 1949);
        values.put("Starting_Weight", 200);
        values.put("Start_LVL", 3);
        values.put("Cal_Needs", 3021);
        values.put("Cur_Weight", 200);
        values.put("Cur_Level", 3);
        values.put("Email", "test@123.com");
        values.put("Phone", "123-222-2222");

        db.insert(TABLE_USER, null, values);
    }
    // Insert a user into the database
    public static void InsertUser(User user) {
        ContentValues values = new ContentValues();
        //values.put("_id", xxxxx); // user _id is autoIncrement
        values.put("Name", user.getName());
        values.put("Height", user.getHeight());
        values.put("Weight", user.getWeight());
        values.put("BMI", user.getBMI());
        values.put("BMR", user.getBMR());
        values.put("Starting_Weight", user.getWeight());
        values.put("Start_LVL", user.getStart_lvl());
        values.put("Cal_Needs", user.getCal_needs());
        values.put("Cur_Weight", user.getCur_weight());
        values.put("Cur_Level", user.getCur_lvl());
        values.put("Email", user.getEmail());
        values.put("Phone", user.getPhone());

        MainActivity.dbWrite.insert(TABLE_USER, null, values);
    }
    // this will create the workout table
    private void createWorkoutTable(){
        Log.d(TAG, "Creating Workout Table");
        String CMD = "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUTS +
                " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "Name TEXT NOT NULL, " +
                "Description TEXT NOT NULL, "+
                "CAL_COUNT FLOAT NOT NULL, " +
                "IS_MULTIPLIER INTEGER NOT NULL);";
        db.execSQL(CMD);
    }
    // fill the workout table with basic data
    private void fillWorkoutTable(){
        Log.d(TAG, "Filling basic workouts table");
        String name = "Name"; String desc = "Description";
        String count = "CAL_COUNT"; String mult = "IS_MULTIPLIER";
        ContentValues values = new ContentValues();

        values.put(name, "The Limb Loosener");
        values.put(desc, "5 Handstand push-ups\n10 1-legged squats\n15 pull-ups");
        values.put(count, 1.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Core Agony");
        values.put(desc, "100 Pull-ups\n100 Push-ups\n100 Sit-ups\n100 Squats");
        values.put(count, 2.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "The Wimp Special");
        values.put(desc, "5 Pull-ups\n10 Push-ups\n15 Squats");
        values.put(count, 3.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Strength and Length");
        values.put(desc, "500 meter run\n21 x 1.5 pood kettlebell swing\n21 x pull-ups");
        values.put(count, 2.4);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();
    }
    // delete a workout by ID
    public boolean deleteWorkout(int id){
        Log.d(TAG, "workout with id " + id + "selected for deletion");
        return db.delete(TABLE_WORKOUTS, "_ID" + "=" + id, null) > 0;
    }


    // this will delete entire database
    public void destroyDB(){
        dbContext.deleteDatabase(DATABASE_NAME);
    }

}
