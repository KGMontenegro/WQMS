package com.nederlonder.wqms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    public static void start(Context context, String rawData) {
        Intent intent = new Intent(context, LiveGraphActivity.class);
        intent.putExtra(TAG_GRAPH_DATA, rawData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_graph);

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
                        ((LineChart)findViewById(R.id.chart1)).setNoDataText("Failed to load");
                        ((LineChart)findViewById(R.id.chart2)).setNoDataText("Failed to load");
                        ((LineChart)findViewById(R.id.chart3)).setNoDataText("Failed to load");
                        ((LineChart)findViewById(R.id.chart4)).setNoDataText("Failed to load");
                        ((LineChart)findViewById(R.id.chart5)).setNoDataText("Failed to load");
                        ((LineChart)findViewById(R.id.chart6)).setNoDataText("Failed to load");
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

    private void populateGraph(ChannelFeed data) {
        Map<String, List<Entry>> entries = new HashMap<>();
        for (String type : fieldNames)
            entries.put(type, new ArrayList<Entry>());

        for (ChannelFeed.FeedEntry datum : data.feeds) {
            if (datum.field1 != null)
                entries.get(fieldNames[0]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field1)));
            if (datum.field2 != null)
                entries.get(fieldNames[1]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field2)));
            if (datum.field3 != null)
                entries.get(fieldNames[2]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field3)));
            if (datum.field4 != null)
                entries.get(fieldNames[3]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field4)));
            if (datum.field5 != null)
                entries.get(fieldNames[4]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field5)));
            if (datum.field6 != null)
                entries.get(fieldNames[5]).add(new Entry(datum.entry_id, Float.parseFloat(datum.field6)));
        }

        for (String type : fieldNames) {
            LineDataSet dataSet = new LineDataSet(entries.get(type), type); // add entries to dataset
            LineData lineData = new LineData(dataSet);

            charts.get(type).setData(lineData);
            charts.get(type).invalidate(); // refresh chart graphics
        }
    }

    private void configureCharts() {
        charts.put(fieldNames[0], (LineChart)findViewById(R.id.chart1));
        charts.put(fieldNames[1], (LineChart)findViewById(R.id.chart2));
        charts.put(fieldNames[2], (LineChart)findViewById(R.id.chart3));
        charts.put(fieldNames[3], (LineChart)findViewById(R.id.chart4));
        charts.put(fieldNames[4], (LineChart)findViewById(R.id.chart5));
        charts.put(fieldNames[5], (LineChart)findViewById(R.id.chart6));

        for (LineChart chart : charts.values()) {
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            chart.getDescription().setEnabled(false);
            chart.setDrawBorders(true);
            chart.setTouchEnabled(false);

            chart.setMaxVisibleValueCount(0);
        }
    }
}
