package com.example.pawita.real;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
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

import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class MainActivity extends AppCompatActivity {
    Button addClick = null;
    Bitmap currentBitmap = null;
    TextView gridText = null;
    GridView gridView = null;
    ColourCalculator colourCalculator = null;
    CustomAdapter customAdapter = null;

    public List<Uri> images = new ArrayList<>();
    public List<Bitmap> bitmapImages = new ArrayList<>();
    public List<Integer> averageColours = new ArrayList<>();

    private final int PICK_IMAGE = 1;
    private final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colourCalculator = new ColourCalculator();
        setContentView(R.layout.activity_main);
        // 1) find button by id in xml and set an onclicklistener
        addClick = findViewById(R.id.addButton);
        gridText = findViewById(R.id.averagecolour);
        gridView = findViewById(R.id.gridview);

        // 2) add a handler method for when the button is clicked
        addClick.setOnClickListener((View view) -> onClick(view));

        customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),GridItemActivity.class);
                //intent.putExtra("averagecolour",averageColours.get(i).toString());

                ImageView image = view.findViewById(R.id.image);
                TextView fillText = view.findViewById(R.id.averagecolour);

                intent.putExtra("image",images.get(i).toString());
                intent.putExtra("fillText", averageColours.get(i).toString());
                System.out.print(images.get(i).toString());
                System.out.print(averageColours.get(i).toString());
                startActivity(intent);
            }
        });
    }
    private void onClick(View v){

        choosePicture();
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE & resultCode == RESULT_OK){
            Uri pictureUri = data.getData();
            Log.i(LOG_TAG, "Uri: " + pictureUri );
            images.add(pictureUri);
            try {
                this.currentBitmap = getBitmap(getApplicationContext().getContentResolver() , pictureUri);
            }
            catch (Exception e) {
                Log.e(LOG_TAG,"error onActivityResult" + ExceptionUtils.getFullStackTrace(e));
            }

            Log.i(LOG_TAG, "calling get camera photo orientation");
            Bitmap rotatedBitmap = rotatePicture(pictureUri, this.getContentResolver());
            Log.i(LOG_TAG, "finish calling get camera photo orientation");
            //image.setImageBitmap(rotatedBitmap);
            bitmapImages.add(rotatedBitmap);


            int averageColour = colourCalculator.calculateAverageColour(currentBitmap);
            averageColours.add(calculateAverageColour(this.currentBitmap));
            customAdapter.notifyDataSetChanged();
        }
    }
    public static Bitmap rotatePicture(Uri pictureUri, ContentResolver contentResolver){
        Bitmap myBitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(pictureUri);
            ExifInterface exif = new ExifInterface(inputStream);
            myBitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUri);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
        }
        catch (Exception e) {
            //Log.e(LOG_TAG,"cannot rotate " + ExceptionUtils.getFullStackTrace(e));
        }
        return myBitmap;
    }


    public int calculateAverageColour(Bitmap currentBitmap){
        int countX = currentBitmap.getWidth();
        int countY = currentBitmap.getHeight();
        List<Integer> redValueArray = new ArrayList<>();
        List<Integer> greenValueArray = new ArrayList<>();
        List<Integer> blueValueArray = new ArrayList<>();
        for (int i = 0; i <  countX; i+=countX/5) {
            for (int j = 0; j <  countY; j+=countY/5) {
                int colour = currentBitmap.getPixel(i, j);
                int red = Color.red(colour);int blue = Color.blue(colour); int green = Color.green(colour); int alpha = Color.alpha(colour);
                redValueArray.add(red);greenValueArray.add(green); blueValueArray.add(blue);
            }
        }
        int sumRed = 0; int sumGreen = 0; int sumBlue = 0;
        int countRgb =0;
        for (int k=0; k< redValueArray.size();k++){
            sumRed += redValueArray.get(k); sumGreen += greenValueArray.get(k); sumBlue += blueValueArray.get(k);
            countRgb++;
        }
        double averageRed = sumRed/countRgb;double averageGreen = sumGreen/countRgb;double averageBlue = sumBlue/countRgb;
        double averageColour = Color.rgb((int) averageRed,(int) averageGreen,(int) averageBlue);
        return (int) averageColour;
    }


private class CustomAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return bitmapImages.size();
    }

    @Override
    public Object getItem(int i) {
        return bitmapImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = getLayoutInflater().inflate(R.layout.row_data, null);

        ImageView image = view1.findViewById(R.id.image);
        //image.setImageURI(images.get(i));
        image.setImageBitmap(bitmapImages.get(i));

        TextView fillText = view1.findViewById(R.id.averagecolour);
        fillText.setBackgroundColor(averageColours.get(i));
        String dispColour = colourCalculator.hex2RgbString(averageColours.get(i));
        //fillText.setText(averageColours.get(i).toString());

        fillText.setText(dispColour);
        //image.setImageURI(images.get(i));

        return view1;
    }
}
}