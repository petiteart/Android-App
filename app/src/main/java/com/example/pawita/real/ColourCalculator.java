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
        for (int i = 0; i <  countX; i+=countX/5) {
            for (int j = 0; j <  countY; j+=countY/5) {
                int colour = currentBitmap.getPixel(i, j);
                int red = calculateRed(colour);
                int blue = calculateBlue(colour);
                int green = Color.green(colour);
                int alpha = Color.alpha(colour);
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

    private int calculateRed(int colour) {
        if (isValidRgbValue(colour)) {
            return (colour >> 16) & 0xFF;
        }
        return -1;
    }

    private int calculateBlue(int colour) {
        if (isValidRgbValue(colour)) {
            return colour & 0xFF;
        }
        return -1;
    }

    private boolean isValidRgbValue(int color) {
        return color >= 0 && color <= 255;
    }
}
