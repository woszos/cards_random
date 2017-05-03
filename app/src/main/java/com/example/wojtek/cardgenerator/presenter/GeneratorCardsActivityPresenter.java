package com.example.wojtek.cardgenerator.presenter;

import android.util.Log;

import com.example.wojtek.cardgenerator.model.Card;
import com.example.wojtek.cardgenerator.model.RandomCards;
import com.example.wojtek.cardgenerator.model.ShuffledInfo;
import com.example.wojtek.cardgenerator.net.ApiServiceFactory;
import com.example.wojtek.cardgenerator.net.CardService;
import com.example.wojtek.cardgenerator.presenter.basic.Presenter;

import java.util.List;

import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GeneratorCardsActivityPresenter implements Presenter<OnGeneratorCardsActivityView> {

    //region VARIABLES
    public static final String TAG = GeneratorCardsActivityPresenter.class.getCanonicalName();
    private CardService service;
    private boolean enabled = true;
    private OnGeneratorCardsActivityView view;
    private Subscription subscription;
    private List<Card> list;
    private int remaining;
    private boolean setRemaining = false;
    private boolean random = false;
    //endregion

    //region CONSTRUCTOR
    public GeneratorCardsActivityPresenter() {
        service = ApiServiceFactory.getRetrofit().create(CardService.class);
    }
    //endregion

    //region LIFE CYCLE
    @Override
    public void onViewAttached(OnGeneratorCardsActivityView view) {
        Log.d(TAG, "onViewAttached");
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        Log.d(TAG, "onViewDetached");
        view = null;
    }

    @Override
    public void onDestroyed() {
        Log.d(TAG, "onDestroyed");
        view = null;
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
    //endregion

    //region SETTERS AND GETTERS
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Card> getList() {
        return list;
    }

    public void setRemaining(int remaining) {
        if (!setRemaining) {
            setRemaining = true;
            this.remaining = remaining;
        }
    }
    //endregion

    //region PRESENTER METHOD
    public void random(final String deckId) {
        if (remaining < 5) {
            random = true;
            shuffleCards(deckId);
        }
        else {
            randomCards(deckId);
        }
    }

    private void randomCards(final String deckId) {
        subscription = service.getRandomCards(deckId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<RandomCards>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        enabled = true;
                        if (view != null) {
                            view.onErrorRandom();
                        }
                    }

                    @Override
                    public void onNext(Response<RandomCards> randomCardsResponse) {
                        Log.d(TAG, "onNext");
                        if (randomCardsResponse.code() == 200 && randomCardsResponse.body().getSuccess()) {
                            list = randomCardsResponse.body().getCards();
                            remaining = randomCardsResponse.body().getRemaining();
                            if (randomCardsResponse.body().getRemaining() < 5) {
                                shuffleCards(deckId);
                            } else {
                                enabled = true;
                                if (view != null) {
                                    view.onSetCarts(list);
                                }
                            }
                        } else {
                            if (view != null) {
                                view.onErrorRandom();
                            }
                        }
                    }
                });
    }

    private void shuffleCards(final String deckId) {
        subscription = service.shuffleCards(deckId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ShuffledInfo>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        enabled = true;
                        if (view != null) {
                            view.onErrorShuffleCards();
                        }
                    }

                    @Override
                    public void onNext(Response<ShuffledInfo> shuffledInfoResponse) {
                        Log.d(TAG, "onNext");
                        enabled = true;
                        if (shuffledInfoResponse.code() == 200 && shuffledInfoResponse.body().getSuccess()) {
                            remaining = shuffledInfoResponse.body().getRemaining();
                            if (view != null) {
                                view.onShuffleCardsSuccess();
                            }
                            if (random) {
                                random = false;
                                random(deckId);
                            }
                        }
                        else {
                            if (view != null) {
                                view.onErrorShuffleCards();
                            }
                        }
                    }
                });
    }
    //endregion

}
