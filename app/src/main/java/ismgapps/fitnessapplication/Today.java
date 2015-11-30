package ismgapps.fitnessapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Today extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView date, weight, dailyCals, needCals;
    private ListView list;
    private Button addWorkout;

    public Today() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String DateTime = DateFormat.getDateFormat(MainActivity.mContext).format(new Date());
        date = (TextView) getView().findViewById(R.id.date);
        date.setText(DateTime);
        list = (ListView) getView().findViewById(R.id.workList);
        weight = (TextView)getView().findViewById(R.id.weight);
        weight.setText(String.valueOf(MainActivity.user.getCur_weight()));
        dailyCals = (TextView)getView().findViewById(R.id.calorieDisplay);
        needCals = (TextView)getView().findViewById(R.id.DailyCaloriesText);
        needCals.setText(String.format("%.0f", MainActivity.user.getCal_needs()));
        //int array contains workout ID's on schedule for today
        final int workoutIDs[] = DBhandler.returnTodaysRecords(DateTime);
        // if workoutID's returned null there is nothing on the schedule....skip all displaying
        if (workoutIDs != null) {
            float calorieCounter = 0;
            List<String> items = new ArrayList<String>(); // list of names for display
            final List<workoutDetail> items2 = new ArrayList<workoutDetail>(); // detail items for calculations
            workoutDetail works;
            for (int i = 0; i < workoutIDs.length; i++) {
                for (int x = 0; x < WorkoutData.workouts.size(); x++) {
                    if (WorkoutData.workouts.get(x).getID() == workoutIDs[i]) {
                        items.add(WorkoutData.workouts.get(x).getName());
                        works = new workoutDetail();
                        works.wName = WorkoutData.workouts.get(x).getName();
                        works.wDescription = WorkoutData.workouts.get(x).getDescription();
                        works.wCalories = WorkoutData.workouts.get(x).getCal_count();
                        works.wMultiplier = WorkoutData.workouts.get(x).getMultiplier();
                        // if the calorie count is raw increase the daily calories by the calorie amount
                        if (works.wMultiplier == 0) calorieCounter = calorieCounter + works.wCalories;
                        // else if the there is a multiplier (per pound calories) multiply calorie number by the users current weight
                        else calorieCounter = calorieCounter + (works.wCalories * MainActivity.user.getCur_weight());
                        items2.add(works);
                        works = null;
                    }
                }
            }
            dailyCals.setText(String.format("%.0f", calorieCounter));
            // if the calories are within 100 of required...change color to green
            if (MainActivity.user.getCal_needs() <= (calorieCounter + 100)) dailyCals.setTextColor(Color.GREEN);
            // if they are from 300 blue (yellow to hard to read)
            else if (MainActivity.user.getCal_needs() <= (calorieCounter + 300)) dailyCals.setTextColor(Color.BLUE);
            // else the count is red
            else dailyCals.setTextColor(Color.RED);
            // show the list of current workouts
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, items);
            // list is the scroll list in the middle with the current workout listed, and this section is what happens when you click on one
            list.setAdapter(itemsAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.d("today item listener ", "Item selcted");
                    float calories;
                    if (items2.get(position).wMultiplier == 0) // if no multiplier show straight up calories
                        calories = items2.get(position).wCalories;
                    else calories = items2.get(position).wCalories * MainActivity.user.getCur_weight(); // else if there is a multiplier so the math

                    new AlertDialog.Builder(MainActivity.mContext)
                            .setTitle("Workout Review")
                            .setMessage("Workout Desription:\n" + items2.get(position).wDescription + "\n\nCalories: " + String.format("%.0f", calories))
                            .setPositiveButton("Delete from Schedule", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("pp", "position " + position);
                                    DBhandler.deleteFromToday(DateTime, workoutIDs[position]);
                                    mListener.onFragmentInteraction(position); // call back to main will delete this fragement and replace it (reload data)
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // don't do anything here
                                }
                            }).show();
                }
            });
        } // end if workoutID's is not null section
        addWorkout = (Button) getView().findViewById(R.id.addWorkoutButton);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                startActivity(intent);
            }
        });
    }
    // class used as structure to keep workout data details from the list
    private class workoutDetail {
        public String wName;
        public String wDescription;
        public Float wCalories;
        public int wMultiplier;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    static interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int test);
    }
}
