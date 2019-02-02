package com.example.pawita.real;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button addClick = null;
    Bitmap currentBitmap = null;
    TextView gridText = null;
    GridView gridView = null;
    ColourCalculator colourCalculator = null;

    int count = 1;
    private final int PICK_IMAGE = 1;
    private final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colourCalculator = new ColourCalculator();
        setContentView(R.layout.activity_main);
        // 1) find button by id in xml and set an onclicklistener
        addClick = findViewById(R.id.addButton);
        // 2) add a handler method for when the button is clicked
        addClick.setOnClickListener((View view) -> onClick(view));

        // averagecolour does not exist anymore
        gridText = findViewById(R.id.averagecolour);

        gridView = findViewById(R.id.gridview);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),GridItemActivity.class);
                startActivity(intent);
            }
        });

    }
    private void onClick(View v){
        /*
         * THIS IS NOW OBSELETE, gridText does no longer exist - updateText();
         */
        choosePicture();
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }
    @Deprecated //deprecation method as updatetext refers to gridtext which no longer exists
    private void updateText() {
        String countText = "you have "+count;
        if(count == 1){
           gridText.setText(countText + " time.");
        }
        else {
            gridText.setText(countText + " times.");
        }
        count = count+1;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE & resultCode == RESULT_OK){
            Uri pictureUri = data.getData();
            Log.i(LOG_TAG, "Uri: " + pictureUri );
            //galleryImage.setImageURI(pictureUri);

            //currentBitmap = BitmapFactory.decodeFile(pictureUri.);
            try {
                this.currentBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , pictureUri);
                // get Bitmap orientation
                ExifInterface exif = null;
                try {
                    // this is throwing an exception, it's expecting a filename but instead receives log tag, consider use of this ExifInterface
                    // DO YOU NEED AN EXIF TAG?
                    exif = new ExifInterface(LOG_TAG);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                //handle exception
            }

            int averageColour = colourCalculator.calculateAverageColour(currentBitmap);
  //          gridText.setBackgroundColor(averageColour);
        }
    }



    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return PICK_IMAGE;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data, null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.averagecolour);
            ImageView image = view1.findViewById(R.id.image);
            //image.setImageURI(pictureUri);
            return view1;
        }
    }
}