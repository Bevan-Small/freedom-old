package com.example.android.freedom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    String[] aucklandItems= {"Auckland item 1", "Auckland item 2", "Auckland item 3", "Auckland item 4", "Auckland item 5"};
    String[] canterburyItems= {"Canterbury item 1", "Canterbury item 2", "Canterbury item 3", "Canterbury item 4", "Canterbury item 5"};
    String[] cenNorthIslItems= {"Central North Island item 1", "Central North Island item 2", "Central North Island item 3", "Central North Island item 4", "Central North Island item 5"};
    // creates a constant MESSAGE to pass into the intent
    public final static String EXTRA_MESSAGE = "com.example.android.freedom.MESSAGE";

    Spinner spRegions;
    ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spRegions = (Spinner) findViewById(R.id.regions_spinner);
        resultListView = (ListView)findViewById(R.id.result_listview);

        // create listener to change result text when the spinner option is changed
        spRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                // Fills out results list view
                populateResultList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                resultListView.setVisibility(View.INVISIBLE);
            }


        });



        // Sends user to detail screen when a list result is clicked
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                String detailText = (String)parent.getItemAtPosition(position);
                intent.putExtra(EXTRA_MESSAGE, detailText);
                startActivity(intent);

            }
        });



        // fills list view


    }

    //////////////////////////////result listview///////////////////////////////////////////////////


    // doesnt work
    public void populateResultList(){

        Spinner spinner = (Spinner)findViewById(R.id.regions_spinner);

        if (spinner.getSelectedItem().toString().equals("Auckland")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_result, R.id.list_result_textview,aucklandItems);
            ListView listView = (ListView)findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        } else if(spinner.getSelectedItem().toString().equals("Canterbury")){
            ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_result, R.id.list_result_textview,canterburyItems);
            ListView listView = (ListView)findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        } else if(spinner.getSelectedItem().toString().equals("Central North Island")){
            ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_result, R.id.list_result_textview,cenNorthIslItems);
            ListView listView = (ListView)findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }
        else {
            ListView listView = (ListView)findViewById(R.id.result_listview);
            listView.setVisibility(View.INVISIBLE);
        }

    }




}



