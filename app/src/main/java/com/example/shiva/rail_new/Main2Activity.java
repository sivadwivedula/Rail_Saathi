package com.example.shiva.rail_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Main2Activity extends AppCompatActivity {
        ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv=(ListView)findViewById(R.id.listview_new);

    }
}
