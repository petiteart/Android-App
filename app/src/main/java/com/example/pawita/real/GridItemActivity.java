package com.example.pawita.real;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemActivity extends AppCompatActivity {

    TextView gridData;
    ImageView imageView;
    ColourCalculator colourCalculator = new ColourCalculator();
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Mike first commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);


        gridData = findViewById(R.id.griddata);
        imageView = findViewById(R.id.gridimage);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String receivedName =  extras.getString("fillText");
        int receivedColour = Integer.parseInt(receivedName);
        Uri receivedImage = Uri.parse(extras.getString("image"));


        gridData.setBackgroundColor(receivedColour);
        String dispColour = colourCalculator.hex2RgbString(receivedColour);

        gridData.setText(dispColour);
        imageView.setImageURI(receivedImage);

        //imageView.setImageURI(Uri.parse(receivedImage));
        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }

}