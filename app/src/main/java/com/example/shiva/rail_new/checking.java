package com.example.shiva.rail_new;

import android.widget.Toast;

import java.util.Collections;

/**
 * Created by shiva on 23-08-2017.
 */

public class checking {

    String[] getlow(traindetail obj, String cl) {
        String[] arr = {"", "", ""};
        arr[0] = obj.getavail(cl, "GN");
        arr[1] = obj.getavail(cl, "TQ");
        arr[2] = obj.getavail(cl, "PT");
      preci pobj = new preci();
        String[] a1= pobj.getprecidence(arr[0]);
        String[] a2 = pobj.getprecidence(arr[1]);
        String[] a3 = pobj.getprecidence(arr[2]);
        int p11=Integer.parseInt(a1[0]);
        int p21=Integer.parseInt(a2[0]);
        int p31=Integer.parseInt(a3[0]);
        if(p11<p21){

            if(p21<p31){
             return a3;

            }
            else {
               return a2;
            }
        }
        else {
         return a1;
        }


    }
}
