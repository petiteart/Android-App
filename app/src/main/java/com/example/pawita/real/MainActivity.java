package com.example.pawita.real;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button initialClick = null;
    TextView fillText = null;
    ImageView selectedPictureView = null;
    Bitmap currentBitmap = null;

    int count = 1;
    private final int PICK_IMAGE = 1;
    private final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1) find button by id in xml and set an onclicklistener
        initialClick = findViewById(R.id.button);
        // 2) add a handler method for when the button is clicked
        initialClick.setOnClickListener((View view) -> onClick(view));
        fillText = findViewById(R.id.textview);
        // 3) Look up how you can select an image file to open on screen.
        selectedPictureView = findViewById(R.id.selectedPictureView);

    }
    private void onClick(View v){
        updateText();
        choosePicture();
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }

    private void updateText() {
        String countText = "Hello Guapa, you have "+count;
        if(count == 1){
            fillText.setText(countText + " time.");
        }
        else {
            fillText.setText(countText + " times.");
        }
        count = count+1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE & resultCode == RESULT_OK){
            Uri pictureUri = data.getData();
            Log.i(LOG_TAG, "Uri: " + pictureUri );
            selectedPictureView.setImageURI(pictureUri);
            //currentBitmap = BitmapFactory.decodeFile(pictureUri.);
            try {
                this.currentBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver() , pictureUri);
            }
            catch (Exception e) {
                //handle exception
            }

            int averageColour = calculateAverageColour();
            fillText.setBackgroundColor(averageColour);
        }
    }
    private int calculateAverageColour(){
        int countX = currentBitmap.getWidth();
        int countY = currentBitmap.getHeight();
        List<Integer> redValueArray = new ArrayList<>();
        List<Integer> greenValueArray = new ArrayList<>();
        List<Integer> blueValueArray = new ArrayList<>();
        for (int i = 0; i <  countX; i+=2) {
            for (int j = 0; j <  countY; j+=2) {
                int colour = currentBitmap.getPixel(i, j);
                int red = Color.red(colour);
                int blue = Color.blue(colour);
                int green = Color.green(colour);
                int alpha = Color.alpha(colour);
                //int[] colourValue = {red, green, blue};
                //Log.i(LOG_TAG, "colourValue = " + colourValue );
                //return Color.rgb(red, green, blue);
                redValueArray.add(red);
                greenValueArray.add(green);
                blueValueArray.add(blue);

                //return colourValue;
            }
        }
        int sumRed = 0; int sumGreen = 0; int sumBlue = 0;
        int countRgb =0;
        for (int k=0; k< redValueArray.size();k++){
            sumRed += redValueArray.get(k);
            sumGreen += greenValueArray.get(k);
            sumBlue += blueValueArray.get(k);
            countRgb++;
        }
        double averageRed = sumRed/countRgb;
        double averageGreen = sumGreen/countRgb;
        double averageBlue = sumBlue/countRgb;
        double averageColour = Color.rgb((int) averageRed,(int) averageGreen,(int) averageBlue);
        return (int) averageColour;
    }
}