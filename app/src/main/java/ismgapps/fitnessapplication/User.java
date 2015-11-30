package ismgapps.fitnessapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {
    private final String TAG = "User Class Activity";

    private String name, password, email, phone;
    private float weight, height, BMI, BMR, start_weight, cal_needs, cur_weight;
    private int start_lvl, cur_lvl;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //empty constructor
    public User(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences; // set the preferences set
        this.editor = sharedPreferences.edit(); // set the editor
    }

    // this method fills the user data from the shared preferences information
    public void fillUserFromPrefs() {
        this.name = sharedPreferences.getString("name", null);
        this.password = sharedPreferences.getString("password", null);
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

    // @TODO this is a sample to get through the database, safe to delete buildDummy method on production
    public void buildDummy(SQLiteDatabase db){
        // set query to execute
        String query = "SELECT * FROM Users;";
        // set a cursor to run the query
        Cursor c = db.rawQuery(query, null);
        // move to the first row
        c.moveToFirst();
        // clear the shared preferences
        editor.clear();
        // set both the shared prefernces and the user elements...
        editor.putString("name", name = c.getString(1));
        editor.putString("password", password = c.getString(2));
        editor.putFloat("weight", weight = c.getFloat(3));
        editor.putFloat("height", height = c.getFloat(4));
        editor.putFloat("BMI", BMI = c.getFloat(5));
        editor.putFloat("BMR", BMR = c.getFloat(6));
        editor.putFloat("Starting_Weight", start_weight = c.getFloat(7));
        editor.putInt("Start_LVL", start_lvl = c.getInt(8));
        editor.putFloat("Cal_Needs", cal_needs = c.getFloat(9));
        editor.putFloat("Current_Weight", cur_weight = c.getFloat(10));
        editor.putInt("Cur_Lvl", cur_lvl = c.getInt(11));
        editor.putString("Email", email = c.getString(12));
        editor.putString("Phone", phone = c.getString(13));
        // commit changes to shared preferences
        editor.commit();
    }
    // removed a user from sharedPreferences
    public void logOut(){
        editor.clear().commit();
    }

    public void commitUserToDB(){
        DBhandler.InsertUser(this);
        commitUserToPrefs();
    }

    public void commitUserToPrefs(){
        editor.clear();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putFloat("weight", weight);
        editor.putFloat("height", height);
        editor.putFloat("BMI", BMI);
        editor.putFloat("BMR", BMR);
        editor.putFloat("Starting_Weight", start_weight);
        editor.putInt("Start_LVL", start_lvl);
        editor.putFloat("Cal_Needs", cal_needs);
        editor.putFloat("Current_Weight", cur_weight);
        editor.putInt("Cur_Lvl", cur_lvl);
        editor.putString("Email", email);
        editor.putString("Phone", phone);
        editor.commit();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) { this.password = password; }

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

    public String getPassword() { return password; }

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
