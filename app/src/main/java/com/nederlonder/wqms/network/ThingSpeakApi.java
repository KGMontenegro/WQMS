package com.nederlonder.wqms.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThingSpeakApi {

    private ThingSpeakApi() {
    }

    public static ThingSpeakApiAdapter adapter() {
        Gson gson = new Gson();

        return new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/channels/431493/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new ThingSpeakApiClient.Builder().build().client())
                .build()
                .create(ThingSpeakApiAdapter.class);
    }

}
