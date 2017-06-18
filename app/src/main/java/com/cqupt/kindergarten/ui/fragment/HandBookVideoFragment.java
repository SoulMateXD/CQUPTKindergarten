package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.VideoAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.listener.VideoRecyclerOnclickListener;
import com.cqupt.kindergarten.ui.activity.VideoDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by SoulMateXD on 2017/5/9.
 */

public class HandBookVideoFragment extends BaseFragment {
    @BindView(R.id.video_recycler)
    RecyclerView videoRecycler;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_handbook_video;
    }

    @Override
    public void initView() {
        VideoAdapter adapter = new VideoAdapter(getContext());
        adapter.setOnClickListener(new VideoRecyclerOnclickListener() {
            @Override
            public void onVideoImageClicked(int position) {
                Intent intent = new Intent(getContext(), VideoDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onVideoDianzanClicked(int position, boolean beforeState, boolean currentState) {
                Toast.makeText(getContext(), "您为第" + position + "个点了赞！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCollectClicked(int position, boolean beforeState, boolean currentState) {
                Toast.makeText(getContext(), "您为第" + position + "个点了收藏", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCommentClicked(int position) {

            }
        });
        videoRecycler.setAdapter(adapter);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {

    }

    public static HandBookVideoFragment newInstance() {
        HandBookVideoFragment fragment = new HandBookVideoFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
