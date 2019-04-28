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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class MainActivity extends AppCompatActivity {

    public static final String URI = "Uri";
    public static final String COLOUR = "averageColour";
    Button addClick = null;
    Button refresh = null;
    Bitmap currentBitmap = null;
    TextView gridText = null;
    GridView gridView = null;
    ColourCalculator colourCalculator = null;
    CustomAdapter customAdapter = null;

    /*
     * Have one dataset of type TreeSet https://www.geeksforgeeks.org/treeset-in-java-with-examples/
     * Sorts images when added.
     *
     * NOTE: A set can't have duplicates
     * https://www.geeksforgeeks.org/java-equals-compareto-equalsignorecase-and-compare/
     */
    private Toolbar mTopToolbar;
    public TreeSet<Item> items = new TreeSet<>(new AverageColoursComparator());
    public List<Uri> images = new ArrayList<>();
    public List<Integer> averageColours = new ArrayList<>();

    //public HashMap<Bitmap,Integer> imageMap = new HashMap<>();
    public HashMap<Uri,Integer> imageMap = new HashMap<>();
    public HashMap<Uri,Integer> sortedImageMap = new HashMap<>();

    private final int PICK_IMAGE = 1;
    private final String LOG_TAG = "MainActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu){// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            //Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(mTopToolbar);

        colourCalculator = new ColourCalculator();

        // 1) find button by id in xml and set an onclicklistener
        addClick = findViewById(R.id.addButton);
        gridText = findViewById(R.id.averagecolour);
        gridView = findViewById(R.id.gridview);
        refresh = findViewById(R.id.refreshButton);

        // 2) add a handler method for when the button is clicked
        addClick.setOnClickListener((View view) -> onClick(view));
        refresh.setOnClickListener((View view) -> onClickRefresh(view));

        customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(customAdapter);


    }

    protected void onClickRefresh(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void onClick(View v){ choosePicture(); }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("activity","first");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG,"hit onActivityResult");
        if(requestCode == PICK_IMAGE & resultCode == RESULT_OK){
            Uri pictureUri = data.getData();
            Log.i(LOG_TAG, "Uri: " + pictureUri );

            try {
                currentBitmap = getBitmap(getApplicationContext().getContentResolver(), pictureUri);
            }
            catch (Exception e) {
                Log.e(LOG_TAG,"error onActivityResult" + ExceptionUtils.getFullStackTrace(e));
            }
            Log.i(LOG_TAG, "calling get camera photo orientation");
            Bitmap rotatedBitmap = rotatePicture(pictureUri, getContentResolver());
            Log.i(LOG_TAG, "finish calling get camera photo orientation");
            //bitmapImages.add(rotatedBitmap);

            Integer averageColour = calculateAverageColour(currentBitmap);

            items.add( new Item(pictureUri,rotatedBitmap, averageColour));
            //data.putExtra("imageAndFilltext", items.toString());
            //data.putExtra("image", pictureUri.toString());
            //data.putExtra("fillText", averageColour.toString());
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


private class CustomAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {

        return getItemFromIndex(items,i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = getLayoutInflater().inflate(R.layout.row_data, null);
        ImageView imageView = view1.findViewById(R.id.image);
        TextView fillText = view1.findViewById(R.id.averagecolour);

        Item dispItem = getItemFromIndex(items,i);
        fillText.setBackgroundColor(dispItem.getItemColour());
        fillText.setText(colourCalculator.hex2RgbString(dispItem.getItemColour()));
        imageView.setImageBitmap(dispItem.getItemBitmap());

       // averageColours.add(dispItem.getItemColour());
       // displayedImages.add(dispItem.getItemUri());

//        // Split key and value
//        displayedImages.addAll(itemMap.keySet());
//        averageColours.addAll(itemMap.values());
//
//        //display
//        fillText.setBackgroundColor(averageColours.get(i));
//        String dispColour = colourCalculator.hex2RgbString(averageColours.get(i));
//        fillText.setText(dispColour);
//
//        imageView.setImageURI(displayedImages.get(i));
        return view1;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

            Intent intent = new Intent(getApplicationContext(),GridItemActivity.class);
            Item item = getItemFromIndex(items,i);

            intent.putExtra(COLOUR,item.getItemColour());
            intent.putExtra(URI, item.getItemUri().toString());

            startActivity(intent);

    }
}

    private Item getItemFromIndex(Set <Item>set, int index ){
        int counter = 0;
        for(Item i : set){
            if (index == counter){
                return i;
            }else{
                counter++;
            }
        }
        return null;
    }

    private Integer getIndex(Set <Item>set, Item item){
        Integer counter = 0;
        for(Item i : set){
            if (i == item){
                return counter;
            }else{
                counter++;
            }
        }
        return -1;
    }
}