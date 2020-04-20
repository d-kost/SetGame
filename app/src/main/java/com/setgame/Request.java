package com.setgame;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

    private String action, nickname;
    private int token;
    private ArrayList<Card> cards;

    public static Request RegisterRequest(String nick) {
        return new Request("register", nick);
    }

    public static Request FetchCardsRequest(Integer token) {
        return new Request("fetch_cards", token);
    }

    public static Request TakeSetRequest(Integer token, ArrayList<Card> cards) {
        return new Request("take_set", token, cards);
    }

    public Request(String action, String nickname) {
        this.action = action;
        this.nickname = nickname;
    }

    public Request(String action, Integer token) {
        this.action = action;
        this.token = token;
    }

    public Request(String action, Integer token, ArrayList<Card> cards) {
        this.action = action;
        this.token = token;
        this.cards = cards;
    }

}
