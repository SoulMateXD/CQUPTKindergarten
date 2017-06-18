package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cqupt.kindergarten.R;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/1/17.
 */

public class ClassTimeTableGridAdapter extends BaseAdapter {
    ArrayList<String> datas;
    Context context;
    GridView gridView;

    public ClassTimeTableGridAdapter(ArrayList<String> list, Context context, GridView gridView){
        datas = list;
        this.context = context;
        this.gridView = gridView;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_class_timetable, null);
        }
        TextView text = (TextView) view.findViewById(R.id.item_timetable);
        if (datas.get(i).equals("")){
            view.setBackgroundResource(R.drawable.class_timetable_null_bg);
        }else {
            text.setText(datas.get(i));
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                (gridView.getWidth()-30)/5 , (gridView.getHeight()-80)/4);
        view.setLayoutParams(params);
        return view;
    }
}
