package ismgapps.fitnessapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutData {
    private int ID;
    private String name;
    private String description;
    private float cal_count;
    private int multiplier;

    public static List<WorkoutData> workouts = getWorkouts();
    // build initial list from SQL
    private static List<WorkoutData> getWorkouts(){
        List<WorkoutData> theList = new ArrayList<WorkoutData>();
        WorkoutData temp;
        String query = "SELECT * FROM Workouts;";
        Cursor c = DBhandler.db.rawQuery(query, null);
        c.moveToFirst();
        Log.d("Workout Data Class", "Starting to fill list");

        while (!c.isAfterLast()){
            temp = new WorkoutData(c.getInt(0), c.getString(1), c.getString(2), c.getFloat(3), c.getInt(4));
            theList.add(temp);
            temp = null;
            c.moveToNext();
        }
        Log.d("Workout Data Class", "List Filled");
        return theList;
    }
    // constructor to build object where an ID is already known (ie. from database)
    public WorkoutData(int id, String name, String description, float cal_count, int multiplier) {
        this.ID = id;
        this.name = name;
        this.description = description;
        this.cal_count = cal_count;
        this.multiplier = multiplier;
    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getID(){ return ID; }

    public float getCal_count() {
        return cal_count;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String toString() {
        return this.name;
    }
}
