package ismgapps.fitnessapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class DBhandler extends SQLiteOpenHelper{
    private final static String TAG = "SQL DBhelper Class"; // debug tag
    public static SQLiteDatabase db;
    private Context dbContext;  // universal context item

    private static final int DATABASE_VERSION = 1;          //db version number
    private static final String DATABASE_NAME = "fitness";  // name for database
    private static final String TABLE_USER = "Users";       // name for users table
    private static final String TABLE_RECIPIES = "Recipies";// name for table to hold recipie info
    private static final String TABLE_WORKOUTS = "Workouts";// name for table to hold workout details
    private static final String TABLE_FITNESS_RECORD = "Record";// table to keep track of daily workouts record
    //public constructor
    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbContext = context;
        this.db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        createUserTable(); // make the users table
        createTestPerson(); // make test person
        createWorkoutTable(); // make workout table
        fillWorkoutTable(); // fill workouts table
        createRecordsTable(); // make workout records table
        fillDates(); // fill test users workout data
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
                "Password TEXT NOT NULL, " +
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
        values.put("Name", "Test");
        values.put("Password", "pass");
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
        values.put("Password", user.getPassword());
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

        db.insert(TABLE_USER, null, values);
    }
    // check login information (Returns 1 if name was incorrect, 2 if password was incorrect, 0 if mission success
    public int checkUserLogin(String name, String password){
        Log.d(TAG, "Check user login operation hit with name " + name + " and password " + password);
        String CMD = "SELECT * FROM " + TABLE_USER + " WHERE Name ='" + name + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() < 1){ return 1; }// name was not found
        else{
            c.moveToFirst(); // put cursor back
            if (!c.getString(2).equals(password)){ return 2; } // password was not found
        }
        // user name and password found!! .. build up current user
        MainActivity.user.setName(c.getString(1));
        MainActivity.user.setPassword(c.getString(2));
        MainActivity.user.setWeight(c.getFloat(3));
        MainActivity.user.setHeight(c.getFloat(4));
        MainActivity.user.setBMI(c.getFloat(5));
        MainActivity.user.setBMR(c.getFloat(6));
        MainActivity.user.setStart_weight(c.getFloat(7));
        MainActivity.user.setStart_lvl(c.getInt(8));
        MainActivity.user.setCal_needs(c.getFloat(9));
        MainActivity.user.setCur_weight(c.getFloat(10));
        MainActivity.user.setCur_lvl(c.getInt(11));
        MainActivity.user.setEmail(c.getString(12));
        MainActivity.user.setPhone(c.getString(13));
        // put the user info into userPrefs (loggedIN info)
        MainActivity.user.commitUserToPrefs();
        return 0;
    }
    // update a users weight, inputs are username and new weight
    public void updateUserWeight(String name, float weight, float BMI, float BMR){
        ContentValues values = new ContentValues();
        values.put("Cur_Weight", weight);
        values.put("BMI", BMI);
        values.put("BMR", BMR);
        values.put("Cal_Needs", BMR);
        db.update(TABLE_USER, values, "Name = '" + name + "'", null);
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

        values.put(name, "Bench Press");
        values.put(desc, "10 to 12 Reps\n4 Sets\n30 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

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
    // insert a workout into the DB, returns the ID of the item inserted
    public int insertWorkout(ContentValues values){
        db.insert(TABLE_WORKOUTS, null, values);
        String CMD = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE Name ='" + values.get("Name") + "';";
        Cursor c = db.rawQuery(CMD, null);
        c.moveToFirst();
        return c.getInt(0);
    }
    private void createRecordsTable(){
        Log.d(TAG, "Creating Records table");
        String CMD = "CREATE TABLE IF NOT EXISTS " + TABLE_FITNESS_RECORD +
                " (Date TEXT NOT NULL, " +
                "Workout_ID INT NOT NULL, " +
                "Name TEXT NOT NULL, " +
                "Calories INT NOT NULL, " +
                "IS_MULTIPLIER INT NOT NULL);";
        db.execSQL(CMD);
        //CMD = "ALTER TABLE " + TABLE_FITNESS_RECORD + " ADD PRIMARY KEY (Date, Workout_ID);";
        //db.rawQuery(CMD);
    }
    private void fillDates(){
        Log.d(TAG, "filling dates");
        ContentValues values = new ContentValues();

        values.put("Date", "11/27/15");
        values.put("Workout_ID", 2);
        values.put("Name", "Test");
        values.put("Calories", 200);
        values.put("IS_MULTIPLIER", 0);
        db.insert(TABLE_FITNESS_RECORD, null, values);

        values.put("Date", "11/27/15");
        values.put("Workout_ID", 3);
        values.put("Name", "Test");
        values.put("Calories", 200);
        values.put("IS_MULTIPLIER", 0);
        db.insert(TABLE_FITNESS_RECORD, null, values);
    }
    public static void addWorkoutToToday(int id){
        final String DateTime = DateFormat.getDateFormat(MainActivity.mContext).format(new Date());
        ContentValues values = new ContentValues();
        String CMD = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE _ID = " + id + ";";
        Cursor c = db.rawQuery(CMD, null);
        c.moveToFirst();
        values.put("Date", DateTime);
        values.put("Workout_ID", c.getInt(0));
        values.put("Name", c.getString(1));
        values.put("Calories", c.getFloat(3));
        values.put("IS_MULTIPLIER", c.getInt(4));
        Log.d(TAG, "inserting name " + c.getString(1) + " id " + c.getInt(0) + " date " + DateTime);
        db.insert(TABLE_FITNESS_RECORD, null, values);
        Toast.makeText(MainActivity.mContext, c.getString(1) + " added to todays workout list", Toast.LENGTH_LONG).show();
    }
    //returns an array of integers that correspond to workout_ID's scheduled today
    public static int[] returnTodaysRecords(String date){
        Log.d(TAG, "Return items from date " + date);
        int[] theList;
        String CMD = "SELECT * FROM " + TABLE_FITNESS_RECORD + " WHERE Date ='" + date + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() <= 0){
            return null;
        }
        c.moveToFirst();
        theList = new int[c.getCount()];
        for (int i = 0; !c.isAfterLast(); i++){
            theList[i] = c.getInt(1);
            c.moveToNext();
        }
        return theList;
    }
    public static void deleteFromToday(String date, int ID){
        Log.d(TAG, "date passed " + date + "ID passed " + ID);
        db.delete(TABLE_FITNESS_RECORD, "Workout_ID=" + ID + " AND Date='" + date + "'", null);
    }
    // this will delete entire database
    public void destroyDB(){
        dbContext.deleteDatabase(DATABASE_NAME);
    }
}
