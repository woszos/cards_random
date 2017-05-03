package com.example.wojtek.cardgenerator.net;

import com.example.wojtek.cardgenerator.model.CardsDeck;
import com.example.wojtek.cardgenerator.model.RandomCards;
import com.example.wojtek.cardgenerator.model.ShuffledInfo;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface CardService {

    @GET("/api/deck/new/shuffle")
    Observable<Response<CardsDeck>> getCardsDeck(@Query("deck_count") int count);

    @GET("/api/deck/{deck_id}/draw/?count=5")
    Observable<Response<RandomCards>> getRandomCards(@Path("deck_id") String deckId);

    @GET("/api/deck/{deck_id}/shuffle/")
    Observable<Response<ShuffledInfo>> shuffleCards(@Path("deck_id") String deckId);

}
