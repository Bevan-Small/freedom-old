package com.example.android.freedom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Message to myself", "Detail activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // fetching the string array from the intent
        Bundle extras = getIntent().getExtras();
        String[] detailArray = extras.getStringArray("String array");

        int entryId = Integer.parseInt(detailArray[0]);
        String entryTitle = detailArray[1];
        String entryBodytext = detailArray[2];
        String entryAddress = detailArray[3];


        // Tester textview
        TextView tv = (TextView) findViewById(R.id.detail_title);
        tv.setText(entryId + " " + entryTitle);

        // Filling actual fields with data from intent
        TextView tv1 = (TextView) findViewById(R.id.detail_id_textview);
        tv1.setText(entryId+"");

        TextView tv2 = (TextView) findViewById(R.id.detail_title_textview);
        tv2.setText(entryTitle);

        ImageView imgv1 = (ImageView)findViewById(R.id.detail_imageview);
        int imageId = getResources().getIdentifier("southland"+(entryId+1), "drawable", getPackageName());
        imgv1.setImageResource(imageId);

        TextView tv3 = (TextView) findViewById(R.id.detail_body_text_view);
        tv3.setText(entryBodytext);

        TextView tv4 = (TextView) findViewById(R.id.detail_address_text_view);
        tv4.setText(entryAddress);

        // trialling read/write stuff

        // readData();

    }

    /////// Doesn't work!
    // Can't write to the app files idiot
    public void writeData() {
        PlaceData auckland = new PlaceData();
        String dataFileName = "Auckland.bin"; //@res/values/
        try {
            FileOutputStream fs = new FileOutputStream(dataFileName);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(auckland);

            os.close();
            fs.close();

        } catch (FileNotFoundException e) {
            Log.e("Read error", "File not found: " + dataFileName);
        } catch (IOException e) {
            Log.e("Read error", "Class not found: " + dataFileName);
        }
    }


    // Does work! Takes a premade file produced in eclipse, and puts the text on display!
    // Probably chuck in an asynctask or other thread
    // miraculous
    public void readData() {

        String dataFileName = "Auckland.bin"; //@res/values/

        try {
            InputStream ins = getResources().openRawResource(getResources().getIdentifier("auckland_single_item","raw", getPackageName()));

            ObjectInputStream os = new ObjectInputStream(ins);

            PlaceData place = (PlaceData) os.readObject();

            TextView tv1 = (TextView) findViewById(R.id.detail_id_textview);
            tv1.setText(place.getData()[0]);

            TextView tv2 = (TextView) findViewById(R.id.detail_title_textview);
            tv2.setText(place.getData()[1]);

            TextView tv3 = (TextView) findViewById(R.id.detail_body_text_view);
            tv3.setText(place.getData()[2]);

            TextView tv4 = (TextView) findViewById(R.id.detail_address_text_view);
            tv4.setText(place.getData()[3]);

            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            Log.e("Read error", "File not found: " + dataFileName);
        } catch (IOException e) {
            Log.e("Read error", "File not readable: " + dataFileName);
            ;
        } catch (ClassNotFoundException e) {
            Log.e("Read error", "Class not found");
        }
    }





}
