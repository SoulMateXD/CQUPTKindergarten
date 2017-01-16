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
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickLitener mOnItemClickLitener;
    List<String>newsList;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public NewsListAdapter(Context context, List<String>notices){
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
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_newslist,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        if (mOnItemClickLitener!=null){
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.mCardView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTvTitle;
        private TextView mTvTime;
        private ImageView mImgNews;
        public NewsViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mImgNews = (ImageView) itemView.findViewById(R.id.img_news);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_news_time);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
        }
    }
}
