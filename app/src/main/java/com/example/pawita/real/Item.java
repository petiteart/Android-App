package com.example.pawita.real;

import android.graphics.Bitmap;
import android.net.Uri;

public class Item implements Comparable {

        private Uri itemUri;
        private int itemColour;
        private Bitmap itemBitmap;

        public Item(Uri itemUri, Bitmap itemBitmap, int itemColour) {
            super();
            this.itemUri = itemUri;
            this.itemBitmap = itemBitmap;
            this.itemColour = itemColour;
        }

        public Uri getItemUri() {
            return itemUri;
        }

        public void setItemUri(Uri itemUri) {
            this.itemUri = itemUri;
        }

        public int getItemColour() {
            return itemColour;
        }

        public void setItemColour(Integer itemColour) {
            this.itemColour = itemColour;
        }

        public Bitmap getItemBitmap() {
        return itemBitmap;
        }

        public void setItemBitmap(Bitmap itemBitmap) {
        this.itemBitmap = itemBitmap;
        }


        @Override
        public String toString() {
            return itemUri + " " + itemColour;
        }

    @Override
    public int compareTo(Object o) {
            if(o instanceof Item){
                Item i = (Item)o;
                return this.itemUri.compareTo(i.itemUri);
            }else{
                return this.compareTo(o);
            }

    }


}
