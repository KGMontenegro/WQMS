package com.nederlonder.wqms.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RawJsonFetcher extends AsyncTask<String, String, String> {

    private String baseUrl;
    private CallbackListener listener;
//    private Map<String, String> queryParameters;
//    private Map<String, String> ;

    public RawJsonFetcher(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setListener(CallbackListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
//        String searchParams = params[0];
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();

//        urlBuilder.addQueryParameter("_app_key", apiKey);
//        urlBuilder.addQueryParameter("_app_id", appId);
//        urlBuilder.addQueryParameter("your_search_parameters", searchParams);
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
