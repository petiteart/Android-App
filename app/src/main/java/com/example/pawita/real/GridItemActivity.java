package com.example.pawita.real;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemActivity extends AppCompatActivity {

    TextView gridData;
    ImageView imageView;

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

        gridData.setText(receivedName);
        gridData.setBackgroundColor(receivedColour);
        imageView.setImageURI(receivedImage);

        //imageView.setImageURI(Uri.parse(receivedImage));
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
        //super.onBackPressed();
    }

}