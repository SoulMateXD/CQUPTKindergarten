package com.cqupt.kindergarten.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView ( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        mView = inflater.inflate ( getLayoutId ( ), container, false );
        setUpComponent ( );
        initView ( );
        initData ( );
        return mView;
    }


    public abstract int getLayoutId ( );

    public abstract void initView ( );

    public abstract void initData ( );


    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
    }

    @Override
    public void onDetach ( ) {
        super.onDetach ( );
    }

    //模板方法,留给子类编写
    public abstract void setUpComponent ( );
}
