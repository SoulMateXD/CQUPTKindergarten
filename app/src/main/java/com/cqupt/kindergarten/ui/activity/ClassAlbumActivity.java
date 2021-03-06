package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;

import java.util.ArrayList;

public class ClassAlbumActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<AlbumBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_album);

        initView();

        recyclerView.setAdapter(new AlbumAdapter(datas, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        ArrayList<Integer> imgs;
        for (int i=1; i<10; i++){
            imgs = new ArrayList<>();
            for (int j=1; j<=i; j++){
                imgs.add(R.drawable.class_timetable_bg);
            }
            AlbumBean data = new AlbumBean("2017年2月" + i + "日", imgs );
            datas.add(data);
        }
    }

    private void initView() {
        initData();
        toolbar = (Toolbar) findViewById(R.id.class_album_toolbar);
        toolbar.setTitle("班级相册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.class_album_recycler);
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

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumViewholder>{
        private ArrayList<AlbumBean> datas;
        private LayoutInflater inflater;
        private Context context;

        public AlbumAdapter(ArrayList<AlbumBean> datas, Context context) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public AlbumViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AlbumViewholder(inflater.inflate(R.layout.item_class_album, parent, false));
        }

        @Override
        public void onBindViewHolder(AlbumViewholder holder, int position) {
            AlbumBean data = datas.get(position);
            holder.time.setText(data.getTime());
            AlbumGridViewAdapter adapter = new AlbumGridViewAdapter(data.getImgs(), context, holder.gridView);
            adapter.setOnClickInterface(new OnClickInterface() {
                @Override
                public void onClick(View view, int position) {
                    Toast.makeText(context, "您点击了第"+ position + "个图片", Toast.LENGTH_SHORT).show();
                }
            });
            holder.gridView.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class AlbumViewholder extends RecyclerView.ViewHolder{

        private TextView time;
        private GridView gridView;

        public AlbumViewholder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_class_album_time);
            gridView = (GridView) itemView.findViewById(R.id.item_class_album_gridview);
        }
    }

    private class AlbumGridViewAdapter extends BaseAdapter implements View.OnClickListener{
        ArrayList<Integer> imgs;
        Context context;
        GridView gridView;
        OnClickInterface onClickInterface;

        AlbumGridViewAdapter(ArrayList<Integer> imgs, Context context, GridView gridView){
            this.imgs = imgs;
            this.context = context;
            this.gridView = gridView;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public Object getItem(int i) {
            return imgs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if (view == null){
                imageView = (ImageView) LayoutInflater.from(context).inflate
                        (R.layout.item_class_album_gridview, viewGroup, false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else {
                imageView = (ImageView) view;
            }
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            return imageView;
        }

        @Override
        public void onClick(View view) {
            onClickInterface.onClick(view, (Integer) view.getTag());
        }

        public void setOnClickInterface(OnClickInterface onClickInterface) {
            this.onClickInterface = onClickInterface;
        }
    }

    private interface OnClickInterface{
        void onClick(View view, int position);
    }

    private class AlbumBean{
        String time;
        ArrayList<Integer> imgs;

        public AlbumBean(String time, ArrayList<Integer> imgs) {
            this.time = time;
            this.imgs = imgs;
        }

        public String getTime() {
            return time;
        }

        public ArrayList<Integer> getImgs() {
            return imgs;
        }
    }
}
