package com.example.pawita.real;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LegacyColourCalculatorTest {

    LegacyColourCalculator colourCalculator;

    @Before
    public void setUp() {
        colourCalculator = new LegacyColourCalculator();
    }

    @Test
    @Ignore
    public void testSameColour() {
        int COLOUR = 0;
        Bitmap bitmap = mock(Bitmap.class);
        when(bitmap.getWidth()).thenReturn(5);
        when(bitmap.getHeight()).thenReturn(5);
        when(bitmap.getPixel(anyInt(),anyInt())).thenReturn(COLOUR);
        int result = colourCalculator.calculateAverageColour(bitmap);
        //assertEquals(colourCalculator.calculateRGB(0, 0, COLOUR), result);
        assertEquals(colourCalculator.calculateRGB(0, 0, COLOUR), result);
    }

    @Test
    @Ignore
    public void testSameColourRed() {
        int RED = 16711680;
        Bitmap bitmap = mock(Bitmap.class);
        when(bitmap.getWidth()).thenReturn(5);
        when(bitmap.getHeight()).thenReturn(5);
        when(bitmap.getPixel(anyInt(),anyInt())).thenReturn(RED);
        int result = colourCalculator.calculateAverageColour(bitmap);
        //assertEquals(colourCalculator.calculateRGB(0, 0, COLOUR), result);
        assertEquals(colourCalculator.calculateRGB(255, 0, RED), result);
    }


}