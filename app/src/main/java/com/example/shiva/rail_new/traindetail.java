package com.example.shiva.rail_new;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shiva on 05-08-2017.
 */

public class traindetail {
    String duration="-";
    String dest_arrival_time="-";
    String source_departure_time="-";
   ArrayList<HashMap> arrsl = new ArrayList<>();
    ArrayList<HashMap> arr1a = new ArrayList<>();
    ArrayList<HashMap> arr2a = new ArrayList<>();
    ArrayList<HashMap> arr3a = new ArrayList<>();
    ArrayList[] aa= {arrsl,arr1a,arr2a,arr3a};
    String[] quo={"GN","TQ","PT","LD"};
    traindetail(){
        for (int j =0;j<aa.length;j++) {
            for (int i = 0; i < quo.length; i++) {
                HashMap hm = new HashMap();
                hm.put("name", quo[i]);
                hm.put("value", "-");
                aa[j].add(hm);
            }

        }

    }
    public void setavail(String cl,String qu,String ava){
        int temp;
        if(cl.equals("SL")){
            temp=0;
        }
        else if(cl.equals("1A")){
            temp=1;

        }
        else if(cl.equals("2A")){
            temp=2;
        }
        else {
            temp=3;
        }
        ArrayList<HashMap> t=aa[temp];
        for (int i=0;i<t.size();i++){
            HashMap h=t.get(i);
            if(h.get("name").equals(qu)){
                h.put("value",ava);
            }

        }

    }
    public String getavail(String cl,String qu){
        String returnval=null;
        int temp;
        if(cl.equals("SL")){
            temp=0;
        }
        else if(cl.equals("1A")){
            temp=1;

        }
        else if(cl.equals("2A")){
            temp=2;
        }
        else {
            temp=3;
        }
        ArrayList<HashMap> t=aa[temp];
        for (int i=0;i<t.size();i++){
            HashMap h=t.get(i);
            if(h.get("name").equals(qu)){
               returnval =h.get("value").toString();
                break;
            }

        }
       return  returnval;
    }

}
