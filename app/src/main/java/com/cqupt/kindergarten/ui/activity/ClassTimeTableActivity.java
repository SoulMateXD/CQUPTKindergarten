package com.cqupt.kindergarten.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.ClassTimeTableGridAdapter;
import com.cqupt.kindergarten.bean.LessonDataBean;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.LogUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ClassTimeTableActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private Toolbar toolbar;
    private ArrayList<String> list = new ArrayList<>();
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<Bean> data = new ArrayList<>();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private LogUtil logUtil;

    private static final int TIME_OUT = 0;
    private static final int SUCCESS = 1;
    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final int TEACHER = 0;
    private static final int PARENT = 1;

    private static final String POSTURL = "http://119.29.53.178:8080/kindergarden/LessonAppShow";
    private static final String POSTKEY = "lessonIdJson";
    private String POSTVALUE;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_table);
        ButterKnife.bind(this);

        logUtil = new LogUtil("ClassTimeTableActivity");

        initData();

        toolbar = (Toolbar) findViewById(R.id.class_timetable_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.activity_class_timetable_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbarTitle.setText(data.get(position).getWeek());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter = new ViewPagerAdapter(this, data);
        viewPager.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        showProgressDialog();
        getLocalValues();
//        for (int i=0; i<20; i++){
//            String string = null;
//            if (i%3 != 0){
//                string = "语文" + i;
//            }else {
//                string = "";
//            }
////            String string = "语文" + i;
//            list.add(string);
//        }
//        for (int i=1; i<10; i++){
//            Bean bean = new Bean(list, "第 " + i + " 周");
//            data.add(bean);
//        }
//        ArrayList<String> keys = new ArrayList<>();
//        ArrayList<String> values = new ArrayList<>();
//        keys.add(POSTKEY);
//        values.add(POSTVALUE);
//        HttpUtil.mOkHttpPost(POSTURL, keys, values, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message message = new Message();
//                message.what = TIME_OUT;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Message message = new Message();
//                message.what = SUCCESS;
//                message.obj = response.body().string();
//                handler.sendMessage(message);
//            }
//        });
        Map<String, Object> map = new HashMap<>();
        map.put(POSTKEY, POSTVALUE);
        HttpUtil.mOkHttpPost(POSTURL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = TIME_OUT;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    private void dealWithResponse(String response) {
        ArrayList<LessonDataBean> beans = GsonUtil.jsonToArrayList(response, LessonDataBean.class);
        logUtil.d("AAA" + response);
        for (int i = 0; i < beans.size(); i++) {
            list.clear();
            LessonDataBean bean = beans.get(i);
            list.addAll(new ArrayList<>(Arrays.asList(bean.getlMon().split(","))));
            list.addAll(new ArrayList<>(Arrays.asList(bean.getlTue().split(","))));
            list.addAll(new ArrayList<>(Arrays.asList(bean.getlWed().split(","))));
            list.addAll(new ArrayList<>(Arrays.asList(bean.getlThu().split(","))));
            list.addAll(new ArrayList<>(Arrays.asList(bean.getLfri().split(","))));
            Bean bean1 = new Bean(list, "第 " + bean.getlWeek() + " 周");
            data.add(bean1);
        }
        adapter.notifyDataSetChanged();
    }

    private void getLocalValues() {
        sharedPreferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        type = sharedPreferences.getInt("TYPE", 0);
        if (type == PARENT) {
            Parent parent = DataSupport.findFirst(Parent.class);
            POSTVALUE = parent.getcId();
        } else if (type == TEACHER) {
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            POSTVALUE = teacher.getcId();
        }
    }

    /*
    *   请求数据，转圈圈
    * */
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(ClassTimeTableActivity.this);
        progressDialog.setTitle("请等待");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    if (progressDialog.isShowing()) {
                        Message message = new Message();
                        message.what = TIME_OUT;
                        handler.sendMessage(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    *   让ProgressDialog消失
    * */
    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int response = msg.what;
            switch (response) {
                case TIME_OUT:
                    Toast.makeText(getApplicationContext(), "您的网络有点不给力噢！请检查下网络设置^.^", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    String dataResponse = (String) msg.obj;
                    dealWithResponse(dataResponse);
            }
            dismissProgressDialog();
        }
    };

    private class ViewPagerAdapter extends PagerAdapter {

        private ArrayList<Bean> data;
        private Context context;
        private GridView gridView;

        ViewPagerAdapter(Context context, ArrayList<Bean> data) {
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

    public class Bean {
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
