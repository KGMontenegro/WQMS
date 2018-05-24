package com.trollyhandle.wqms.network;

import com.trollyhandle.wqms.models.ChannelFeed;
import com.trollyhandle.wqms.models.ChannelStatus;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ThingSpeakApiAdapter {
    // API Requests

    // get a channel feed
    @GET("feeds.json")
    Observable<ChannelFeed> getChannelFeed();
    @GET("feeds.json")
    Observable<ChannelFeed> getChannelFeed(@Query("start") String startTime, @Query("end") String endTime);
    @GET("feeds/last.json")
    Observable<ChannelFeed> getLastChannelFeed();

    // get a channel field
//    @GET("fields/{field_num}.json")
//    Observable<TYPE> getChannelField(@Path("field_num") int field);
//    @GET("fields/{field_num}/last.json")
//    Observable<TYPE> getLastChannelField(@Path("field_num") int field);

    // get channel status updates
    @GET("status.json")
    Observable<ChannelStatus> getStatusUpdate();

}
