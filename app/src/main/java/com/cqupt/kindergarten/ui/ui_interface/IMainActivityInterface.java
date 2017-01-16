package com.cqupt.kindergarten.ui.ui_interface;

import android.support.v4.app.Fragment;

import com.cqupt.kindergarten.base.MvpView;




public interface IMainActivityInterface extends MvpView{
    void replaceFragment(int position, Fragment fragment);
}
