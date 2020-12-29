///////////////////////////////
//
//  Bryan Hatami: bhatami
//
//  C343
//  10/23/2020
//  Lab 9
///////////////////////////////

import java.util.Arrays;
import java.util.Random;

public class HW05CountingSort {
    //set random to var rd
    Random rd = new Random();
    public int[] counting(int[] a){
        ///////////////
        //set the var for the max and len of the table
        int openValueMaxKey = 0;
        int arrTable = a.length;
        System.out.println(arrTable);
        System.out.println(openValueMaxKey);
        ///////////////
        //for loop to find the max value
        for(int j = 0; j<arrTable; j++){
            //check if it is the greatest value
            if(openValueMaxKey<a[j]){ openValueMaxKey=a[j]; } }
        System.out.println(openValueMaxKey);
        ///////////////
        //introduce a look up table
        //find the occurrence of all the elements in table
        int[] lookUpTab = new int[openValueMaxKey+1];
        System.out.println(lookUpTab.length);
        System.out.println(openValueMaxKey);
        for(int j = 0; j<arrTable; j++){ lookUpTab[a[j]]+=1; }
        System.out.println(Arrays.toString(lookUpTab));
        ///////////////
        //loop through and add all occurrence values to the previous one
        for(int j = 1; j<lookUpTab.length;j++){ lookUpTab[j]=lookUpTab[j]+lookUpTab[j-1]; }
        System.out.println(Arrays.toString(lookUpTab));
        ///////////////
        //new array to hold output of final table
        int[] outTable = new int[arrTable];
        ///////////////
        //loop through the back of the table
        for(int j = arrTable-1; j>= 0; j--){
            //after the value of a is found it will be set as an index to find value lookup table
            outTable[lookUpTab[a[j]]-1] = a[j];
            //must -- so that value does not repeat
            lookUpTab[a[j]]--;
        }
        ///////////////
        return outTable;
    }

    public static void main(String args[]){
        HW05CountingSort sortV = new HW05CountingSort();
        //set random to var rd
        Random rd = new Random();
        //set table
        int[] a = new int[11];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int)(Math.random()*10); // storing random integers for 0-10 in an array
        }
        //test and print out new table
        int[] x = sortV.counting(a);
        System.out.println(Arrays.toString(x));
    }
}
