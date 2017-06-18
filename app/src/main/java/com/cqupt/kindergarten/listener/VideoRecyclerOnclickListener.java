package com.cqupt.kindergarten.listener;

/**
 * Created by SoulMateXD on 2017/5/17.
 */

public interface VideoRecyclerOnclickListener {

    void onVideoImageClicked(int position);
    void onVideoDianzanClicked(int position, boolean beforeState, boolean currentState);
    void onVideoCollectClicked(int position, boolean beforeState, boolean currentState);
    void onVideoCommentClicked(int position);
}
