package com.example.pawita.real;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class ColourCalculator {

    public int calculateAverageColour(Bitmap currentBitmap){
        int countX = currentBitmap.getWidth();
        int countY = currentBitmap.getHeight();
        List<Integer> redValueArray = new ArrayList<>();
        List<Integer> greenValueArray = new ArrayList<>();
        List<Integer> blueValueArray = new ArrayList<>();
        for (int i = 0; i <  countX; i+=5) {
            for (int j = 0; j <  countY; j+=5) {
                int colour = currentBitmap.getPixel(i, j);
                int red    = Color.red(colour);
                int blue   = Color.blue(colour);
                int green = Color.green(colour);
                int alpha = Color.alpha(colour);
                redValueArray.add(red);
                greenValueArray.add(green);
                blueValueArray.add(blue);
            }
        }
        int sumRed = 0; int sumGreen = 0; int sumBlue = 0;
        int countRgb =0;
        for (int k=0; k< redValueArray.size();k++){
            sumRed += redValueArray.get(k); sumGreen += greenValueArray.get(k); sumBlue += blueValueArray.get(k);
            countRgb++;
        }

        if(countRgb == 0){return -1;}
        double averageRed = sumRed/countRgb;double averageGreen = sumGreen/countRgb;double averageBlue = sumBlue/countRgb;
        return Color.rgb((int)averageRed, (int)averageGreen, (int)averageBlue);
    }

    public String hex2RgbString(int colourHexDouble){
        String colourHex  = colourHexDouble + "";
        Integer red = Integer.valueOf(colourHex.substring( 1, 3 ), 16);
        Integer green = Integer.valueOf(colourHex.substring( 3, 5 ), 16);
        Integer blue = Integer.valueOf(colourHex.substring( 5, 7 ), 16);
        String RGBString = "RGB " + "(" + red.toString() + ", " + green.toString() + ", " + blue.toString() + ")";
        return RGBString;
    }
}
