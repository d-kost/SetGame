package com.setgame;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class CardField {
    private ArrayList<Card> cards; //12 or less cards
    private ArrayList<Card> selectedCards;

    public CardField(ArrayList<Card> cards) {
        this.cards = cards;
        this.selectedCards = new ArrayList<>();
    }


    public boolean checkSelection(float x, float y) {
        for (Card c : cards) {
            if (c.isTouched(x, y)) {
                if (selectedCards.contains(c)) {
                    selectedCards.remove(c);
                } else {
                    selectedCards.add(c);
                }
                return true;
            }
        }
        return false;
    }


    public boolean isSelectedAreSet () {
        if (selectedCards == null || selectedCards.size() != 3) {
            return false;
        }

        Card thirdCard = selectedCards.get(0).getThird(selectedCards.get(1));
        if (thirdCard.equals(selectedCards.get(2))) {
            return true;
        }

        return false;
    }

    public boolean findSet() {
        selectedCards.clear();
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i+1; j < cards.size(); j++) {
                Card c = cards.get(i).getThird(cards.get(j));

                if (cards.contains(c)) {
                    selectedCards.add(cards.get(i));
                    selectedCards.add(cards.get(j));
                    selectedCards.add(c);
                    return true;
                }
            }
        }

        return false;
    }


    public void initCoordinates(int width, int height) {
        int x = 15;
        int y = 20;

        int card_width = (width - 100) / 4;
        int card_height = height / 4;

        for (Card c : cards) {
            c.setParameters(x, y, card_width, card_height);

            x += card_width + 20;
            if ((x + card_width) > width) {
                x = 15;
                y += card_height + 20;
            }
        }

    }
}
