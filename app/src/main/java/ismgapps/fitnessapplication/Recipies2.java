package ismgapps.fitnessapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Recipies2 extends AppCompatActivity {
    Button b1000,b1200,b1500;
    ImageView jpg1000,jpg1200,jpg1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                cal1000();
                cal1200();
                cal1500();
            }
        });
    }
    public void cal1000(){
        jpg1000 = (ImageView) findViewById(R.id.imageView3);
        b1000 = (Button) findViewById(R.id.button2);
        b1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1000.setImageResource(R.drawable.p1000);
                jpg1000.setScaleType(ImageView.ScaleType.CENTER);
            }
        });
    }

    public void cal1200(){
        jpg1200 = (ImageView) findViewById(R.id.imageView3);
        b1200 = (Button) findViewById(R.id.button3);
        b1200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1200.setImageResource(R.drawable.p1200);
                jpg1200.setScaleType(ImageView.ScaleType.CENTER);
            }
        });
    }

    public void cal1500(){
        jpg1500 = (ImageView) findViewById(R.id.imageView3);
        b1500 = (Button) findViewById(R.id.button4);
        b1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg1500.setImageResource(R.drawable.p1500);
                jpg1500.setScaleType(ImageView.ScaleType.CENTER);
            }
        });
    }

}
