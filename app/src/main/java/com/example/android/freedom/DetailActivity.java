package com.example.android.freedom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Message to myself", "Detail activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // fetching the string array from the intent
        Bundle extras = getIntent().getExtras();
        String[] detailArray = extras.getStringArray("String array");

        // received data
        int entryId = Integer.parseInt(detailArray[0]);
        String entryTitle = detailArray[1];
        String entryBodytext = detailArray[2];
        final String entryAddress = detailArray[3];


        // Filling actual fields with data from intent


        TextView tv2 = (TextView) findViewById(R.id.detail_title_textview);
        tv2.setText(entryTitle);

        ImageView imgv1 = (ImageView)findViewById(R.id.detail_imageview);
        int imageId = getResources().getIdentifier("southland"+(entryId), "drawable", getPackageName());
        imgv1.setImageResource(imageId);

        TextView tv3 = (TextView) findViewById(R.id.detail_body_text_view);
        tv3.setText(entryBodytext);

        TextView tv4 = (TextView) findViewById(R.id.detail_address_text_view);
        tv4.setText(entryAddress);

        // Sends user off to maps app
        View addressBlock = findViewById(R.id.detail_address_block);

        addressBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO add functionality for coordinate processing


                // NZ centred coords and zoom @-41.0824,174.7099,6z
                // parsed would be geo:-41.0824,174.7099?z=6&q=example+address+goes+here
                Uri gmmIntentUri = Uri.parse("geo:-41.0824,174.7099?z=6&q="+entryAddress.toLowerCase());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

    }



}
