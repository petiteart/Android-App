package com.example.pawita.real;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBitmap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23, shadows = {ShadowBitmap.class})
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

        ArgumentMatcher lessThanFifty = new ArgumentMatcher<Integer>(){
            @Override
            public boolean matches(Integer argument) {
                return argument.intValue() < 50;
            }
        };
        when(bitmap.getPixel(intThat(lessThanFifty), anyInt())).thenReturn(0);

        ArgumentMatcher gtEqFifty = new ArgumentMatcher<Integer>(){
            @Override
            public boolean matches(Integer argument) {
                return argument.intValue() >= 50;
            }
        };
        when(bitmap.getPixel(intThat(gtEqFifty), anyInt())).thenReturn(255);
        when(bitmap.getHeight()).thenReturn(100);
        int result = colourCalculator.calculateAverageColour(bitmap);
        assertEquals(128, result);
    }

}