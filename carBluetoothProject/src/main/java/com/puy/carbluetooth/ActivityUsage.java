package com.puy.carbluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Util.Util;
import custom.HorizontalListView;

/**
 * Created by puy on 2016-12-14.
 */

public class ActivityUsage extends Activity {
    private HorizontalListView horizontalListView;
    private MyAdapter myAdapter;
    private ArrayList<Integer> datas  = new ArrayList<>();
    private ArrayList<String> dataStrs = new ArrayList<>();
    private ImageView back;
    private TextView rec;
    private TextView problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);
        initView();
        initData();
    }

    private void initView() {
        horizontalListView = (HorizontalListView) findViewById(R.id.HorizontalListView);
        back  = (ImageView) findViewById(R.id.back);
        rec = (TextView) findViewById(R.id.rec);
        problem = (TextView) findViewById(R.id.problem);
    }

    private void initData() {
        datas.add(R.mipmap.picture_01);
        datas.add(R.mipmap.picture_02);
        datas.add(R.mipmap.picture_03);
        datas.add(R.mipmap.picture_04);
        datas.add(R.mipmap.picture_05);
        datas.add(R.mipmap.picture_06);
        datas.add(R.mipmap.picture_07);
        dataStrs.add("按照说明书来组装");
        dataStrs.add("下载APP");
        dataStrs.add("打开APP后连接蓝牙");
        dataStrs.add("操作");
        dataStrs.add("蓝牙模块和电机分解");
        dataStrs.add("按照其他组装说明来组装");
        dataStrs.add("操作");



        myAdapter = new MyAdapter(getApplicationContext(),datas,dataStrs);
        horizontalListView.setAdapter(myAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityRecommend.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                finish();
            }
        });


        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityProblem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                finish();
            }
        });

    }


    class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Context context;
        private ArrayList<Integer> datas;
        private ArrayList<String> dataStrs;

        public MyAdapter(Context context,ArrayList<Integer> datas,ArrayList<String> dataStrs) {
            this.context = context;
            layoutInflater = layoutInflater.from(context);
            this.datas =datas;
            this.dataStrs = dataStrs;

        }

        @Override
        public int getCount() {
            if(datas.size() == 0){
                return  0;
            }else {
                return datas.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder h;
            if (convertView == null) {
                h = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.layout_usage_item, null);
                h.image = (ImageView) convertView.findViewById(R.id.image);
                h.text = (TextView) convertView.findViewById(R.id.text);
                h.text1 = (TextView) convertView.findViewById(R.id.text1);
                h.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout);
                convertView.setTag(h);

            }else{
                h = (ViewHolder) convertView.getTag();
            }
            h.relativeLayout.setVisibility(View.INVISIBLE);
            h.image.setBackgroundResource(datas.get(position));
            h.text.setText(dataStrs.get(position));
            if(position==0){
                h.text1.setText("使用方法");
                h.relativeLayout.setVisibility(View.VISIBLE);
            }else if(position==4){
                h.text1.setText("只购买外部模型的时候");
                h.relativeLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layoutParams1.setMargins(Util.dp2px(25,getApplicationContext()),0,0,0);
                layoutParams2.setMargins(Util.dp2px(25,getApplicationContext()),Util.dp2px(20,getApplicationContext()),0,0);
                layoutParams3.setMargins(Util.dp2px(25,getApplicationContext()),Util.dp2px(5,getApplicationContext()),0,0);
                h.relativeLayout.setLayoutParams(layoutParams1);
                h.image.setLayoutParams(layoutParams2);
                h.text.setLayoutParams(layoutParams3);
            }

            return convertView;
        }


    }


    static class ViewHolder {
        ImageView image;
        TextView text;
        TextView text1;
        RelativeLayout relativeLayout;
    }
}
