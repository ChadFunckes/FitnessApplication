package ismgapps.fitnessapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFitness extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView name, weight, BMI, BMR, calories;

    public MyFitness() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_fitness, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get references to the textViews
        name = (TextView) getView().findViewById(R.id.nameText);
        weight = (TextView) getView().findViewById(R.id.currWeight);
        BMI = (TextView) getView().findViewById(R.id.BMI);
        BMR = (TextView) getView().findViewById(R.id.BMR);
        calories = (TextView) getView().findViewById(R.id.calories);
        // set the text view texts
        name.setText(MainActivity.user.getName());
        weight.setText(String.valueOf(MainActivity.user.getCur_weight()));
        BMI.setText(String.valueOf(MainActivity.user.getBMI()));
        BMR.setText(String.valueOf(MainActivity.user.getBMR()));
        calories.setText(String.valueOf(MainActivity.user.getCal_needs()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
