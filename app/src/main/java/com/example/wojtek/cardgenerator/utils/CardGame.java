package com.example.wojtek.cardgenerator.utils;

import android.util.Log;

import com.example.wojtek.cardgenerator.model.Card;

import java.util.Collections;
import java.util.List;

public class CardGame {

    private static String[] COLOUR = {"DIAMONDS", "HEARTS", "CLUBS", "SPADES"};
    public static String ACE = "ACE";
    public static String QUEEN = "QUEEN";
    public static String JACK = "JACK";
    public static String KING = "KING";

    public static boolean haveColor(List<Card> list) {
        int[] colours = new int[4];//0 - DIAMONDS, 1 - HEARTS, 2 - CLUBS, 3 - SPADES

        boolean isColour = false;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < 4; j++) {
                if (list.get(i).getSuit().equals(COLOUR[j])) {
                    colours[j]++;
                    if (colours[j] == 3) {
                        isColour = true;
                    }
                    break;
                }
            }
        }

        return isColour;
    }

    public static boolean haveStairs(List<Card> list) {

        Collections.sort(list);

        for (int i = 0; i < list.size(); i++) {
            Log.d("TAG", list.get(i).getValue());
        }

        int stairs = 1;
        for (int i = 1; i < list.size(); i++) {
            int c1 = getValueInt(list.get(i-1).getValue());
            int c2 = getValueInt(list.get(i).getValue());
            if (c1 + 1 == c2) {
                stairs++;
            }
            else {
                stairs = 1;
            }
            if (stairs == 3) {
                return true;
            }
        }

        return false;

    }

    public static boolean haveFigures(List<Card> list) {

        int figures = 0;
        for (int i = 0; i < list.size(); i++) {
            if (getValueInt(list.get(i).getValue()) > 10 || getValueInt(list.get(i).getValue()) == 1) {
                figures++;
            }
        }

        return figures > 2;
    }

    public static boolean haveTwins(List<Card> list) {

        int[] cards = new int[13];

        for (int i = 0; i < list.size(); i++) {
            cards[getValueInt(list.get(i).getValue()) - 1]++;
        }

        boolean twins = false;
        for (int i = 0; i < 12; i++) {
            if (cards[i] > 2) {
                twins = true;
                break;
            }
        }

        return twins;
    }

    public static int getValueInt(String value) {
        int v = 0;
        try {
            v = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            if (value.equals(CardGame.ACE)) {
                v = 1;
            }
            else if (value.equals(CardGame.JACK)) {
                v = 11;
            }
            else if (value.equals(CardGame.QUEEN)) {
                v = 12;
            }
            else if (value.equals(CardGame.KING)) {
                v = 13;
            }
        }
        return v;
    }

}
