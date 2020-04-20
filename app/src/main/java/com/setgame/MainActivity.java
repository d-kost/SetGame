package com.setgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    SetCardsFieldView cardsFieldView;
    TextView tvPoints, tvCardsLeft;
    EditText etNickname;
    int token = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPoints = findViewById(R.id.points);
        tvCardsLeft = findViewById(R.id.cardsLeft);
        cardsFieldView = findViewById(R.id.cardsField);
        etNickname = findViewById(R.id.etNickname);

    }


    public void receiveResponse(Response r, RequestType requestType) {

        switch (requestType) {
            case REGISTER: {
                if ((r.getToken() != 0) || (r.getToken() != -1)) {
                    token = r.getToken();
                    sendFetchRequest();
                }
            }
            break;
            case FETCH_CARDS: {
                cardsFieldView.setCardField(new CardField(r.getCards()));
            }
            break;
            case TAKE_SET: {
                tvPoints.setText(Integer.toString(r.getPoints()));
                tvCardsLeft.setText(Integer.toString(r.getCards_left()));
                sendFetchRequest();
            }
            break;
        }

    }

    private void sendFetchRequest() {
        Request req = Request.FetchCardsRequest(token);
        SetServerTask task = new SetServerTask(this, RequestType.FETCH_CARDS);
        task.execute(req);
    }

    public void onSendClick(View view) {
        String nickname = etNickname.getText().toString();
        if (!nickname.equals("")) {
            Request req = Request.RegisterRequest(nickname);
            SetServerTask task = new SetServerTask(this, RequestType.REGISTER);
            task.execute(req);
        }

    }

    public void onCheckSetClick(View view) {
        ArrayList<Card> set = cardsFieldView.checkSet();

        if (set != null) {
            Request req = Request.TakeSetRequest(token, set);
            SetServerTask task = new SetServerTask(this, RequestType.TAKE_SET);
            task.execute(req);
        }
    }

    public void onFindSetClick(View view) {
        cardsFieldView.findSet();
    }
}
