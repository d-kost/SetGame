package com.setgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    SetCardsFieldView cardsFieldView;
    TextView tv;
    EditText etNickname;
    int token = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.text);
        cardsFieldView = findViewById(R.id.cardsField);
        etNickname = findViewById(R.id.etNickname);

//        Request req = Request.FetchCardsRequest(8754661);
//        SetServerTask task = new SetServerTask(this);
//        task.execute(req);


    }

    // TODO: добавить кнопку для отправки карт
    // TODO: отобразить число очков игрока

    public void receiveResponse(Response r) {
        if ((token == 0) && (r.token != 0)) {
            token = r.token;

            Request req = Request.FetchCardsRequest(token);
            SetServerTask task = new SetServerTask(this);
            task.execute(req);
        }

        if (r.cards != null) {
            cardsFieldView.setCardField(new CardField(r.cards));
        }

    }

    public void onSendClick(View view) {
        String nickname = etNickname.getText().toString();
        if (!nickname.equals("")) {
            Request req = Request.RegisterRequest(nickname);
            SetServerTask task = new SetServerTask(this);
            task.execute(req);
        }

    }
}
