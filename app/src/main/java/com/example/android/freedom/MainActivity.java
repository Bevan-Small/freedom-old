package com.example.android.freedom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // creates a constant MESSAGE to pass into the intent
    public final static String EXTRA_MESSAGE = "com.example.android.freedom.MESSAGE";

    Spinner spRegions;
    ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        // Sends user to detail screen when a list result is clicked. passes data entry
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Passes string array. Can only be used with southland and west coast currently
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                String[] detailArray = (String[]) parent.getItemAtPosition(position);
                intent.putExtra("String array", detailArray);
                startActivity(intent);

            }
        });

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // works! determines which result array to display and displays it
    // push to async task or other thread?

    public void populateResultList() {

        // can probably tidy this up referring globally to the spRegions spinner
        Spinner spinner = (Spinner) findViewById(R.id.regions_spinner);
        String placeName = spinner.getSelectedItem().toString();

        if (placeName.equals("Auckland")) {
            updateListResult(placeName);

        } else if (placeName.equals("Northland")) {
            updateListResult(placeName);

        } else if (placeName.equals("Central North Island")) {
            updateListResult(placeName);

        } else if (placeName.equals("Lower North Island")) {
            updateListResult(placeName);

        } else if (placeName.equals("Marlborough and Tasman")) {
            updateListResult(placeName);

        } else if (placeName.equals("Canterbury")) {
            updateListResult(placeName);

        } else if (placeName.equals("West Coast")) {
            updateListResult(placeName);

        } else if (placeName.equals("Otago")) {
            updateListResult(placeName);

        } else if (placeName.equals("Southland")) {
            updateListResult(placeName);

        } else {
            ListView listView = (ListView) findViewById(R.id.result_listview);
            listView.setVisibility(View.INVISIBLE);
        }

    }

    //
    public void updateListResult(String placeName){
        // Implementation of the custom adapter with data reading
        // Tidy up by referring to the globally defined custom adapter?
        CustomListAdapter adapter = new CustomListAdapter(this, readArrayData(placeName.replaceAll(" ","_").toLowerCase()));
        ListView listView = (ListView) findViewById(R.id.result_listview);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
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

        public Object getItem(int position) {
            return itemList.get(position);
        }

        public long getItemId(int position) {
            // This is stupid. Put a long in the array rather than string
            return Long.parseLong((itemList.get(position)[0]));
        }

        public int getCount() {
            return itemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // this does the heavy lifting, inflating the sublayout in the list and populating the textviews
            // Can call getSystemService directly when in the activity. It works though, so I aint touching it
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflatedView = inflater.inflate(R.layout.array_list_result, null);

            String[] entry = (String[]) getItem(position);

            if (entry != null) {
                // sets title and body text
                TextView tt1 = (TextView) inflatedView.findViewById(R.id.array_list_result_title);
                TextView tt2 = (TextView) inflatedView.findViewById(R.id.array_list_result_bodytext);
                ImageView im1 = (ImageView) inflatedView.findViewById(R.id.array_list_result_imageview);

                if (tt1 != null) {
                    tt1.setText(entry[0]);
                }

                if (tt2 != null) {
                    tt2.setText(entry[1]);

                    int imageId = getResources().getIdentifier("southland" + (getItemId(position) + 1), "drawable", getPackageName());
                    im1.setImageResource(imageId);
                }
            }
            return inflatedView;
        }

    }

    // Reads data fom serialised file. Returns arraylist to be passed into adapter
    // Takes in placeName to find file
    public ArrayList<String[]> readArrayData(String placeName){

        try {
            InputStream ins = getResources().openRawResource(getResources().getIdentifier(placeName,"raw", getPackageName()));
            ObjectInputStream os = new ObjectInputStream(ins);
            EntryData data = (EntryData)os.readObject();

            os.close();
            ins.close();
            return data.getData();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}



