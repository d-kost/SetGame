package com.setgame;

public class Card {
    int count, color, shape, fill;
    int x ,y, width, height;

    public Card(int count, int color , int shape, int fill) {
        this.count = count;
        this.color = color;
        this.shape = shape;
        this.fill = fill;

    }

    public void setParameters(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Card.class){
            return false;
        }

        Card c = (Card) obj;
        return this.fill == c.fill && this.color == c.color && this.count == c.count && this.shape == c.shape;
    }

    @Override
    public String toString() {
        return "count: " + count + ", color: " + color + ", shape: " + shape + ", fill: " + fill;
    }

    public Card getThird(Card c) {

        int count;
        int color;
        int shape;
        int fill;


        count = thirdProperty(this.count, c.count);
        fill = thirdProperty(this.fill, c.fill);
        shape = thirdProperty(this.shape, c.shape);
        color = thirdProperty(this.color, c.color);

        Card thirdCard = new Card(count, color, shape, fill);
        return thirdCard;
    }

    private int thirdProperty(int one, int two){
        int result;
        if (one == two){
            result = one;
        } else if (one == 1 && two == 2 || one == 2 && two == 1) {
            result = 3;
        } else if (one == 1 && two == 3 || one == 3 && two == 1) {
            result = 2;
        } else {
            result = 1;
        }
        return result;
    }
}
