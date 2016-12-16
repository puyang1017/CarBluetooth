package com.puy.carbluetooth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import custom.HorizontalListView;

/**
 * Created by puy on 2016-12-14.
 */

public class FragmentThree extends Fragment {
    private View mView;
    private HorizontalListView horizontalListView;
    private MyAdapter myAdapter;
    private ArrayList<Integer> datas  = new ArrayList<>();
    private ArrayList<String> dataInfos = new ArrayList<>();
    private ArrayList<String> dataStrs = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_three, container, false);

        horizontalListView = (HorizontalListView) mView.findViewById(R.id.HorizontalListView);
        datas  = new ArrayList<>();
        dataInfos = new ArrayList<>();
        dataStrs = new ArrayList<>();
        datas.add(R.mipmap.picture_1);
        datas.add(R.mipmap.picture_2);
        datas.add(R.mipmap.picture_3);
        datas.add(R.mipmap.picture_4);


        dataInfos.add("Smart Device Control");
        dataInfos.add("Reuse");
        dataInfos.add("3D Puzzle");
        dataInfos.add("Fine Contorl");
        dataInfos.add("Fine Contorl");


        dataStrs.add("不需要特定遥控器");
        dataStrs.add("可以再次回收利用蓝牙模\n块和点击来平其他模型的车");
        dataStrs.add("提高孩子的空间想象能力");
        dataStrs.add("良好的模型操作杆");
        dataStrs.add("良好的模型操作杆");




        myAdapter = new MyAdapter(this.getContext(),datas,dataInfos,dataStrs);
        horizontalListView.setAdapter(myAdapter);

        return mView;
    }






    class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Context context;
        private ArrayList<Integer> datas;
        private ArrayList<String> dataInfos;
        private ArrayList<String> dataStrs;

        public MyAdapter(Context context,ArrayList<Integer> datas,ArrayList<String> dataInfos,ArrayList<String> dataStrs) {
            this.context = context;
            layoutInflater = layoutInflater.from(context);
            this.datas =datas;
            this.dataInfos = dataInfos;
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
                convertView = layoutInflater.inflate(R.layout.layout_recommend_item, null);
                h.image = (ImageView) convertView.findViewById(R.id.image);
                h.info = (TextView) convertView.findViewById(R.id.info);
                h.text = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(h);

            }else{
                h = (ViewHolder) convertView.getTag();
            }

            h.image.setBackgroundResource(datas.get(position));
            h.info.setText(dataInfos.get(position));
            h.text.setText(dataStrs.get(position));
            return convertView;
        }


    }


    static class ViewHolder {
        ImageView image;
        TextView info;
        TextView text;
    }
}
