package com.nederlonder.wqms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nederlonder.wqms.models.ChannelFeed;
import com.nederlonder.wqms.network.ThingSpeakApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LiveGraphActivity extends AppCompatActivity {

    private static String TAG_GRAPH_DATA = "LIVE_DATA";
    private String TAG = "[LIVE_GRAPH]";

    private String[] fieldNames;
    private Map<String, LineChart> charts = new HashMap<>();

    private ProgressBar progressBar;
    private LinearLayout chartListContainer;
    private TextView failedLoadText;


    public static void start(Context context) {
        Intent intent = new Intent(context, LiveGraphActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String rawData) {
        Intent intent = new Intent(context, LiveGraphActivity.class);
        intent.putExtra(TAG_GRAPH_DATA, rawData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_graph);

        progressBar = findViewById(R.id.graph_list_progress_bar);
        chartListContainer = findViewById(R.id.graph_list_container);
        failedLoadText = findViewById(R.id.text_loading_failed);


        ThingSpeakApi.adapter().getChannelFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChannelFeed>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: status update complete");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: status update failed", e);

                        progressBar.setVisibility(View.GONE);
                        failedLoadText.setVisibility(View.VISIBLE);

                        Toast.makeText(LiveGraphActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ChannelFeed feed) {
                        Log.d(TAG, "onNext: received feed for channel " + feed.channel.name);

                        fieldNames = feed.getFieldNames();
                        configureCharts();
                        populateGraph(feed);
                    }
                });
    }

    private void configureCharts() {

        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1
        );

        for (String fieldName : fieldNames) {
            LineChart chart = new LineChart(this);
            chart.setLayoutParams(params);
            chartListContainer.addView(chart);

            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            chart.getDescription().setEnabled(false);
            chart.setDrawBorders(true);
//            chart.setTouchEnabled(false);

            chart.setMaxVisibleValueCount(0);
            chart.getAxisLeft().setLabelCount(5);
            chart.getAxisRight().setLabelCount(5);

            chart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GraphDetailActivity.start(LiveGraphActivity.this);
                }
            });

            charts.put(fieldName, chart);


        }
    }

    private void populateGraph(ChannelFeed data) {
        Map<String, List<Entry>> entries = new HashMap<>();
        for (String type : fieldNames)
            entries.put(type, new ArrayList<Entry>());

        for (ChannelFeed.FeedEntry entry : data.feeds) {
            if (entry.field1 != null)
                entries.get(fieldNames[0]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field1)));
            if (entry.field2 != null)
                entries.get(fieldNames[1]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field2)));
            if (entry.field3 != null)
                entries.get(fieldNames[2]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field3)));
            if (entry.field4 != null)
                entries.get(fieldNames[3]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field4)));
            if (entry.field5 != null)
                entries.get(fieldNames[4]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field5)));
            if (entry.field6 != null)
                entries.get(fieldNames[5]).add(new Entry(entry.entry_id, Float.parseFloat(entry.field6)));
        }

        for (String type : fieldNames) {
            LineDataSet dataSet = new LineDataSet(entries.get(type), type); // add entries to dataset
            LineData lineData = new LineData(dataSet);

            charts.get(type).setData(lineData);
            charts.get(type).invalidate(); // refresh chart graphics
        }
    }
}
