package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cqupt.kindergarten.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/16.
 */
public class KnowledgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickLitener mOnItemClickLitener;
    List<String>newsList;

    private static final int TYPE_KNOWLEDGE = 0;
    private static final int TYPE_VIDEO = 1;


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public KnowledgeAdapter(Context context, List<String>notices){
        notices = new ArrayList<>();
        this.mContext = context;
        this.newsList = notices;
        inflater = LayoutInflater.from(context);
    }



    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_KNOWLEDGE){
            View view = inflater.inflate(R.layout.item_knowledge,parent,false);
            return new KnowledgeViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_video,parent,false);
            return new VideoViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KnowledgeViewHolder){
            ((KnowledgeViewHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(((KnowledgeViewHolder) holder).mCardView, pos);
                }
            });
        }else {
            ((VideoViewHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(((VideoViewHolder) holder).mCardView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0){
            return TYPE_KNOWLEDGE;
        }else {
            return TYPE_VIDEO;
        }
    }

    public class KnowledgeViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTvTitle;
        private TextView mTvTime;
        private ImageView mImgKnowledge;
        public KnowledgeViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mImgKnowledge = (ImageView) itemView.findViewById(R.id.img_news);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_news_time);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
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
