package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.ImageItemBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;
import com.cqupt.kindergarten.util.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/5/18.
 */

public class CollegeAlbumInnerAdapter extends RecyclerView.Adapter<CollegeAlbumInnerAdapter.MyViewHolder>
        implements View.OnClickListener{
    private Context context;
    private ArrayList<ImageItemBean> datas;
    private RecyclerOnclickInterface onclickListener;

    public CollegeAlbumInnerAdapter(Context context, ArrayList<ImageItemBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setOnclickListener(RecyclerOnclickInterface onclickListener) {
        this.onclickListener = onclickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_college_album_inner_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageItemBean bean = datas.get(position);
        Glide.with(context)
                .load(bean.getXcAdress())
                .placeholder(R.drawable.default_image)
                .thumbnail(0.05f)
                .crossFade()
                .centerCrop()
                .into(holder.imageView);

        holder.imageView.setTag(R.id.recycler_item_position_tag, position);
        holder.imageView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        onclickListener.onClick(v, (Integer) v.getTag(R.id.recycler_item_position_tag));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ScreenUtils screenUtils = new ScreenUtils(context);
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams
                            ((screenUtils.getScreenWidth() - screenUtils.dip2px(26)) / 3,
                                    (screenUtils.getScreenWidth() - screenUtils.dip2px(20)) / 3);
            itemView.setLayoutParams(lp);
            imageView = (ImageView) itemView.findViewById(R.id.item_college_album_inner_image);

        }
    }
}
