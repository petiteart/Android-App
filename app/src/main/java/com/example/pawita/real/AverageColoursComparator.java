package com.example.pawita.real;

import java.util.Comparator;

public class AverageColoursComparator implements Comparator {
    @Override
    public int compare(Object c1, Object c2) {
        if (c1 instanceof Item && c2 instanceof Item) {
            Item i1 = (Item)c1;
            Item i2 = (Item)c2;
            if (i2.getItemColour() >= i1.getItemColour()) {
                return 1;
            } else {
                return -1;
            }
        }else{return -1;}
    }
}
