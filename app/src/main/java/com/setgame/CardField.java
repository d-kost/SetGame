package com.setgame;

import java.util.ArrayList;

public class CardField {
    //12 or less cards
    ArrayList<Card> cards;
    ArrayList<Card> selectedCards;

    public CardField(ArrayList<Card> cards) {
        this.cards = cards;
    }


    public Card cardTouched(float x, float y) {
        return  null;
    }
    public boolean isSelectedAreSet () {
        if (selectedCards == null || selectedCards.size() != 3) {
            return false;
        }
        return false;
    }

    public void initCoordinates(int width, int height) {
        int x = 10;
        int y = 20;

        int card_width = (width - 100) / 4;
        int card_height = (height - 300) / 4;

        for (Card c : cards) {
            c.setParameters(x, y, card_width, card_height);

            x += card_width + 20;
            if ((x + card_width) > width) {
                x = 10;
                y += card_height + 20;
            }
        }

    }
}
