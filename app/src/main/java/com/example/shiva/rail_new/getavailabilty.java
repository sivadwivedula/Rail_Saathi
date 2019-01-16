package com.example.shiva.rail_new;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shiva on 30-07-2017.
 */

public class getavailabilty extends Thread {
    activity2.myadapter1 ma;
    String url = null;
    Handler refresh = new Handler(Looper.getMainLooper());
    String temp_avail=null;
    String source=null;
    String dest=null;
    String clas=null;
    String qu=null;
    int pos;
    String train_no,train_name;
    getavailabilty(activity2.myadapter1 ma,String train_no,String source,String dest,String clas,String qu) {
        this.ma = ma;
        this.train_no= train_no;
        this.source=source;
        this.dest=dest;
        this.clas=clas;
        this.qu=qu;

    }
    public void run() {
        url="https://api.railwayapi.com/v2/check-seat/train/" + train_no + "/source/" + source + "/dest/" + dest+ "/date/" + MainActivity.et_date.getText().toString() + "/class/"+clas+"/quota/"+qu+"/apikey/" + MainActivity.apikey + "/";



        StringBuilder response = new StringBuilder();
        try {
            final URL url = new URL(this.url);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();

                String name = response.toString();

                JSONObject json1 = new JSONObject(name);
                if (json1.isNull("availability")) {


                } else {
                    JSONArray ja1 = json1.getJSONArray("availability");
                    if (ja1.isNull(0)) {
                       getavailabilty obj = new getavailabilty(ma, train_no, source, MainActivity.et_dest.toString(), clas, "GN");
                        obj.start();
                        obj = new getavailabilty(ma, train_no, source, MainActivity.et_dest.toString(), clas, "TQ");
                        obj.start();

                    } else {
                        temp_avail = ((JSONObject) ja1.get(0)).get("status").toString();
                        refresh.post(new Runnable() {
                            public void run()
                            {

                                for(int i =0;i<MainActivity.detail1.size();i++){
                                    HashMap h =MainActivity.detail1.get(i);
                                    if(h.get("no").toString().equals(train_no)){

                                        traindetail a=(traindetail)MainActivity.detail1.get(i).get("val");
                                        a.setavail(clas,qu,temp_avail);

                                    }
                                }
                              //  hm.get(train_no).put(url.toString().substring(20),temp_avail);
                              //  MainActivity.avail.add(temp_avail);

                                ma.notifyDataSetChanged();
                            }
                        });

                    }
                }
            }

            test obj = new test();

        } catch (IOException e) {

            refresh.post(new Runnable() {
                public void run()
                {
                    ma.notifyDataSetChanged();
                }
            });
            e.printStackTrace();
        } catch (JSONException e) {


            refresh.post(new Runnable() {
                public void run()
                {
                    ma.notifyDataSetChanged();
                }
            });
            e.printStackTrace();
        }


    }


}


