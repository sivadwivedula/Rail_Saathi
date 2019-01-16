package com.example.shiva.rail_new;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
   //xml elements

    static EditText et_source,et_dest,et_date,nee1,nee2;
    static final String apikey="10upjz972m";

    //lists
    static Vector<String> source_staion_names= new Vector<>();
    static Vector<String> source_staion_codes= new Vector<>();

    static Vector<String> dest_staion_names= new Vector<>();
    static Vector<String> dest_staion_codes= new Vector<>();
    static ArrayList<HashMap> detail1= new ArrayList<>();
    static Vector<HashMap> sorted_detail1= new Vector<>();

    //adapters
    static  ArrayAdapter<String> ad;
    static  ArrayAdapter<String> ad1;

    //listviews
    ListView lv;
    ListView lv1,lv2;

    //layouts
    LinearLayout ll;
    LayoutInflater l;
    static int date=6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_source=(EditText)findViewById(R.id.et_source);
        et_dest=(EditText)findViewById(R.id.et_dest);

        ll=(LinearLayout)findViewById(R.id.ll);
        lv=(ListView )findViewById(R.id.lv);
        lv1=(ListView )findViewById(R.id.lv1);
        lv1=(ListView )findViewById(R.id.lv1);
        lv2=(ListView )findViewById(R.id.lv2);


        et_date=(EditText)findViewById(R.id.et_date);

         l=getLayoutInflater();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_source.setText(source_staion_codes.get(position));
                source_staion_names.clear();
                source_staion_codes.clear();
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_dest.setText(dest_staion_codes.get(position));
                dest_staion_codes.clear();
                dest_staion_names.clear();
            }
        });
    }
    public void show_source(View v){
        source_staion_codes.clear();
        source_staion_codes.clear();
        getsoucecode gsc = new getsoucecode();
        String temp="https://api.railwayapi.com/v2/suggest-station/name/"+et_source.getText().toString()+"/apikey/"+apikey+"/";

        gsc.execute(temp);
        ad= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,source_staion_names);
        lv.setAdapter(ad);

    }
    public void show_dest(View v){
        dest_staion_names.clear();
        dest_staion_codes.clear();
        getsdestode gsc = new getsdestode();
        String temp="https://api.railwayapi.com/v2/suggest-station/name/"+et_dest.getText().toString()+"/apikey/"+apikey+"/";
        gsc.execute(temp);
        ad1= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,dest_staion_names);
        lv1.setAdapter(ad1);

    }
    public  void method1(){
        Date dt=null;
        String nm=et_date.getText().toString();
        DateFormat df=new SimpleDateFormat("dd-mm-yyyy");
        try {
           dt=df.parse(nm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s1=df.format(dt);
        String a1[]=s1.split("-");
        int year = Integer.parseInt(a1[2].toString());
        int month = Integer.parseInt(a1[1])-1;
        int day = Integer.parseInt((a1[0]));

        Calendar c1 = Calendar.getInstance();
        c1.set(year,month,day);

        //long days = c1.getTime().getTime()/(24*60*60*1000);
        int days=c1.get(Calendar.DAY_OF_WEEK);
        if(days==1){
            date=6;
        }
        else{
            date=days-2;
        }

    }
    public void check(View v){

            method1();
        Intent i = new Intent(MainActivity.this,activity2.class);
        startActivity(i);

    }
}
