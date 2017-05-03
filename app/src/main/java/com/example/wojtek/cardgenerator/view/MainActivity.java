package com.example.wojtek.cardgenerator.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;

import com.example.wojtek.cardgenerator.R;
import com.example.wojtek.cardgenerator.presenter.MainActivityPresenter;
import com.example.wojtek.cardgenerator.presenter.OnMainActivityView;
import com.example.wojtek.cardgenerator.presenter.basic.PresenterFactory;
import com.example.wojtek.cardgenerator.utils.Directory;
import com.example.wojtek.cardgenerator.view.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BasicActivity<MainActivityPresenter, OnMainActivityView> implements OnMainActivityView {

    //region VARIABLES
    public final String TAG = MainActivity.class.getCanonicalName();

    @BindView(R.id.card_deck_numberpicker)
    NumberPicker cardDeck;

    @BindView(R.id.choose_button)
    AppCompatButton choose;

    private MainActivityPresenter presenter;
    //endregion

    //region LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        choose.setEnabled(presenter.isEnabled());
    }
    //endregion

    //region UI
    private void init() {
        cardDeck.setMinValue(1);
        cardDeck.setMaxValue(5);
        cardDeck.setValue(1);
    }
    //endregion

    //region LISTENERS
    @OnClick(R.id.choose_button)
    public void choseQuantityOfCardDeck() {
        presenter.setEnabled(false);
        choose.setEnabled(false);
        presenter.getCardsDeck(cardDeck.getValue());
    }
    //endregion

    //region OnMainActivityView
    @Override
    public void onError() {
        choose.setEnabled(presenter.isEnabled());
        showSnackbar(getString(R.string.message_error_cannot_connect_with_server));
    }

    private void showSnackbar(final String message) {
            Snackbar.make(findViewById(R.id.content_main), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onGoToGeneratorCardsActivity(String deckId, int remaining) {
        Intent intent = new Intent(this, GeneratorCardsActivity.class);
        intent.putExtra(Directory.DECK_ID_INTENT, deckId);
        intent.putExtra(Directory.REMAINING_INTENT, remaining);
        startActivity(intent);
    }
    //endregion

    //region BASIC ACTIVITY
    @NonNull
    @Override
    protected String tag() {
        return TAG;
    }

    @NonNull
    @Override
    protected PresenterFactory<MainActivityPresenter> getPresenterFactory() {
        return new PresenterFactory<MainActivityPresenter>() {
            @Override
            public MainActivityPresenter create() {
                return new MainActivityPresenter();
            }
        };
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull MainActivityPresenter presenter) {
        this.presenter = presenter;
    }
    //endregion

}
