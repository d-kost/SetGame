package com.setgame;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetServerTask extends AsyncTask<Request, Void, Response> {
    private final MainActivity activity;
    private RequestType requestType;

    public SetServerTask(MainActivity activity, RequestType requestType) {
        this.activity = activity;
        this.requestType = requestType;
    }

    public Response requestToSetServer(Request req) {
        Gson gson = new Gson();
        String API_URL = "http://194.176.114.21:8052";
        try {
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // setting POST method
            OutputStream out = urlConnection.getOutputStream();
            String outJSON = gson.toJson(req);
            Log.i("mytag", "out: "+outJSON);
            out.write(outJSON.getBytes());

            InputStream stream = urlConnection.getInputStream();
            Response response = gson.fromJson(new InputStreamReader(stream), Response.class);
            return response;

        } catch (IOException e) { return null; }

    }

    @Override
    protected Response doInBackground(Request... requests) {
        Request r = requests[0];
        return requestToSetServer(r);
    }

    @Override
    protected void onPostExecute(Response response) {
        Log.i("mytag", "token: " + response.getToken());
        activity.receiveResponse(response, requestType);

    }

}
