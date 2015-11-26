package ismgapps.fitnessapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Today extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView date;
    private ListView list;

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
        // @TODO change the weight and connect the button

        //int array contains workout ID's on schedule for today
        final int workoutIDs[] = DBhandler.returnTodaysRecords(DateTime);
        // if workoutID's returned null there is nothing on the schedule....skip all displaying
        if (workoutIDs != null) { //@TODO change the text views for calories in this if section
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
                        items2.add(works);
                        works = null;
                    }
                }
            }

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, items);

            list.setAdapter(itemsAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.d("today item listener ", "Item selcted");
                    float calories;
                    if (items2.get(position).wMultiplier == 0)
                        calories = items2.get(position).wCalories;
                    else calories = items2.get(position).wCalories * MainActivity.user.getWeight();

                    new AlertDialog.Builder(MainActivity.mContext)
                            .setTitle("Workout Review")
                            .setMessage("Workout Desription:\n" + items2.get(position).wDescription + "\n\nCalories: " + calories)
                            .setPositiveButton("Delete from Schedule", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("pp", "position " + position);
                                    DBhandler.deleteFromToday(DateTime, workoutIDs[position]);
                                    mListener.onFragmentInteraction(position);
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
    }

    private class workoutDetail{
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(int test);
    }

}
