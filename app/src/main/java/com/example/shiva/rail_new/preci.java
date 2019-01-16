package com.example.shiva.rail_new;

/**
 * Created by shiva on 22-08-2017.
 */

public class preci {
    String avail = "";
    String[] precidence={"",""};


    String[] getprecidence(String strr) {

        avail = strr;

        int len = avail.length();


        if (avail.contains("NOT")) {
            precidence[0]="3";
            precidence[1]="0";
        } else if (avail.contains("AVAILABLE")) {
            String ll = avail;
            avail = avail.substring(len - 4, len);
            String str = avail.replaceAll("\\D+", "");
            if (!str.isEmpty()) {
                precidence[0]="1";
                precidence[1]=str;

                // Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
            }
            avail = ll;
        } else if (avail.contains("WL")) {
            String ll = avail;
            avail = avail.substring(len - 4, len);
            String str = avail.replaceAll("\\D+", "");
            if (!str.isEmpty()) {
                precidence[0]="2";
                precidence[1]=str;
                // Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
            }
            avail = ll;
        } else if (avail.contains("TRAIN CANCELLED")) {
            precidence[0]="4";
            precidence[1]="0";
        } else {
            precidence[0]="5";
            precidence[1]="0";
        }
        return precidence;
    }
}
