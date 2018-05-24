package com.nederlonder.wqms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.nederlonder.wqms.models.ChannelFeed;
import com.nederlonder.wqms.network.ThingSpeakApi;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AboutActivity extends AppCompatActivity {
    private final String TAG = "[DEBUG/ABOUT]";

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // todo:
        // make images be a slideshow
        // finish about text

        // todo: remove below! (debugging only)
        text = findViewById(R.id.about_text);

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
                        String feedList = "\nentry range:\n";
                        feedList += feed.feeds.get(0).created_at + '\n';
                        feedList += feed.channel.updated_at;
                        text.setText(text.getText() + feedList);
                    }
                });
    }
}
