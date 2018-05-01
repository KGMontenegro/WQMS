package com.nederlonder.wqms.network;

import com.nederlonder.wqms.models.ChannelFeed;
import com.nederlonder.wqms.models.ChannelStatus;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ThingSpeakApiAdapter {
//    API Requests
//    private static final String API_READ_KEY = "H0W2QIEABXZKQ6QO";
//    private static final String baseUrl = "https://api.thingspeak.com/channels/431493";

    // get a channel feed
    @GET("feeds.json")
    Observable<ChannelFeed> getChannelFeed();
//    @GET("feeds/last.json")
//    Observable<ChannelFeed> getChannelFeed();

    // get a channel field
//    @GET("fields/{field_num}.json")
//    Observable<TYPE> getChannelField(@Path("field_num") int field);
//    @GET("fields/{field_num}/last.json")
//    Observable<TYPE> getChannelField(@Path("field_num") int field);

    // get channel status updates
    @GET("status.json")
    Observable<ChannelStatus> getStatusUpdate();

}
