package com.example.wojtek.cardgenerator.presenter;

import android.util.Log;

import com.example.wojtek.cardgenerator.model.CardsDeck;
import com.example.wojtek.cardgenerator.net.ApiServiceFactory;
import com.example.wojtek.cardgenerator.net.CardService;
import com.example.wojtek.cardgenerator.presenter.basic.Presenter;

import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter implements Presenter<OnMainActivityView> {

    //region VARIABLES
    private static final String TAG = MainActivityPresenter.class.getCanonicalName();
    private boolean enabled = true;
    private Subscription subscription;
    private OnMainActivityView view;
    private CardService service;
    //endregion

    //region CONSTRUCTOR
    public MainActivityPresenter() {
        service = ApiServiceFactory.getRetrofit().create(CardService.class);
    }
    //endregion

    //region LIFE CYCLE
    @Override
    public void onViewAttached(OnMainActivityView view) {
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
    //endregion

    //region PRESENTER METHOD
    public void getCardsDeck(final int cardDeck) {
        subscription = service.getCardsDeck(cardDeck)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<CardsDeck>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        enabled = true;
                        if (view != null) {
                            view.onError();
                        }
                    }

                    @Override
                    public void onNext(Response<CardsDeck> cardsDeckResponse) {
                        Log.d(TAG, "onNext");
                        enabled = true;
                        if (cardsDeckResponse.code() == 200 && cardsDeckResponse.body().getSuccess()) {
                            if (view != null) {
                                view.onGoToGeneratorCardsActivity(cardsDeckResponse.body().getDeckId(), cardsDeckResponse.body().getRemaining());
                            }
                        }
                        else {
                            if (view != null) {
                                view.onError();
                            }
                        }
                    }
                });
    }
    //endregion
}
