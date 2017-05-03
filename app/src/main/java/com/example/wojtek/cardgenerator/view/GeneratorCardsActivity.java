package com.example.wojtek.cardgenerator.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.example.wojtek.cardgenerator.R;
import com.example.wojtek.cardgenerator.model.Card;
import com.example.wojtek.cardgenerator.presenter.GeneratorCardsActivityPresenter;
import com.example.wojtek.cardgenerator.presenter.OnGeneratorCardsActivityView;
import com.example.wojtek.cardgenerator.presenter.basic.PresenterFactory;
import com.example.wojtek.cardgenerator.utils.CardGame;
import com.example.wojtek.cardgenerator.utils.Directory;
import com.example.wojtek.cardgenerator.view.basic.BasicActivity;
import com.example.wojtek.cardgenerator.view.widget.Cards;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GeneratorCardsActivity extends BasicActivity<GeneratorCardsActivityPresenter, OnGeneratorCardsActivityView> implements OnGeneratorCardsActivityView {

    //region VARIABLES
    public final String TAG = GeneratorCardsActivity.class.getCanonicalName();

    private GeneratorCardsActivityPresenter presenter;
    private String deckId;
    private int remaining;

    @BindView(R.id.random_button)
    AppCompatButton random;

    @BindView(R.id.cards_widget)
    Cards cards;

    @BindView(R.id.colours_textview)
    AppCompatTextView colours;

    @BindView(R.id.stairs_textview)
    AppCompatTextView stairs;

    @BindView(R.id.figures_textview)
    AppCompatTextView figures;

    @BindView(R.id.twins_textview)
    AppCompatTextView twins;
    //endregion

    //region LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_cards);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIntent(getIntent().getExtras());

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setRemaining(remaining);
        random.setEnabled(presenter.isEnabled());
        if (presenter.getList() != null) {
            List<Card> list = presenter.getList();
            colours.setText(String.format("Color - %s", booleanToHaveString(CardGame.haveColor(list))));
            stairs.setText(String.format("Stairs - %s", booleanToHaveString(CardGame.haveStairs(list))));
            figures.setText(String.format("Figures - %s", booleanToHaveString(CardGame.haveFigures(list))));
            twins.setText(String.format("Twins - %s", booleanToHaveString(CardGame.haveTwins(list))));
            cards.setCards(list);
        }
    }

    private void getIntent(Bundle bundle) {
        deckId = bundle.getString(Directory.DECK_ID_INTENT);
        remaining = bundle.getInt(Directory.REMAINING_INTENT);
    }
    //endregion

    //region LISTENERS
    @OnClick(R.id.random_button)
    public void random() {
        presenter.setEnabled(false);
        random.setEnabled(false);
        presenter.random(deckId);
    }
    //endregion

    //region OnGeneratorCardsActivityView
    @Override
    public void onSetCarts(List<Card> cards) {
        random.setEnabled(true);
        colours.setText(String.format(getString(R.string.message_colour), booleanToHaveString(CardGame.haveColor(cards))));
        stairs.setText(String.format(getString(R.string.message_stairs), booleanToHaveString(CardGame.haveStairs(cards))));
        figures.setText(String.format(getString(R.string.message_figures), booleanToHaveString(CardGame.haveFigures(cards))));
        twins.setText(String.format(getString(R.string.message_twins), booleanToHaveString(CardGame.haveTwins(cards))));
        this.cards.setCards(cards);
    }

    private String booleanToHaveString(boolean value) {
        if (value) {
            return "yes";
        }
        return "no";
    }

    @Override
    public void onShuffleCardsSuccess() {
        cards.setCards(presenter.getList());
        random.setEnabled(true);
        showSnackbar(getString(R.string.message_shuffled_success));
    }

    @Override
    public void onErrorRandom() {
        showSnackbar(getString(R.string.message_error_cannot_connect_with_server));
    }

    @Override
    public void onErrorShuffleCards() {
        showSnackbar(getString(R.string.message_error_cannot_sort_cards));
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.content_generator_cards), message, Snackbar.LENGTH_LONG).show();
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
    protected PresenterFactory<GeneratorCardsActivityPresenter> getPresenterFactory() {
        return new PresenterFactory<GeneratorCardsActivityPresenter>() {
            @Override
            public GeneratorCardsActivityPresenter create() {
                return new GeneratorCardsActivityPresenter();
            }
        };
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull GeneratorCardsActivityPresenter presenter) {
        this.presenter = presenter;
    }
    //endregion

}
