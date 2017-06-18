package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.KnowledgeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/16.
 */
public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.KnowledgeViewHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickLitener mOnItemClickLitener;
    private ArrayList<KnowledgeBean> datas;

    private static final int TYPE_KNOWLEDGE = 0;
    private static final int TYPE_VIDEO = 1;


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public KnowledgeAdapter(Context context, ArrayList<KnowledgeBean> datas){
        this.mContext = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }



    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public KnowledgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_knowledge,parent,false);
        return new KnowledgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KnowledgeViewHolder holder, int position) {
        KnowledgeBean bean = datas.get(position);
        holder.webSiteUrl.setText(bean.getUrl2());
        Glide.with(mContext)
                .load(bean.getUrl1())
                .placeholder(R.drawable.default_image)
                .into(holder.mImgKnowledge);
        holder.mTvTitle.setText(bean.getTitle());
        holder.mTvTime.setText("发布时间 : " + bean.getTime());

        (holder).mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick((holder).mCardView, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_KNOWLEDGE;
    }

    public class KnowledgeViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTvTitle;
        private TextView mTvTime;
        private ImageView mImgKnowledge;
        private TextView webSiteUrl;

        public KnowledgeViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mImgKnowledge = (ImageView) itemView.findViewById(R.id.img_news);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_news_time);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            webSiteUrl = (TextView) itemView.findViewById(R.id.tv_news_url);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTvTime;
        private ImageView mImgKnowledge;
        public VideoViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mImgKnowledge = (ImageView) itemView.findViewById(R.id.img_news);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_news_time);
        }
    }
}
