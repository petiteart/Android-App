package com.example.pawita.real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button initialClick = null;
    TextView fillText = null;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1) find button by id in xml and set an onclicklistener
        initialClick = findViewById(R.id.button);
        // 2) add a handler method for when the button is clicked
        initialClick.setOnClickListener((View view) -> onClick(view));
        // 3) Look up how you can select an image file to open on screen.
    }
    private void onClick(View v){
        fillText = findViewById(R.id.textview);
        String countText = "Hello Guapa, you have "+count;
        if(count == 1){
            fillText.setText(countText + " time.");
        }
        else {
            fillText.setText(countText + " times.");
        }
        count = count+1;

    }
}
