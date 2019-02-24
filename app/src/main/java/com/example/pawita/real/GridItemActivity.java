package com.example.pawita.real;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.pawita.real.MainActivity.rotatePicture;

public class GridItemActivity extends AppCompatActivity {

    TextView gridData;
    ImageView imageView;
    ColourCalculator colourCalculator = new ColourCalculator();

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
        Uri receivedUri = Uri.parse(extras.getString("image"));
        //Bitmap bitmap = intent.getParcelableExtra("bitmapImage");
        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmapImage");


        gridData.setBackgroundColor(receivedColour);
        String dispColour = colourCalculator.hex2RgbString(receivedColour);

        gridData.setText(dispColour);
        //imageView.setImageURI(receivedUri);
        Bitmap bitmap = rotatePicture(receivedUri, this.getContentResolver());

        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        ///TO DO-----> Correct Image view orientation



        imageView.setOnTouchListener(new Touch());

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        float imageWidth = imageView.getDrawable().getIntrinsicWidth();
        float imageHeight = imageView.getDrawable().getIntrinsicHeight();
        RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
        RectF viewRect = new RectF(0, 0, imageView.getWidth(),
                imageView.getHeight());
        Matrix matrix = imageView.getMatrix();
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
        imageView.setImageMatrix(matrix);
        imageView.invalidate();

    }

}