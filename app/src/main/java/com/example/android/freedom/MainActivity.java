package com.example.android.freedom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // creates a constant MESSAGE to pass into the intent
    public final static String EXTRA_MESSAGE = "com.example.android.freedom.MESSAGE";

    Spinner spRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spRegions = (Spinner) findViewById(R.id.regions_spinner);


        // create listener to change result text when the spinner option is changed
        spRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                makeResultVisible();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                makeResultInvisible();
            }
        });


    }

    // Makes the result view viewable if the correct region is selected
    // Changes result text according to region
    public void makeResultVisible() {
        if (spRegions.getSelectedItem().toString().equals("Auckland")) {
            TextView tv = (TextView) findViewById(R.id.spinner_result);
            tv.setText(R.string.result_auckland);
            tv.setVisibility(View.VISIBLE);
        } else if(spRegions.getSelectedItem().toString().equals("Canterbury")){
            TextView tv = (TextView) findViewById(R.id.spinner_result);
            tv.setText(R.string.result_canterbury);
            tv.setVisibility(View.VISIBLE);
        } else {
            TextView tv = (TextView) findViewById(R.id.spinner_result);
            tv.setText(null);
            makeResultInvisible();
        }
    }

    /** makes the spinner result text invisible */
    public void makeResultInvisible() {
        TextView tv = (TextView) findViewById(R.id.spinner_result);
        tv.setVisibility(View.INVISIBLE);
    }

    /** Goes to detail selected*/
    public void goToDetail (View view){
        // creates an intent, puts the text from the spinner result text view in it, and
        // starts and activity of the type specified in the intent
        Intent intent = new Intent(this, DetailActivity.class);
        TextView tv = (TextView)findViewById(R.id.spinner_result);
        String detailText = tv.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, detailText);
        startActivity(intent);
    }
}



