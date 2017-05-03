package com.example.wojtek.cardgenerator.presenter.basic;

public interface Presenter<V>{
    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
