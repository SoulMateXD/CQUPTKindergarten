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
import com.cqupt.kindergarten.bean.NoticeListBean;
import com.cqupt.kindergarten.util.ScreenUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/16.
 */
public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.NewsViewHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickLitener mOnItemClickLitener;
    List<NoticeListBean>noticeList;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public NoticeListAdapter(Context context, List<NoticeListBean>  notices){
        this.mContext = context;
        this.noticeList = notices;
        inflater = LayoutInflater.from(context);
    }



    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_notice,parent,false);
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
            holder.mTvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.mCardView, pos);
                }
            });
        }
        NoticeListBean bean = noticeList.get(position);
        holder.mTvTime.setText(bean.getTime());
        holder.mTvTitle.setText(bean.getTitle());
        RichText.initCacheDir(mContext);
        RichText.fromHtml(bean.getMessage())
                .scaleType(ImageHolder.ScaleType.FIT_CENTER)
                .into(holder.mTvContent);

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvContent;
        public NewsViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_notice_time);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_notice_title);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_notice_content);
        }
    }
}
