package com.example.shiva.rail_new;

/**
 * Created by shiva on 26-08-2017.
 */

import android.os.AsyncTask;
import android.os.Handler;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by shiva on 10-08-2017.
 */

public class inter extends AsyncTask<String,String,String> {
    Handler h = new Handler();
    activity2.myadapter1 ma;
    static  Elements newsHeadlines;
    static String nn="NGP";
    inter(activity2.myadapter1 ma){
        this.ma=ma;
    }
    @Override
    protected String doInBackground(String[] params) {
        Document doc = null;
        try {
            // doc = Jsoup.connect("https://www.trainman.in/trains/Hyderabad-Deccan-HYB/Raipur-Jn-R?date=12-08-2017&class=ALL&quota=GN").get();
            doc=Jsoup.connect("https://indiarailinfo.com/search/185/0/844?date=15039648&dd=0&ad=0&co=0&tt=0&ed=0&dp=&ea=0&ap=&loco=&drev=0&arev=0&trev=0&rake=&rsa=0&idf=0&idt=0&dhf=0&dmf=0&dht=0&dmt=0&ahf=0&amf=0&aht=0&amt=0&nhf=-1&nht=-1&ttf=0&ttt=0&dstf=0&dstt=0&spdf=0&spdt=0&zone=0&pantry=0&stptype=undefined&raketype=0&cu=undefined&trn=0&q=").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newsHeadlines = doc.select("a[href^=/search/r]");
        h.post(new Runnable() {
            @Override
            public void run() {
            //    MainActivity.tv.setText(sh.newsHeadlines.get(0).text());
            }
        });
        newsHeadlines.toString();

            return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.detail1.clear();


        String temp1= MainActivity.et_source.getText().toString();
        String temp2=nn;
        String temp3=MainActivity.et_dest.getText().toString();
        String ur="https://api.railwayapi.com/v2/between/source/"+temp1+"/dest/"+temp2+"/date/"+MainActivity.et_date.getText().toString()+"/apikey/"+MainActivity.apikey+"/";



        gettrains gt = new gettrains(ma);
        gt.execute(ur);
        ur="https://api.railwayapi.com/v2/between/source/"+temp2+"/dest/"+temp3+"/date/"+MainActivity.et_date.getText().toString()+"/apikey/"+MainActivity.apikey+"/";

        gt = new gettrains(ma);
        gt.execute(ur);

       // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

    }
}

