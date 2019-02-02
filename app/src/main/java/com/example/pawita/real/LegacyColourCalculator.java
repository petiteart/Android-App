package com.example.pawita.real;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public class LegacyColourCalculator {

    public int calculateAverageColour(Bitmap currentBitmap){
        int countX = currentBitmap.getWidth();
        int countY = currentBitmap.getHeight();
        List<Integer> redValueArray = new ArrayList<>();
        List<Integer> greenValueArray = new ArrayList<>();
        List<Integer> blueValueArray = new ArrayList<>();
        for (int i = 0; i <  countX; i+=countX/5) {
            for (int j = 0; j <  countY; j+=countY/5) {
                int colour = currentBitmap.getPixel(i, j);
                int red    = calculateRed(colour);
                int blue   = calculateBlue(colour);
                int green  = calculateGreen(colour);
                int alpha  = calculateAlpha(colour);
                System.out.println("red" + red);
                System.out.println("red" + red);
                System.out.println("green" + green);
                System.out.println("blue" + blue);

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
        System.out.println("sum red" + sumRed);
        System.out.println("sum green" + sumGreen);
        System.out.println("sum blue" + sumBlue);
        double averageRed = sumRed/countRgb;double averageGreen = sumGreen/countRgb;double averageBlue = sumBlue/countRgb;
        System.out.println("average red" + averageRed);
        System.out.println("average green" + averageGreen);
        System.out.println("average blue" + averageBlue);
        int averageColourHex = calculateRGB((int) averageRed,(int) averageGreen,(int) averageBlue);
        return (int) averageColourHex;
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


    private int calculateGreen(int colour) {
        if (isValidRgbValue(colour)) {
            return (colour >> 8) & 0xFF;
        }
        return -1;
    }

    private int calculateAlpha(int colour) {
        if (isValidRgbValue(colour)) {
            return colour >>> 24;
        }
        return -1;
    }

    public int calculateRGB(int red, int green, int blue){
        if(areValidRgbValues(red, green, blue)) {
            return 0xff000000 | (red << 16) | (green << 8) | blue;
        }
        return -1;
    }

    private boolean areValidRgbValues(int... colors)
    {
        for (int i : colors) {
            if (!isValidRgbValue(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidRgbValue(int color) {
        return color >= 0 && color <= 255;
    }

//    public Integer hex2RgbString(int colourHexDouble){
//        String colourHex  = colourHexDouble + "";
//        Integer red = Integer.valueOf(colourHex.substring( 1, 3 ), 16);
//        Integer green = Integer.valueOf(colourHex.substring( 3, 5 ), 16);
//        Integer blue = Integer.valueOf(colourHex.substring( 5, 7 ), 16);
//        String RGBString = "#" + red.toString() + green.toString() + blue.toString();
//        Integer RGBInteger = Integer.parseInt(RGBString);
//        //Integer[] RGBArray = {red, green, blue};
//        return RGBInteger;
//    }

}
