package ismgapps.fitnessapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Recipies extends Fragment {
    Button b1000,b1200,b1500,b1800;
    ImageView jpg;

    public int buttonPushed = 0;  // keeps track of the button pushed for state change

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipies, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle onSavedInstanceState){
        super.onViewCreated(view, onSavedInstanceState);
        jpg = (ImageView) getView().findViewById(R.id.imageView3);
        cal1000();
        cal1200();
        cal1500();
        cal1800();

        Bundle bundle = getArguments();
        if (bundle != null)
            buttonPushed = bundle.getInt("button");

        if (buttonPushed >1 ){
            if (buttonPushed == 1000){
                jpg.setImageResource(R.drawable.image1000);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
            }
            else if (buttonPushed == 1200){
                jpg.setImageResource(R.drawable.image1200);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
            }
            else if (buttonPushed == 1500){
                jpg.setImageResource(R.drawable.image1500);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
            }
            else  if (buttonPushed == 1800){
                jpg.setImageResource(R.drawable.image1800);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
            }
        }
    }

    public void cal1000(){
        b1000 = (Button) getView().findViewById(R.id.button1000);
        b1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1000);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                mListener.onFragmentInteraction(0,1000);
            }
        });
    }

    public void cal1200(){
        b1200 = (Button) getView().findViewById(R.id.button1200);
        b1200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1200);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                mListener.onFragmentInteraction(0,1200);
            }
        });
    }

    public void cal1500(){
        b1500 = (Button) getView().findViewById(R.id.button1500);
        b1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1500);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                mListener.onFragmentInteraction(0,1500);
            }
        });
    }

    public void cal1800(){
        b1800 = (Button) getView().findViewById(R.id.button1800);
        b1800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1800);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                mListener.onFragmentInteraction(0, 1800);
            }
        });
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
        public void onFragmentInteraction(int x, int y);
    }

}
