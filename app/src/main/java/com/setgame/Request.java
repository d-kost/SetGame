package com.setgame;

public class Request {

    String action, nickname;
    int token;

    public static Request RegisterRequest(String nick) {
        return new Request("register", nick);
    }

    public static Request FetchCardsRequest(Integer token) {
        return new Request("fetch_cards", token);
    }

    public Request(String action, String nickname) {
        this.action = action;
        this.nickname = nickname;
    }

    public Request(String action, Integer token) {
        this.action = action;
        this.token = token;
    }

}
