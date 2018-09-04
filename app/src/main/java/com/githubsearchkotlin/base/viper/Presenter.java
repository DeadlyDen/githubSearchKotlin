package com.githubsearchkotlin.base.viper;


public interface Presenter<V extends View> {

    void attachView(V mvpView);

    void detachView();
}
