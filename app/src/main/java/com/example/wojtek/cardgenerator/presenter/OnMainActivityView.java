package com.example.wojtek.cardgenerator.presenter;

public interface OnMainActivityView {

    void onError();
    void onGoToGeneratorCardsActivity(String deckId, int remaining);
}
