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

    //@TODO this is a sample to get through the database
    public void buildDummy(SQLiteDatabase db, SharedPreferences sharedPreferences){
        String query = "SELECT * FROM Users;";

        Cursor c = db.rawQuery(query ,null);
        c.moveToFirst();

        sharedPreferences.edit().putString("name", name = c.getString(1));
        sharedPreferences.edit().putFloat("weight", weight = c.getFloat(2));
        sharedPreferences.edit().putFloat("height", height = c.getFloat(3));

        Log.i(TAG, "name is: "+ name);
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
