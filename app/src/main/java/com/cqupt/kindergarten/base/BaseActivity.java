package com.cqupt.kindergarten.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( getLayoutId ( ) );
        setUpComponent ( );
        initView ( );
        initData ( );

    }

    public abstract void setUpComponent ( );


    public abstract int getLayoutId ( );

    public abstract void initView ( );

    public abstract void initData ( );
}