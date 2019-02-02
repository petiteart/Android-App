package com.example.pawita.real;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColourCalculatorTest {

    ColourCalculator colourCalculator;

    @Before
    public void setUp() {
        colourCalculator = new ColourCalculator();
    }

    @Test
    public void testSameColour() {
        Bitmap bitmap = mock(Bitmap.class);
        when(bitmap.getWidth()).thenReturn(100);
        when(bitmap.getHeight()).thenReturn(100);
        colourCalculator.calculateAverageColour(bitmap);

    }

}