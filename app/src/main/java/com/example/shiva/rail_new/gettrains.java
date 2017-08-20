package com.example.shiva.rail_new;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiva on 30-07-2017.
 */

public class gettrains extends AsyncTask<String,String,String>{
      MainActivity.myadapter1 ma;
    String ne=null;
    Handler refresh = new Handler(Looper.getMainLooper());

    gettrains(MainActivity.myadapter1 ma){
            this.ma=ma;
        }
    @Override
    protected String doInBackground(String... params) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
                String name=response.toString();
                JSONObject json = new JSONObject(name);
                JSONArray ja = json.getJSONArray("trains");
                for (int i = 0; i < ja.length(); i++) {
                    ne="";
                    JSONObject jobj = (JSONObject) ja.get(i);
                     String temp_name=jobj.getString("name");
                    JSONObject jaa=jobj.getJSONObject("to_station");
                    JSONObject jaa1=jobj.getJSONObject("from_station");

                    String temp=jaa.getString("code");
                    String temp1=jaa1.getString("code");

                    String temp_no=jobj.get("number").toString();
                    String temp_tr_time=jobj.get("travel_time").toString();
                    JSONArray runsarr=jobj.getJSONArray("days");
                    JSONObject runsobj=runsarr.getJSONObject(MainActivity.date);
                         String runs=   runsobj.getString("runs");
                    if(runs.equals("Y"))
                    {

                        publishProgress(temp_name,temp_no,temp,temp1,temp_tr_time);
                    }

                }




            }


        } catch (MalformedURLException e) {
            refresh.post(new Runnable() {
                public void run()
                {
                    ma.notifyDataSetChanged();
                }
            });
            e.printStackTrace();
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
        return null;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

       //MainActivity.list_trains_names.add(values[0]);
        //MainActivity.list_trains_codes.add(values[1]);
        //MainActivity.list_st_codes.add(values[2]);
       //MainActivity.list_st_codess.add(values[3]) ;
       // ArrayList<String> myList = new ArrayList<String>(Arrays.asList(values[4].split(",")));

        //MainActivity.list_tr_classes.add(myList);
        HashMap hm = new HashMap();
        hm.put("no",values[1]);
        hm.put("name",values[0]);
        hm.put("src",values[3]);
        hm.put("dest",values[2]);
        hm.put("tr_time",values[4]);
        hm.put("val",new traindetail());
        MainActivity.detail1.add(hm);
        ma.notifyDataSetChanged();

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       // MainActivity.et_date.setText("exectude");


       getavailabilty obj[] = new getavailabilty[MainActivity.detail1.size()];
        for(int i=0;i<MainActivity.detail1.size();i++) {
            HashMap hm =MainActivity.detail1.get(i);
            String no=hm.get("no").toString();
            String src = hm .get("src").toString();
            String dest = hm .get("dest").toString();

                    String[] a = {"SL", "1A", "2A", "3A"};
                for (int j = 0; j < a.length; j++) {
                    //String cl = MainActivity.list_tr_classes.get(i).get(j).toString();

                    obj[i] = new getavailabilty(ma, no, src, dest, a[j], "GN");
                  obj[i].start();

                    obj[i] = new getavailabilty(ma, no, src, dest, a[j], "TQ");
                    obj[i].start();



            }

        }



    }


}


