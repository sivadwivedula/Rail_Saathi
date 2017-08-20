package com.example.shiva.rail_new;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * Created by shiva on 30-07-2017.
 */

public class getsoucecode extends AsyncTask<String,Iterator,String>{
    Handler re= new Handler();

    @Override
    protected String doInBackground(final String... params) {
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

                JSONObject jsonObject = new JSONObject(name);
                JSONArray jsonArray =(JSONArray)jsonObject.get("station");
                for(int i=0;i<jsonArray.length();i++){
                   JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                    String temp1=jsonObject1.get("name").toString();
                    String temp2= jsonObject1.get("code").toString();
                    MainActivity.source_staion_names.add(temp1);
                    MainActivity.source_staion_codes.add(temp2);
                    publishProgress();
                }


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Iterator... values) {
        super.onProgressUpdate(values);
        MainActivity.ad.notifyDataSetChanged();

    }
}


