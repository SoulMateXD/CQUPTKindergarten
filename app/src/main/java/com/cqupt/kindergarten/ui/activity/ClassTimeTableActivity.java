package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.ClassTimeTableGridAdapter;

import java.util.ArrayList;

public class ClassTimeTableActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> list = new ArrayList<>();
    private ViewPager viewPager;
    private ArrayList<Bean> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_table);

        initData();

        toolbar = (Toolbar) findViewById(R.id.class_timetable_toolbar);
        toolbar.setTitle("第1周");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.activity_class_timetable_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar.setTitle(data.get(position).getWeek());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new ViewPagerAdapter(this, data));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        for (int i=0; i<20; i++){
            String string = null;
            if (i%3 != 0){
                string = "语文" + i;
            }else {
                string = "";
            }
//            String string = "语文" + i;
            list.add(string);
        }
        for (int i=1; i<10; i++){
            Bean bean = new Bean(list, "第 " + i + " 周");
            data.add(bean);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private ArrayList<Bean> data;
        private Context context;
        private GridView gridView;

        ViewPagerAdapter(Context context, ArrayList<Bean> data){
            this.data = data;
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.
                    from(context).inflate(R.layout.fragment_class_timetable_item, null);
            gridView = (GridView) itemView.findViewById(R.id.class_timetable_gridview);
            ClassTimeTableGridAdapter adapter =
                    new ClassTimeTableGridAdapter(data.get(position).getList(), context, gridView);
            gridView.setAdapter(adapter);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class Bean{
        ArrayList<String> list;
        String week;

        public Bean(ArrayList<String> list, String week) {
            this.list = list;
            this.week = week;
        }

        public ArrayList<String> getList() {
            return list;
        }

        public void setList(ArrayList<String> list) {
            this.list = list;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
