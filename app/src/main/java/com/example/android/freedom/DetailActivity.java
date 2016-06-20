package com.example.android.freedom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Log.e("Passed message", message);
        TextView tv = (TextView) findViewById(R.id.detail_title);
        tv.setText(message);

        // trialling read/write stuff

        readData();

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
    // miraculous
    public void readData() {

        String dataFileName = "Auckland.bin"; //@res/values/

        try {
            InputStream ins = getResources().openRawResource(getResources().getIdentifier("auckland","raw", getPackageName()));

            ObjectInputStream os = new ObjectInputStream(ins);

            PlaceData place = (PlaceData) os.readObject();

            TextView tv1 = (TextView) findViewById(R.id.id_text_view);
            tv1.setText(place.getData()[0]);

            TextView tv2 = (TextView) findViewById(R.id.title_text_view);
            tv2.setText(place.getData()[1]);

            TextView tv3 = (TextView) findViewById(R.id.body_text_view);
            tv3.setText(place.getData()[2]);

            TextView tv4 = (TextView) findViewById(R.id.address_text_view);
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
