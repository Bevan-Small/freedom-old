package com.example.android.freedom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String[] aucklandItems = {"Auckland item 1", "Auckland item 2", "Auckland item 3", "Auckland item 4", "Auckland item 5"};
    String[] canterburyItems = {"Canterbury item 1", "Canterbury item 2", "Canterbury item 3", "Canterbury item 4", "Canterbury item 5"};
    String[] cenNorthIslItems = {"Central North Island item 1", "Central North Island item 2", "Central North Island item 3", "Central North Island item 4", "Central North Island item 5"};

    ArrayList<String[]> southlandListItems = new ArrayList();
    ArrayList<String[]> westCoastListItems = new ArrayList();

    // creates a constant MESSAGE to pass into the intent
    public final static String EXTRA_MESSAGE = "com.example.android.freedom.MESSAGE";

    Spinner spRegions;
    ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialises experimental southland Arraylist of String[]

        for (int i = 0; i < 10; i++) {
            String[] temporaryItem = {i + "","Southland"};
            String[] temporaryItem2 = {i +"", "West Coast"};
            southlandListItems.add(temporaryItem);
        }

        for (int i=0;i<southlandListItems.size();i++){
            Log.e("Message to myself", "Array ids before adapter" + southlandListItems.get(i)[0]);
        }

        spRegions = (Spinner) findViewById(R.id.regions_spinner);
        resultListView = (ListView) findViewById(R.id.result_listview);

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
        // Currently taking a single string of data
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                String detailText = (String) parent.getItemAtPosition(position);
                intent.putExtra(EXTRA_MESSAGE, detailText);
                startActivity(intent);

            }
        });

    }


    // works! determines which result array to display
    // adapters implemented in here
    public void populateResultList() {

        Spinner spinner = (Spinner) findViewById(R.id.regions_spinner);

        if (spinner.getSelectedItem().toString().equals("Auckland")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_result, R.id.list_result_textview, aucklandItems);
            ListView listView = (ListView) findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        } else if (spinner.getSelectedItem().toString().equals("Canterbury")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_result, R.id.list_result_textview, canterburyItems);
            ListView listView = (ListView) findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        } else if (spinner.getSelectedItem().toString().equals("Central North Island")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_result, R.id.list_result_textview, cenNorthIslItems);
            ListView listView = (ListView) findViewById(R.id.result_listview);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        } else if (spinner.getSelectedItem().toString().equals("Southland")) {

            // Implementation of the custom adapter


            CustomListAdapter adapter = new CustomListAdapter(this,southlandListItems);
            ListView listView = (ListView) findViewById(R.id.result_listview);

            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            ListView listView = (ListView) findViewById(R.id.result_listview);
            listView.setVisibility(View.INVISIBLE);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Custom adapter that takes a List<String[]> where a single String[] is one entry
    // Works!

    public class CustomListAdapter extends BaseAdapter implements ListAdapter {

        ArrayList<String[]> itemList = new ArrayList();
        Context context;

        public CustomListAdapter(Context context, ArrayList<String[]> itemList) {

            this.itemList = itemList;
            this.context = context;
        }

        public Object getItem(int position){
            return itemList.get(position);
        }

        public long getItemId(int position){
            // This is stupid. Put a long in the array rather than string
            return Long.parseLong((itemList.get(position)[0]));
        }

        public int getCount(){
            return itemList.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Can call getSystemService directly when in the activity. It works though, so I aint touching it
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflatedView = inflater.inflate(R.layout.array_list_result,null);

            String[] entry = (String[])getItem(position);

            if (entry != null) {
                // sets title and body text
                TextView tt1 = (TextView) inflatedView.findViewById(R.id.array_list_result_title);
                TextView tt2 = (TextView) inflatedView.findViewById(R.id.array_list_result_bodytext);

                if (tt1 != null) {
                    tt1.setText(entry[0]);
                }

                if (tt2 != null) {
                    tt2.setText(entry[1]);
                }
            }
            return inflatedView;
        }

    }


}



