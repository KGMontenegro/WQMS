package com.trollyhandle.wqms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.trollyhandle.wqms.models.ChannelFeed;
import com.trollyhandle.wqms.models.ChannelStatus;
import com.trollyhandle.wqms.network.ThingSpeakApi;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HistoricalActivity extends AppCompatActivity {

    private final String TAG = "[HistoricalActivity]";

    private TextView text;

    public static void start(Context context) {
        Intent intent = new Intent(context, HistoricalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        text = findViewById(R.id.historical_text_view);

        ThingSpeakApi.adapter().getChannelFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChannelFeed>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: status update complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: status update failed", e);
                    }

                    @Override
                    public void onNext(ChannelFeed feed) {
                        Log.d(TAG, "onNext: received status update for channel " + feed.channel.name);
                        String feedList = "\n";
                        for (ChannelFeed.FeedEntry entry : feed.feeds) {
                            feedList += entry.created_at + '\n';
                        }
                        text.setText(text.getText() + "\nlast entry: " + feed.channel.updated_at + feedList);
                    }
                });

    }
}
