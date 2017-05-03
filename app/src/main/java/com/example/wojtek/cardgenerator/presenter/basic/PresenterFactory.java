package com.example.wojtek.cardgenerator.presenter.basic;

public interface PresenterFactory<T extends Presenter> {
    T create();
}
