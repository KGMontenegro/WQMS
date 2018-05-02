package com.nederlonder.wqms.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThingSpeakApiClient {
    private static final String API_READ_KEY = "H0W2QIEABXZKQ6QO";
    private OkHttpClient client;

    private ThingSpeakApiClient() {
        client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        HttpUrl url = request.url();
//
//                        HttpUrl modifiedUrl = url.newBuilder()
////                                .addQueryParameter("api_key", API_READ_KEY)
//                                .build();
//
//                        Request.Builder requestBuilder = request.newBuilder()
//                                .url(modifiedUrl);
//                        Request modifiedRequest = requestBuilder.build();
//                        return chain.proceed(modifiedRequest);
//                    }
//                })
                .build();
    }

    public OkHttpClient client() {
        return client;
    }

    public static class Builder {
        private static ThingSpeakApiClient client = new ThingSpeakApiClient();

        public ThingSpeakApiClient build() {
            ThingSpeakApiClient output = client;
            client = new ThingSpeakApiClient();
            return output;
        }

    }

}
