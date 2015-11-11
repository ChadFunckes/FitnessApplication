package ismgapps.fitnessapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class User {
    private final String TAG = "User Class Activity";

    private String name, email, phone;
    private float weight, height, BMI, BMR, start_weight, cal_needs, cur_weight;
    private int start_lvl, cur_lvl;

    //empty constructor
    public User() {

    }

    // this method fills the user data from the shared preferences information
    public void fillUserFromPrefs(SharedPreferences sharedPreferences) {
        this.name = sharedPreferences.getString("name", null);
        this.weight = sharedPreferences.getFloat("weight", 0);
        this.height = sharedPreferences.getFloat("height", 0);
        this.BMI = sharedPreferences.getFloat("BMI", 0);
        this.BMR = sharedPreferences.getFloat("BMR", 0);
        this.start_weight = sharedPreferences.getFloat("Starting_Weight", 0);
        this.start_lvl = sharedPreferences.getInt("Start_LVL", 0);
        this.cal_needs = sharedPreferences.getFloat("Cal_Needs", 0);
        this.cur_weight = sharedPreferences.getFloat("Current_Weight", 0);
        this.cur_lvl = sharedPreferences.getInt("Cur_Lvl", 0);
        this.email = sharedPreferences.getString("Email", null);
        this.phone = sharedPreferences.getString("Phone", null);
    }

    //@TODO this is a sample to get through the database
    public void buildDummy(SQLiteDatabase db, SharedPreferences.Editor sharedEditor){
        // set query to execute
        String query = "SELECT * FROM Users;";
        // set a cursor to run the query
        Cursor c = db.rawQuery(query, null);
        // move to the first row
        c.moveToFirst();
        // clear the shared preferences
        sharedEditor.clear();
        // set both the shared prefernces and the user elements...
        sharedEditor.putString("name", name = c.getString(1));
        sharedEditor.putFloat("weight", weight = c.getFloat(2));
        sharedEditor.putFloat("height", height = c.getFloat(3));
        sharedEditor.putFloat("BMI", BMI = c.getFloat(4));
        sharedEditor.putFloat("BMR", BMR = c.getFloat(5));
        sharedEditor.putFloat("Starting_Weight", start_weight = c.getFloat(6));
        sharedEditor.putInt("Start_LVL", start_lvl = c.getInt(7));
        sharedEditor.putFloat("Cal_Needs", cal_needs = c.getFloat(8));
        sharedEditor.putFloat("Current_Weight", cur_weight = c.getFloat(9));
        sharedEditor.putInt("Cur_Lvl", cur_lvl = c.getInt(10));
        sharedEditor.putString("Email", email = c.getString(11));
        sharedEditor.putString("Phone", phone = c.getString(12));
        // commit changes to shared preferences
        sharedEditor.commit();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public void setBMR(float BMR) {
        this.BMR = BMR;
    }

    public void setStart_weight(float start_weight) {
        this.start_weight = start_weight;
    }

    public void setCal_needs(float cal_needs) {
        this.cal_needs = cal_needs;
    }

    public void setCur_weight(float cur_weight) {
        this.cur_weight = cur_weight;
    }

    public void setStart_lvl(int start_lvl) {
        this.start_lvl = start_lvl;
    }

    public void setCur_lvl(int cur_lvl) {
        this.cur_lvl = cur_lvl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public float getBMI() {
        return BMI;
    }

    public float getBMR() {
        return BMR;
    }

    public float getStart_weight() {
        return start_weight;
    }

    public float getCal_needs() {
        return cal_needs;
    }

    public float getCur_weight() {
        return cur_weight;
    }

    public int getStart_lvl() {
        return start_lvl;
    }

    public int getCur_lvl() {
        return cur_lvl;
    }


}
