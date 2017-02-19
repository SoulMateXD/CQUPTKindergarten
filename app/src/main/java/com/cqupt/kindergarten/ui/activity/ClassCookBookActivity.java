package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.util.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ClassCookBookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Banner mBanner;
    private RecyclerView recyclerView;
    private ArrayList<String> imgUrls = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cook_book);

        toolbar = (Toolbar) findViewById(R.id.cook_book_toolbar);
        toolbar.setTitle("");
        toolbar.setTitle("班级食谱");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatas();
        mBanner = (Banner) findViewById(R.id.cook_book_banner);
        mBanner.setImages(imgUrls)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(3000)
                .start();

        recyclerView = (RecyclerView) findViewById(R.id.cook_book_recycler);
        CookBookAdapter adapter = new CookBookAdapter(this, names);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void initDatas() {
        imgUrls.add("http://img3.redocn.com/tupian/20141115/yuxiangrousi1_3457403.jpg");
        imgUrls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3860735185,1493309562&fm=23&gp=0.jpg");
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487928750&di=ef40869e5a77a5407f696dfca433ea59&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.yikul.cn%2Fimages%2F201403%2Fgoods_img%2F21813_G_1393888359119.jpg");
        names.add("鱼香肉丝");
        names.add("肉末茄子");
        names.add("番茄鸡蛋");
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

    class CookBookAdapter extends RecyclerView.Adapter<CookBookViewHolder>{
        private ArrayList<String> datas;
        private LayoutInflater layoutInflater;
        CookBookAdapter(Context context, ArrayList<String> datas){
            layoutInflater = LayoutInflater.from(context);
            this.datas = datas;
        }

        @Override
        public CookBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CookBookViewHolder viewHolder =
                    new CookBookViewHolder(layoutInflater.inflate(R.layout.item_cookbook_names, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CookBookViewHolder holder, int position) {
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class CookBookViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public CookBookViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_cook_book_text);
        }
    }

}
