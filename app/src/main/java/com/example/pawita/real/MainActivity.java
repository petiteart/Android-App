package com.example.pawita.real;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button initialClick = null;
    TextView fillText = null;
    ImageView selectedPictureView = null;

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
        }
    }
}