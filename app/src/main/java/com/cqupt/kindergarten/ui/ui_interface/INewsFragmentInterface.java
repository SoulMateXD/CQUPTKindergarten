package com.cqupt.kindergarten.ui.ui_interface;

import com.cqupt.kindergarten.base.MvpView;

import java.util.List;

/**
 * Created by zhx on 2017/1/15.
 */

public interface INewsFragmentInterface extends MvpView{
    void getBanner(List<String>imgurls);
}
