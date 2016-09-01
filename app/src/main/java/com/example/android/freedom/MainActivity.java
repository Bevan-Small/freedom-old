package com.example.android.freedom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spRegions;
    ListView resultListView;
    CustomListAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spRegions = (Spinner) findViewById(R.id.regions_spinner);
        resultListView = (ListView) findViewById(R.id.result_listview);

        // creates listener to change result text when the spinner option is changed
        spRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
                                       long id) {
                populateResultList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                resultListView.setVisibility(View.INVISIBLE);
            }
        });

        // Sends user to detail screen when a list item is clicked. Passes item info as a string array
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                String[] detailArray = (String[]) parent.getItemAtPosition(position);
                intent.putExtra("String array", detailArray);
                startActivity(intent);
            }
        });

    }


    ///////////////////////////////////List item logic/////////////////////////////////////////////

    /**
     * Determines which result array to display and displays it
     */
    public void populateResultList() {

        String placeName = spRegions.getSelectedItem().toString();

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
            resultListView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * updates list info based on placeName passed in. makes the list visible
     */
    public void updateListResult(String placeName) {
        // TODO rather than use a new adapter, clear old one and add new items

        ArrayList<String[]> placeDataEntries = readCsv(placeName.replaceAll(" ", "_").toLowerCase());

        if (placeDataEntries.size() > 0) {
            resultsAdapter = new CustomListAdapter(this, placeDataEntries);
            resultListView.setAdapter(resultsAdapter);
            resultListView.setVisibility(View.VISIBLE);
        } else {
            resultListView.setVisibility(View.INVISIBLE);
        }

    }


    /////////////////////////////////Adapter///////////////////////////////////////////////////

    /**
     * Custom adapter that takes a List<String[]> where a single String[] is one entry
     */
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
            // TODO review when id system is changed
            return Long.parseLong((itemList.get(position)[0]));
        }

        public int getCount() {
            return itemList.size();
        }

        // this inflates the sublayout in the list and populates the text views and imageview
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Can call getSystemService directly when in the activity. It works though, so I aint touching it
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflatedView = inflater.inflate(R.layout.array_list_result_with_cardview, null);

            String[] entry = (String[]) getItem(position);

            if (entry != null) {
                // sets title and body text
                TextView tt1 = (TextView) inflatedView.findViewById(R.id.array_list_cardview_result_title);
                TextView tt2 = (TextView) inflatedView.findViewById(R.id.array_list_cardview_result_bodytext);
                ImageView im1 = (ImageView) inflatedView.findViewById(R.id.array_list_cardview_result_imageview);

                if (tt1 != null) {
                    tt1.setText(entry[1]);
                }

                if (tt2 != null) {
                    tt2.setText(entry[2]);

                    int imageId = getResources().getIdentifier("southland" + (getItemId(position)), "drawable", getPackageName());
                    im1.setImageResource(imageId);
                }
            }
            return inflatedView;
        }

    }


    ////////////////////////////////Csv reader//////////////////////////////////////////////

    /**
     * Reads in file named placeName.csv. Parses and adds all lines to result ArrayList
     * returns result
     */
    public ArrayList<String[]> readCsv(String placeName) {

        ArrayList<String[]> result = new ArrayList<String[]>();

        BufferedReader br = null;

        try {

            InputStream is = getResources().openRawResource(getResources().getIdentifier(placeName,
                    "raw", getPackageName()));
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr, 8192); // 2nd arg is buffer size

            String line;
            while (true) {
                line = br.readLine();

                if (line == null) {
                    break;
                }
                result.add(parseCsvLine(line));
            }

        } catch (IOException e) {
            Log.e("FILE ERROR", "Unable to read place data file: " + placeName + ".txt. " + e);
        } finally {
            try {
                br.close();

            } catch (IOException e) {
                Log.e("FILE ERROR", "Failed to close place data file: " + placeName + ".txt. " + e);
            } catch (NullPointerException e) {
                Log.e("FILE ERROR", "Unable to read place data file: " + placeName + ".txt. " + e);
            }
        }

        return result;
    }

    /**
     * Takes in a string of format "id,title,bodytext,address" and
     * returns a String[] = {id, title, bodytext, address}
     */
    private static String[] parseCsvLine(String line) {

        // Break line into components
        String[] entry = new String[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            // breaks line into {id, title, bodytext, address} components
            // marksFlag signals if a " mark has been read, to accommodate entries with commas
            // that have been surrounded in " marks

            boolean marksFlag = false;
            StringBuilder dataPiece = new StringBuilder();

            while (index < line.length()) {
                if ((line.charAt(index) == ',') && (!marksFlag)) {
                    // breaks loop if comma found and marksFlag is not true
                    // skips comma as well
                    index++;
                    break;
                }

                if (line.charAt(index) == '\"') {
                    // flip flag and skip append
                    marksFlag = !marksFlag;
                } else {
                    // add char
                    dataPiece.append(line.charAt(index));
                }

                index++;
            }
            entry[i] = dataPiece.toString();
        }
        return entry;
    }

    ///////////////////////// Menu stuff///////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.action_email:
                // send email to Bevan
                String emailBody = "Please add a title, description and address for your " +
                        "submission, and attach a photo. Thanks for submitting!";

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "bevan.r.small@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "New Freedom submission");
                intent.putExtra(Intent.EXTRA_TEXT, emailBody);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



