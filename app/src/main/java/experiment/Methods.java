package experiment;

import java.util.ArrayList;

public class Methods {

    public int Multiple3n5(int maxRange){
        //for 0 < i < 1000
        ArrayList<Integer> numbersDividedBy3 = new ArrayList<Integer>();
        ArrayList<Integer> numbersDividedBy5 = new ArrayList<Integer>();
        int sumValue = 0;
        //int[] numbersDividedBy3 = null;
        //int[] numbersDividedBy5 = null;
        if(maxRange<0 || maxRange>10){
            return -1;
        }else{
            for(int i=0; i<maxRange; i++){
                if(i %3 == 0){
                    numbersDividedBy3.add(i);
                    sumValue += i;

                }if(i %5 == 0){
                    numbersDividedBy5.add(i);
                    sumValue += i;
                }

            }
        }


        return sumValue;
    }
}