package com.githubsearchkotlin.base.viper;


public interface InteractorCallback<T> {
    void onSuccess(T model, boolean onMore);

    void onFailure(Throwable throwable);
}
