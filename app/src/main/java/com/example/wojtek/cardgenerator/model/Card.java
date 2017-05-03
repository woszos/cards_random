package com.example.wojtek.cardgenerator.model;

import com.example.wojtek.cardgenerator.utils.CardGame;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card implements Comparable<Card> {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("suit")
    @Expose
    private String suit;
    @SerializedName("code")
    @Expose
    private String code;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int compareTo(Card o) {

        int c1 = CardGame.getValueInt(value);
        int c2 = CardGame.getValueInt(o.getValue());

        if (c1 == c2) {
            return 0;
        }
        if (c1 < c2) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
