package com.example.pawita.real;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@Ignore
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ColourCalculatorTest {

    ColourCalculator colourCalculator;
    @Before
    public void setUp() {
        colourCalculator = new ColourCalculator();
    }

    @Ignore
    @Test
    public void testSameColour() {
        int COLOUR = -16777216;
        Bitmap bitmap = mock(Bitmap.class);
        when(bitmap.getWidth()).thenReturn(10);
        when(bitmap.getHeight()).thenReturn(10);
        when(bitmap.getPixel(anyInt(),anyInt())).thenReturn(COLOUR);
        int result = colourCalculator.calculateAverageColour(bitmap);
        //assertEquals(colourCalculator.calculateRGB(0, 0, COLOUR), result);
        assertEquals(COLOUR, result);
    }

    @Ignore
    @Test
    public void testSameColourRed() {
        int RED = 16711680;
        Bitmap bitmap = mock(Bitmap.class);
        when(bitmap.getWidth()).thenReturn(5);
        when(bitmap.getHeight()).thenReturn(5);
        when(bitmap.getPixel(anyInt(),anyInt())).thenReturn(RED);
        int result = colourCalculator.calculateAverageColour(bitmap);
        //assertEquals(colourCalculator.calculateRGB(0, 0, COLOUR), result);
        assertEquals(RED, result);
    }

}