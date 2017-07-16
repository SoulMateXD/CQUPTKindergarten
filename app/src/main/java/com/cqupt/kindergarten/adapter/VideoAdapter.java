package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.VideoBean;
import com.cqupt.kindergarten.listener.VideoRecyclerOnclickListener;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/5/16.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<VideoBean> datas;
    private VideoRecyclerOnclickListener onClickListener;

    private static final int BEFORESTATE = 1;
    private static final int POSITION = 2;

    public VideoAdapter(Context context, ArrayList<VideoBean> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_handbook_video, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VideoBean bean = datas.get(position);
        Glide.with(context)
                .load(bean.getMface())
                .placeholder(R.drawable.default_image)
                .into(holder.videoImage);
        holder.videoDate.setText(bean.getMvDate());
//        if (bean.getCollect()){
        if (false){
            holder.collectImage.setImageResource(R.drawable.collect_clicked);
        }else {
            holder.collectImage.setImageResource(R.drawable.collect_unclicked);
        }
//        if (bean.getDianzan()){
        if (false){
            holder.dianzanImage.setImageResource(R.drawable.dianzan_clicked);
        }else {
            holder.dianzanImage.setImageResource(R.drawable.dianzan_unclicked);
        }

        holder.videoImage.setTag(R.id.video_adapter_position, position);
        holder.videoImage.setOnClickListener(this);

        //在给Tag定义key时要保持key的唯一性，因而要在resource中定义
        //static final 的key不唯一
        holder.dianzanImage.setTag(R.id.video_adapter_position, position);
        holder.dianzanImage.setOnClickListener(this);

        holder.collectImage.setTag(R.id.video_adapter_position, position);
        holder.collectImage.setOnClickListener(this);

        holder.comment.setTag(R.id.video_adapter_position, position);
        holder.comment.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnClickListener(VideoRecyclerOnclickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_video_message_icon:
            case R.id.item_video_image:
                onClickListener.onVideoImageClicked((Integer) v.getTag(R.id.video_adapter_position));
                break;
            case R.id.item_video_dianzan:
                int position  = (int) v.getTag(R.id.video_adapter_position);
//                boolean beforeState = datas.get(position).getDianzan();
                boolean beforeState = false;
                dianzanSwitch((ImageView) v, !beforeState);
//                datas.get(position).setDianzan(!beforeState);
                onClickListener.onVideoDianzanClicked(position, beforeState, !beforeState);
                break;
            case R.id.item_video_collect:
                int position1  = (int) v.getTag(R.id.video_adapter_position);
//                boolean beforeState1 = datas.get(position1).getCollect();
                boolean beforeState1 = false;
                collectSwitch((ImageView) v, !beforeState1);
//                datas.get(position1).setCollect(!beforeState1);
                onClickListener.onVideoCollectClicked(position1, beforeState1, !beforeState1);
                break;
        }
    }

    private void dianzanSwitch(ImageView image, boolean currentState){
        if (currentState)
            image.setImageResource(R.drawable.dianzan_clicked);
        else
            image.setImageResource(R.drawable.dianzan_unclicked);
    }

    private void collectSwitch(ImageView image, boolean currentState){
        if (currentState)
            image.setImageResource(R.drawable.collect_clicked);
        else
            image.setImageResource(R.drawable.collect_unclicked);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoImage;
        private TextView videoDate;
        private ImageView collectImage;
        private ImageView dianzanImage;
        private ImageView comment;


        public MyViewHolder(View itemView) {
            super(itemView);
            videoImage = (ImageView) itemView.findViewById(R.id.item_video_image);
            videoDate = (TextView) itemView.findViewById(R.id.item_video_time_text);
            collectImage = (ImageView) itemView.findViewById(R.id.item_video_collect);
            dianzanImage = (ImageView) itemView.findViewById(R.id.item_video_dianzan);
            comment = (ImageView) itemView.findViewById(R.id.item_video_message_icon);
        }
    }

//    class Bean {
//        private String videoImageUrl;
//        private String date;
//        private Boolean isDianzan;
//        private Boolean isCollect;
//        private String videoTime;
//
//        public Bean(String videoImageUrl, String date, Boolean isDianzan, Boolean isCollect, String videoTime) {
//            this.videoImageUrl = videoImageUrl;
//            this.date = date;
//            this.isDianzan = isDianzan;
//            this.isCollect = isCollect;
//            this.videoTime = videoTime;
//        }
//
//        public String getVideoImageUrl() {
//            return videoImageUrl;
//        }
//
//        public void setVideoImageUrl(String videoImageUrl) {
//            this.videoImageUrl = videoImageUrl;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public Boolean getDianzan() {
//            return isDianzan;
//        }
//
//        public void setDianzan(Boolean dianzan) {
//            isDianzan = dianzan;
//        }
//
//        public Boolean getCollect() {
//            return isCollect;
//        }
//
//        public void setCollect(Boolean collect) {
//            isCollect = collect;
//        }
//
//        public String getVideoTime() {
//            return videoTime;
//        }
//
//        public void setVideoTime(String videoTime) {
//            this.videoTime = videoTime;
//        }
//    }
//
//    private void initData() {
//        datas = new ArrayList<>();
//        datas.add(new Bean("http://img0.pconline.com.cn/pconline/1405/14/4764636_10_thumb.jpg",
//                "2016-8-6", true, false, "12:20'"));
//        datas.add(new Bean("http://images.wandafilm.com/uploadServer/resource/images/2014/04/20140415160802803080.jpg",
//                "2017-3-2", true, true, "10:11'"));
//        datas.add(new Bean("http://pic38.nipic.com/20140224/6608733_102528639000_2.jpg",
//                "2017-5-13", false, false, "90:33'"));
//        datas.add(new Bean("http://img3.redocn.com/tupian/20140925/dianyingyuansuyunzuoshipin_1947793.jpg",
//                "2011-9-10", false, true, "15:06"));
//        datas.add(new Bean("http://pic38.nipic.com/20140211/1361020_000701112000_2.jpg",
//                "2018-6-30", true, true, "12:08"));
//        datas.add(new Bean("http://img0.pconline.com.cn/pconline/1405/14/4764636_10_thumb.jpg",
//                "2016-8-6", true, false, "12:20'"));
//        datas.add(new Bean("http://images.wandafilm.com/uploadServer/resource/images/2014/04/20140415160802803080.jpg",
//                "2017-3-2", true, true, "10:11'"));
//        datas.add(new Bean("http://pic38.nipic.com/20140224/6608733_102528639000_2.jpg",
//                "2017-5-13", false, false, "90:33'"));
//        datas.add(new Bean("http://img3.redocn.com/tupian/20140925/dianyingyuansuyunzuoshipin_1947793.jpg",
//                "2011-9-10", false, true, "15:06"));
//        datas.add(new Bean("http://pic38.nipic.com/20140211/1361020_000701112000_2.jpg",
//                "2018-6-30", true, true, "12:08"));
//        datas.add(new Bean("http://img0.pconline.com.cn/pconline/1405/14/4764636_10_thumb.jpg",
//                "2016-8-6", true, false, "12:20'"));
//        datas.add(new Bean("http://images.wandafilm.com/uploadServer/resource/images/2014/04/20140415160802803080.jpg",
//                "2017-3-2", true, true, "10:11'"));
//        datas.add(new Bean("http://pic38.nipic.com/20140224/6608733_102528639000_2.jpg",
//                "2017-5-13", false, false, "90:33'"));
//        datas.add(new Bean("http://img3.redocn.com/tupian/20140925/dianyingyuansuyunzuoshipin_1947793.jpg",
//                "2011-9-10", false, true, "15:06"));
//        datas.add(new Bean("http://pic38.nipic.com/20140211/1361020_000701112000_2.jpg",
//                "2018-6-30", true, true, "12:08"));
//        datas.add(new Bean("http://img0.pconline.com.cn/pconline/1405/14/4764636_10_thumb.jpg",
//                "2016-8-6", true, false, "12:20'"));
//        datas.add(new Bean("http://images.wandafilm.com/uploadServer/resource/images/2014/04/20140415160802803080.jpg",
//                "2017-3-2", true, true, "10:11'"));
//        datas.add(new Bean("http://pic38.nipic.com/20140224/6608733_102528639000_2.jpg",
//                "2017-5-13", false, false, "90:33'"));
//        datas.add(new Bean("http://img3.redocn.com/tupian/20140925/dianyingyuansuyunzuoshipin_1947793.jpg",
//                "2011-9-10", false, true, "15:06"));
//        datas.add(new Bean("http://pic38.nipic.com/20140211/1361020_000701112000_2.jpg",
//                "2018-6-30", true, true, "12:08"));
//        datas.add(new Bean("http://img0.pconline.com.cn/pconline/1405/14/4764636_10_thumb.jpg",
//                "2016-8-6", true, false, "12:20'"));
//        datas.add(new Bean("http://images.wandafilm.com/uploadServer/resource/images/2014/04/20140415160802803080.jpg",
//                "2017-3-2", true, true, "10:11'"));
//        datas.add(new Bean("http://pic38.nipic.com/20140224/6608733_102528639000_2.jpg",
//                "2017-5-13", false, false, "90:33'"));
//        datas.add(new Bean("http://img3.redocn.com/tupian/20140925/dianyingyuansuyunzuoshipin_1947793.jpg",
//                "2011-9-10", false, true, "15:06"));
//        datas.add(new Bean("http://pic38.nipic.com/20140211/1361020_000701112000_2.jpg",
//                "2018-6-30", true, true, "12:08"));
//    }
}
