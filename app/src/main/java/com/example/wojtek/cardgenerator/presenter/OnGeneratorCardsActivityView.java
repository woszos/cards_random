package com.example.wojtek.cardgenerator.presenter;

import com.example.wojtek.cardgenerator.model.Card;

import java.util.List;

public interface OnGeneratorCardsActivityView {
    void onSetCarts(List<Card> cards);
    void onShuffleCardsSuccess();
    void onErrorRandom();
    void onErrorShuffleCards();
}
