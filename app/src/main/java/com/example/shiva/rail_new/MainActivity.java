package com.example.shiva.rail_new;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
   //xml elements
    static EditText et_source,et_dest,et_date;
    static final String apikey="h1914l8ccg";

    //lists
    static Vector<String> source_staion_names= new Vector<>();
    static Vector<String> source_staion_codes= new Vector<>();

    static Vector<String> dest_staion_names= new Vector<>();
    static Vector<String> dest_staion_codes= new Vector<>();
    static Vector<HashMap> detail1= new Vector<>();
    static Vector<HashMap> sorted_detail1= new Vector<>();

    //adapters
    static  ArrayAdapter<String> ad;
    static  ArrayAdapter<String> ad1;
    myadapter1 ma;
    //listviews
    ListView lv;
    ListView lv1,lv2;

    TextView tv,tv1,tv2,tv3,tv4,tv5;
    //layouts
    LinearLayout ll;
    LayoutInflater l;
    static int date=6;
    static ArrayList indd= new ArrayList<>();

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
    public void temp(View v){
        BinarySearchTree bst_available= new BinarySearchTree();
        BinarySearchTree bst_waiting= new BinarySearchTree();
        BinarySearchTree bst_notavailable= new BinarySearchTree();
        for(int i=0;i<detail1.size();i++){
           HashMap hm = detail1.get(i);
           traindetail obj=(traindetail)hm.get("val");
           String av=obj.getavail("SL","TQ");
           if(av.contains("AVAILABLE")) {
               int len=av.length()-1;
               av=av.substring(len-4,len);
               String str = av.replaceAll("\\D+", "");
               Toast.makeText(this, str+" available", Toast.LENGTH_SHORT).show();
               if (!str.isEmpty()) {
                   bst_available.insert(Integer.parseInt(str),i);

                  // Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
               }
           }

       }
//            bst_available.printInOrderRec(bst_available.root);
//        for(int i=0;i<indd.size();i++){
//            String spos=indd.get(i).toString();
//            int ipos=Integer.parseInt(spos);
//            HashMap temp=detail1.get(ipos);
//            HashMap temp1=detail1.get(i);
//            detail1.set(ipos,temp1);
//            ma.notifyDataSetChanged();
//            detail1.set(i,temp);
//            ma.notifyDataSetChanged();
//
//        }
        //Toast.makeText(this, indd.toString()+"", Toast.LENGTH_SHORT).show();
    }
    public void check(View v){
        detail1.clear();
            method1();
        String temp1= et_source.getText().toString();
        String temp2=et_dest.getText().toString();
        String ur="https://api.railwayapi.com/v2/between/source/"+temp1+"/dest/"+temp2+"/date/"+et_date.getText().toString()+"/apikey/"+apikey+"/";

        ma = new myadapter1();

        gettrains gt = new gettrains(ma);
        gt.execute(ur);






   lv=(ListView)findViewById(R.id.lv);
      lv.setAdapter(ma);
    }

    class myadapter1 extends BaseAdapter{
        @Override
        public int getCount() {
            return detail1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=l.inflate(R.layout.sdf,null);
          //  tv=(TextView)v.findViewById(R.id.tv_trainname);
            TextView tv_no=(TextView)v.findViewById(R.id.vv8);
            TextView tv_1a_gn=(TextView)v.findViewById(R.id.tv_1ac_gn);
            TextView tv_2a_gn=(TextView)v.findViewById(R.id.tv_2ac_gn);
            TextView tv_3a_gn=(TextView)v.findViewById(R.id.tv_3ac_gn);
            TextView tv_sl_gn=(TextView)v.findViewById(R.id.tv_sl_gn);

            TextView tv_1a_tq=(TextView)v.findViewById(R.id.tv_1ac_tk);
            TextView tv_2a_tq=(TextView)v.findViewById(R.id.tv_2ac_tk);
            TextView tv_3a_tq=(TextView)v.findViewById(R.id.tv_3ac_tk);
            TextView tv_sl_tq=(TextView)v.findViewById(R.id.tv_sl_tk);

            TextView tv_1a_ld=(TextView)v.findViewById(R.id.tv_1ac_ld);
            TextView tv_2a_ld=(TextView)v.findViewById(R.id.tv_2ac_ld);
            TextView tv_3a_ld=(TextView)v.findViewById(R.id.tv_3ac_ld);
            TextView tv_sl_ld=(TextView)v.findViewById(R.id.tv_sl_ld);

            TextView tv_1a_pt=(TextView)v.findViewById(R.id.tv_1ac_ptk);
            TextView tv_2a_pt=(TextView)v.findViewById(R.id.tv_2ac_ptk);
            TextView tv_3a_pt=(TextView)v.findViewById(R.id.tv_3ac_ptk);
            TextView tv_sl_pt=(TextView)v.findViewById(R.id.tv_sl_ptk);


                TextView aa = (TextView)v.findViewById(R.id.vv9);
                     TextView aa1 = (TextView)v.findViewById(R.id.vv10);

            HashMap hm =detail1.get(position);
            String no=hm.get("no").toString();
            String name = hm.get("name").toString();
            String tr_time=hm.get("tr_time").toString();
            aa.setText(name);
            aa1.setText(tr_time);
            traindetail obj=(traindetail) hm.get("val");
            String gn_sl=obj.getavail("SL","GN");
            String gn_1a=obj.getavail("1A","GN");
            String gn_2a=obj.getavail("2A","GN");
            String gn_3a=obj.getavail("3A","GN");

            String tq_sl=obj.getavail("SL","TQ");
            String tq_1a=obj.getavail("1A","TQ");
            String tq_2a=obj.getavail("2A","TQ");
            String tq_3a=obj.getavail("3A","TQ");

            String ld_sl=obj.getavail("SL","LD");
            String ld_1a=obj.getavail("1A","LD");
            String ld_2a=obj.getavail("2A","LD");
            String ld_3a=obj.getavail("3A","LD");

            String pt_sl=obj.getavail("SL","PT");
            String pt_1a=obj.getavail("1A","PT");
            String pt_2a=obj.getavail("2A","PT");
            String pt_3a=obj.getavail("3A","PT");

            tv_no.setText(no);
            tv_1a_gn.setText(gn_1a);
            tv_2a_gn.setText(gn_2a);
            tv_3a_gn.setText(gn_3a);
            tv_sl_gn.setText(gn_sl);

            tv_1a_tq.setText(tq_1a);
            tv_2a_tq.setText(tq_2a);
            tv_3a_tq.setText(tq_3a);
            tv_sl_tq.setText(tq_sl);

            tv_1a_ld.setText(ld_1a);
            tv_2a_ld.setText(ld_2a);
            tv_3a_ld.setText(ld_3a);
            tv_sl_ld.setText(ld_sl);

            tv_1a_pt.setText(pt_1a);
            tv_2a_pt.setText(pt_2a);
            tv_3a_pt.setText(pt_3a);
            tv_sl_pt.setText(pt_sl);
            return v;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "des", Toast.LENGTH_SHORT).show();
        finish();

    }
}
