package com.example.shiva.rail_new;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class activity2 extends AppCompatActivity {
LayoutInflater l;
    static  String s_cl="SL";
    static String s_qu="GN";
    ListView listView;
    static  myadapter1 ma;
    Spinner sp1,sp2;
    static LinearLayout ll;
    static  ArrayAdapter<String> cl;
    static  ArrayAdapter<String> qu;
    static LinearLayout ll1;
    static Button b , b1;
    static TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        b=(Button)findViewById(R.id.b);
        b1=(Button)findViewById(R.id.b1);
        b.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.GONE);
        tv1=(TextView)findViewById(R.id.tv1);
        tv1.setVisibility(View.GONE);
        listView = (ListView)findViewById(R.id.listview);

        tv1.setText("there are no trains from "+MainActivity.et_source.getText().toString()+"to "+MainActivity.et_dest.getText().toString());


        MainActivity.detail1.clear();

        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);

        final ArrayList<String> arr1= new ArrayList();
        arr1.add("SL");
        arr1.add("1A");
        arr1.add("2A");
        arr1.add("3A");
        cl= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr1);
        sp1.setAdapter(cl);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_cl=arr1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayList<String> arr2 = new ArrayList<>();
        arr2.add("GN");
        arr2.add("TQ");
        arr2.add("PT");
        arr2.add("LD");

        qu= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr2);
        sp2.setAdapter(qu);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_qu=arr2.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        l=getLayoutInflater();

        String temp1= MainActivity.et_source.getText().toString();
        String temp2=MainActivity.et_dest.getText().toString();
        String ur="https://api.railwayapi.com/v2/between/source/"+temp1+"/dest/"+temp2+"/date/"+MainActivity.et_date.getText().toString()+"/apikey/"+MainActivity.apikey+"/";
        ur="http://indianrailapi.com/api/v1/trainsbetweenstations/apikey/e26862e8447e2a6bfadcebaf57e331db/date/20171130/source/R/destination/VSKP/";
        ma= new myadapter1();

        gettrains gt = new gettrains(ma);
        gt.execute(ur);

        listView.setAdapter(ma);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.detail1.clear();
                getintermediate obj = new getintermediate();

                String temp ="NGP";

                String ur="https://api.railwayapi.com/v2/between/source/"+MainActivity.et_source.getText().toString()+"/dest/"+temp+"/date/"+MainActivity.et_date.getText().toString()+"/apikey/"+MainActivity.apikey+"/";
                gettrains obj1 = new gettrains(ma);
                obj1.execute(ur);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.detail1.clear();
                getintermediate obj = new getintermediate();

                String temp ="NGP";

                String ur="https://api.railwayapi.com/v2/between/source/"+temp+"/dest/"+MainActivity.et_dest.getText().toString()+"/date/"+MainActivity.et_date.getText().toString()+"/apikey/"+MainActivity.apikey+"/";
                gettrains obj1 = new gettrains(ma);
                obj1.execute(ur);
            }
        });

    }
    static public void set(){
        tv1.setVisibility(View.VISIBLE);
       b.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
    }
    public  void temp(View v){
        Collections.sort(MainActivity.detail1, new Comparator<HashMap>() {

            @Override
            public int compare(HashMap o1, HashMap o2) {
                activity2.ma.notifyDataSetChanged();

                traindetail obj1 = (traindetail) o1.get("val");
                traindetail obj2 = (traindetail) o2.get("val");

                String av1 = obj1.getavail(s_cl, s_qu);
                String av2 = obj2.getavail(s_cl, s_qu);


                preci pobj = new preci();
                String[] arr1 = pobj.getprecidence(av1);
                pobj= new preci();
                String[] arr2 = pobj.getprecidence(av2);
                int q = Integer.parseInt(arr1[0]);
                int q1 = Integer.parseInt(arr1[1]);

                int w = Integer.parseInt(arr2[0]);
                int w1 = Integer.parseInt(arr2[1]);

                if (q > w) {
                    return 1;
                }
                else if(q<w){
                    return -1;
                }
                else {
                    if(q1>w1){
                        return -1;
                    }
                    else if(q1<w1){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        });
    }

    class myadapter1 extends BaseAdapter {
        @Override
        public int getCount() {
            return MainActivity.detail1.size();
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
            TextView aa2 = (TextView)v.findViewById(R.id.vv11);
            TextView aa3 = (TextView)v.findViewById(R.id.vv12);
            TextView aa4 = (TextView)v.findViewById(R.id.vv13);
            TextView aa5 = (TextView)v.findViewById(R.id.vv14);


            HashMap hm =MainActivity.detail1.get(position);
            String no=hm.get("no").toString();
            String name = hm.get("name").toString();
            String tr_time=hm.get("tr_time").toString();
            String src=hm.get("src").toString();
            String dest=hm.get("dest").toString();
            String src_time=hm.get("src_time").toString();
            String dest_time=hm.get("dest_time").toString();
            aa.setText(name);
            aa1.setText(tr_time);
            aa2.setText(src+"->");
            aa3.setText(dest);
            aa4.setText(src_time);
            aa5.setText(dest_time);

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

}
