package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by SoulMateXD on 2017/2/19.
 */

public class ClassAlbumGridVIew extends GridView {
    public ClassAlbumGridVIew(Context context) {
        super(context);
    }

    public ClassAlbumGridVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
