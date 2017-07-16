package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.VideoListBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> implements View.OnClickListener{
    private ArrayList<VideoListBean> datas;
    private Context context;
    private RecyclerOnclickInterface onclick;

    public VideoListAdapter(Context context, ArrayList<VideoListBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videolist, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, int position) {
        VideoListBean bean = datas.get(position);
        holder.videoListContent.setText(bean.getMccontent());
        holder.videoListPeople.setText(bean.getMcpeople());
        holder.videoListTime.setText(bean.getMctime());
        holder.view.setTag(position);
        holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnclick(RecyclerOnclickInterface onclick) {
        this.onclick = onclick;
    }

    @Override
    public void onClick(View v) {
        onclick.onClick(v, (Integer) v.getTag());
    }

    class VideoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_list_people)
        TextView videoListPeople;
        @BindView(R.id.video_list_time)
        TextView videoListTime;
        @BindView(R.id.video_list_content)
        TextView videoListContent;
        @BindView(R.id.cardview)
        CardView view;


        public VideoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
