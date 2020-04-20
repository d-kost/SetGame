package com.setgame;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String status;
    private int token, cards_left, points;
    private ArrayList<Card> cards;
}
