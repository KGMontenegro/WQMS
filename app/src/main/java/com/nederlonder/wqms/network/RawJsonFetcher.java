package com.nederlonder.wqms.network;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RawJsonFetcher extends AsyncTask<String, String, String> {

    public static final String mockDataUrl = "http://130.157.3.112/ceistudent1/tutorial/montenek/default.php";

    private String baseUrl;
    private CallbackListener listener;

    public RawJsonFetcher() {
        baseUrl = mockDataUrl;
    }

    public RawJsonFetcher(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setListener(CallbackListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Response response;

        try {
            response = client.newCall(request).execute();
            if (response != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (listener != null)
            listener.onCallback(result);
    }

    public interface CallbackListener {
        void onCallback(String result);
    }
}
