package com.cqupt.kindergarten.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.ImageChatBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/6/5.
 */

public class ImageChatAdapter extends RecyclerView.Adapter<ImageChatAdapter.ImageChatViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<ImageChatBean> datas;
    private RecyclerOnclickInterface onClick;

    public ImageChatAdapter(Context context, ArrayList<ImageChatBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ImageChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageChatViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageChatViewHolder holder, int position) {
        ImageChatBean bean = datas.get(position);

        String RESPONSE_TEXT = " 回复 ";
        SpannableString string = null;

        //注意，这里的span 必须得两个不同的对象，同一对象set两遍的话会覆盖掉前一个
        if (bean.getPtwoId() != null) { // 回复评论
            string = new SpannableString(bean.getPoneId() + RESPONSE_TEXT + bean.getPtwoId()
                            + " : "+ bean.getComContent());
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#81dbeb"));
            ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#81dbeb"));
            string.setSpan(colorSpan1, 0, bean.getPoneId().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            string.setSpan(colorSpan2, bean.getPoneId().length() + RESPONSE_TEXT.length(),
                    bean.getPoneId().length() + RESPONSE_TEXT.length() + bean.getPtwoId().length(),
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }else {  // 普通评论
            string = new SpannableString(bean.getPoneId()
                    + " : "+ bean.getComContent());
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#81dbeb"));
            string.setSpan(colorSpan1, 0, bean.getPoneId().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        holder.comment.setText(string);
        holder.comment.setOnClickListener(this);
        holder.comment.setTag(R.id.recycler_item_position_tag, position);
    }

    public void setOnClick(RecyclerOnclickInterface onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        onClick.onClick(v, (Integer) v.getTag(R.id.recycler_item_position_tag));
    }

    class ImageChatViewHolder extends RecyclerView.ViewHolder{
        private TextView comment;

        public ImageChatViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
