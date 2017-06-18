package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.AlbumAdapterBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/5/15.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<AlbumAdapterBean> datas;
    private RecyclerOnclickInterface onclick;

    public AlbumAdapter(Context context, ArrayList<AlbumAdapterBean> datas){
        this.context = context;
        this.datas = datas;
//        initData();
    }

//    private void initData() {
//        datas = new ArrayList<>();
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849308526&di=a771722d56dfd26c80da875e6235a4b9&imgtype=0&src=http%3A%2F%2Fpic.35pic.com%2Fnormal%2F07%2F86%2F41%2F2005877_195923547000_2.jpg"
//        , "54张", "运动会"));
//        datas.add(new AlbumAdapterBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3932087489,654476655&fm=23&gp=0.jpg"
//                ,"594张", "春游"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849406093&di=bd18c6482b4259e5fb7bd83a6f83ac1d&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F150714%2F14-150G4093021G3.jpg",
//                "243张", "秋游"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849421042&di=f0090eeee27e816b38c4af029cf7eb27&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20111215%2F8028661_094311774000_2.png",
//                "563张", "家长会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849434437&di=c5bbd14d12bb73a32b55dc02fcb50c31&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110930%2F109-11093014415452.jpg",
//                "83张", "开学典礼"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849308526&di=a771722d56dfd26c80da875e6235a4b9&imgtype=0&src=http%3A%2F%2Fpic.35pic.com%2Fnormal%2F07%2F86%2F41%2F2005877_195923547000_2.jpg"
//                , "54张", "运动会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849388381&di=a43db92bb31b82e8551e9e499f7cc4d9&imgtype=0&src=http%3A%2F%2Fd.5857.com%2Fkatd_151019%2Fdesk_001.jpg",
//                "594张", "春游"));
//        datas.add(new AlbumAdapterBean(
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849406093&di=bd18c6482b4259e5fb7bd83a6f83ac1d&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F150714%2F14-150G4093021G3.jpg",
//                "243张", "秋游"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849421042&di=f0090eeee27e816b38c4af029cf7eb27&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20111215%2F8028661_094311774000_2.png",
//                "563张", "家长会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849434437&di=c5bbd14d12bb73a32b55dc02fcb50c31&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110930%2F109-11093014415452.jpg",
//                "83张", "开学典礼"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849308526&di=a771722d56dfd26c80da875e6235a4b9&imgtype=0&src=http%3A%2F%2Fpic.35pic.com%2Fnormal%2F07%2F86%2F41%2F2005877_195923547000_2.jpg"
//                , "54张", "运动会"));
//        datas.add(new AlbumAdapterBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3932087489,654476655&fm=23&gp=0.jpg",
//                "594张", "春游"));
//        datas.add(new AlbumAdapterBean(
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849406093&di=bd18c6482b4259e5fb7bd83a6f83ac1d&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F150714%2F14-150G4093021G3.jpg",
//                "243张", "秋游"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849421042&di=f0090eeee27e816b38c4af029cf7eb27&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20111215%2F8028661_094311774000_2.png",
//                "563张", "家长会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849434437&di=c5bbd14d12bb73a32b55dc02fcb50c31&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110930%2F109-11093014415452.jpg",
//                "83张", "开学典礼"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849308526&di=a771722d56dfd26c80da875e6235a4b9&imgtype=0&src=http%3A%2F%2Fpic.35pic.com%2Fnormal%2F07%2F86%2F41%2F2005877_195923547000_2.jpg"
//                , "54张", "运动会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849388381&di=a43db92bb31b82e8551e9e499f7cc4d9&imgtype=0&src=http%3A%2F%2Fd.5857.com%2Fkatd_151019%2Fdesk_001.jpg",
//                "594张", "春游"));
//        datas.add(new AlbumAdapterBean(
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849406093&di=bd18c6482b4259e5fb7bd83a6f83ac1d&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F150714%2F14-150G4093021G3.jpg",
//                "243张", "秋游"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849421042&di=f0090eeee27e816b38c4af029cf7eb27&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20111215%2F8028661_094311774000_2.png",
//                "563张", "家长会"));
//        datas.add(new AlbumAdapterBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494849434437&di=c5bbd14d12bb73a32b55dc02fcb50c31&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110930%2F109-11093014415452.jpg",
//                "83张", "开学典礼"));
//
//    }

//    public ArrayList<AlbumAdapterBean> getDatas() {
//        return datas;
//    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_handbook_album, parent, false));
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Glide.with(context)
                .load(datas.get(position).getImageUrl())
                .placeholder(R.drawable.default_image)
                .into(holder.imageView);
        holder.albumTitle.setText(datas.get(position).getAlbumTitle());
        holder.albumNumber.setText(datas.get(position).getImageNumber());
        holder.view.setTag(position);
        holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnclick(RecyclerOnclickInterface onclick) {
        this.onclick = onclick;
    }

    @Override
    public void onClick(View v) {
        onclick.onClick(v, (Integer) v.getTag());
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView albumTitle;
        private TextView albumNumber;
        private View view;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.item_handbook_album_image);
            albumTitle = (TextView) itemView.findViewById(R.id.item_handbook_album_title);
            albumNumber = (TextView) itemView.findViewById(R.id.item_handbook_album_number);
        }
    }



}
