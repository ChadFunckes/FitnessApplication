package ismgapps.fitnessapplication;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Recipies extends Fragment {
    Button b1000,b1200,b1500,b1800;
    ImageView jpg1000,jpg1200,jpg1500,jpg1800;

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
        cal1000();
        cal1200();
        cal1500();
        cal1800();
    }

    public void cal1000(){
        jpg1000 = (ImageView) getView().findViewById(R.id.imageView3);
        b1000 = (Button) getView().findViewById(R.id.button1000);
        b1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1000.setImageResource(R.drawable.image1000);
                jpg1000.setScaleType(ImageView.ScaleType.FIT_END);
            }
        });
    }

    public void cal1200(){
        jpg1200 = (ImageView) getView().findViewById(R.id.imageView3);
        b1200 = (Button) getView().findViewById(R.id.button1200);
        b1200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1200.setImageResource(R.drawable.image1200);
                jpg1200.setScaleType(ImageView.ScaleType.FIT_END);
            }
        });
    }

    public void cal1500(){
        jpg1500 = (ImageView) getView().findViewById(R.id.imageView3);
        b1500 = (Button) getView().findViewById(R.id.button1500);
        b1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1500.setImageResource(R.drawable.image1500);
                jpg1500.setScaleType(ImageView.ScaleType.FIT_END);
            }
        });
    }

    public void cal1800(){
        jpg1800 = (ImageView) getView().findViewById(R.id.imageView3);
        b1800 = (Button) getView().findViewById(R.id.button1800);
        b1800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jpg1800.setImageResource(R.drawable.image1800);
                jpg1800.setScaleType(ImageView.ScaleType.FIT_END);
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
        public void onFragmentInteraction(Uri uri);
    }

}
