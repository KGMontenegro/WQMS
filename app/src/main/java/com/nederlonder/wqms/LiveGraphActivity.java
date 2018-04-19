package com.nederlonder.wqms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nederlonder.wqms.models.MockDataPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LiveGraphActivity extends AppCompatActivity {

    private static String TAG_GRAPH_DATA = "LIVE_DATA";
    private String TAG = "[LIVE_GRAPH]";

    private LineChart liveChart;
    private String rawMockData;

    public static void start(Context context, String rawData) {
        Intent intent = new Intent(context, LiveGraphActivity.class);
        intent.putExtra(TAG_GRAPH_DATA, rawData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_graph);

        Gson gson = new Gson();
        rawMockData = getIntent().getStringExtra(TAG_GRAPH_DATA);
        Type type = TypeToken.getParameterized(ArrayList.class, MockDataPoint.class).getType();
        ArrayList<MockDataPoint> mockData = gson.fromJson(rawMockData, type);

        if (mockData.size() <= 0) {
            Log.d(TAG, "no data returned!");
            return;
        }

        liveChart = findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<>();
        for (MockDataPoint datum : mockData) {
            entries.add(new Entry(Integer.parseInt(datum.getId()), (float)datum.getTemp()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "ID vs TEMP"); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        liveChart.setData(lineData);
        liveChart.invalidate(); // refresh


    }
}
