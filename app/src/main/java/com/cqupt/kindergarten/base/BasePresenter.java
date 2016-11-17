package com.cqupt.kindergarten.base;




public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mvpView;
    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    public boolean isViewAttached() {
        return !(this.mvpView == null);
    }

    public T getMvpView() {
        return this.mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached())
            throw new MvpViewNotAttacheException();
    }

    public static class MvpViewNotAttacheException extends RuntimeException {
        public MvpViewNotAttacheException() {
            super("please call attachView()");
        }
    }
}

