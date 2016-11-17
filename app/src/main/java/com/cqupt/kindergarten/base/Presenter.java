package com.cqupt.kindergarten.base;



public interface Presenter<T extends MvpView> {

    void attachView(T mvpView);

    void detachView();

}
