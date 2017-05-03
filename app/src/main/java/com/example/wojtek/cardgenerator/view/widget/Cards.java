package com.example.wojtek.cardgenerator.view.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.wojtek.cardgenerator.R;
import com.example.wojtek.cardgenerator.model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cards extends RelativeLayout {

    //region VARIABLES
    @BindView(R.id.card1_imageview)
    AppCompatImageView card1;

    @BindView(R.id.card2_imageview)
    AppCompatImageView card2;

    @BindView(R.id.card3_imageview)
    AppCompatImageView card3;

    @BindView(R.id.card4_imageview)
    AppCompatImageView card4;

    @BindView(R.id.card5_imageview)
    AppCompatImageView card5;

    private View view;
    private Context context;
    //endregion

    //region CONSTRUCTORS
    public Cards(Context context) {
        super(context);
        init(context);
    }

    public Cards(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Cards(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Cards(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.widget_cards, this);
        ButterKnife.bind(this, view);
    }
    //endregion

    //region UI
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }

    private void updateSizeInfo() {
        int width = this.getWidth() / 4;
        int height = (int) (width * 1.7);
        int marginLeft = this.getWidth() / 7;
        int marginStart = width / 3;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginStart, (int) (marginStart * 1.5), 0, 0);
        card1.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginStart + marginLeft, marginStart / 2, 0, 0);
        card2.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginStart + marginLeft * 2, 0, 0, 0);
        card3.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginStart + marginLeft * 3, marginStart / 2, 0, 0);
        card4.setLayoutParams(lp);

        lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginStart + marginLeft * 4, (int) (marginStart  * 1.5), 0, 0);
        card5.setLayoutParams(lp);
    }

    public void setCards(List<Card> cards) {
        Glide.with(context)
                .load(cards.get(0).getImage())
                .into(card1);

        Glide.with(context)
                .load(cards.get(1).getImage())
                .into(card2);

        Glide.with(context)
                .load(cards.get(2).getImage())
                .into(card3);

        Glide.with(context)
                .load(cards.get(3).getImage())
                .into(card4);

        Glide.with(context)
                .load(cards.get(4).getImage())
                .into(card5);
    }
    //endregion

}
