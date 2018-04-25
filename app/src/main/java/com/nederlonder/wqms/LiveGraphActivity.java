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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nederlonder.wqms.models.MockDataPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveGraphActivity extends AppCompatActivity {

    private static String TAG_GRAPH_DATA = "LIVE_DATA";
    private String TAG = "[LIVE_GRAPH]";

    private Map<ChartType, LineChart> charts = new HashMap<>();
    private List<MockDataPoint> graphDataPoints;

    private Map<ChartType, String> labels = new HashMap<>();


    public static void start(Context context, String rawData) {
        Intent intent = new Intent(context, LiveGraphActivity.class);
        intent.putExtra(TAG_GRAPH_DATA, rawData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_graph);

        configureCharts();

        graphDataPoints = parseData(getIntent().getStringExtra(TAG_GRAPH_DATA), 100);
        if (graphDataPoints.size() <= 0) {
            Log.d(TAG, "no data returned!");
            return;
        }
        populateGraph();
    }

    private void populateGraph() {
        Map<ChartType, List<Entry>> entries = new HashMap<>();
        for (ChartType type : ChartType.values()) entries.put(type, new ArrayList<Entry>());

        for (MockDataPoint datum : graphDataPoints) {
            entries.get(ChartType.TEMPERATURE)
                    .add(new Entry(Integer.parseInt(datum.getId()), (float)datum.getTemp()));
            entries.get(ChartType.HUMIDITY)
                    .add(new Entry(Integer.parseInt(datum.getId()), Float.parseFloat(datum.getHumidity())));
            entries.get(ChartType.CO2)
                    .add(new Entry(Integer.parseInt(datum.getId()), Float.parseFloat(datum.getCo2())));
            entries.get(ChartType.PARTICLE)
                    .add(new Entry(Integer.parseInt(datum.getId()), Float.parseFloat(datum.getParticle())));
            entries.get(ChartType.OZONE)
                    .add(new Entry(Integer.parseInt(datum.getId()), Float.parseFloat(datum.getOzone())));
        }

        for (ChartType type : ChartType.values()) {
            LineDataSet dataSet = new LineDataSet(entries.get(type), type.name()); // add entries to dataset
            LineData lineData = new LineData(dataSet);

            charts.get(type).setData(lineData);
            charts.get(type).invalidate(); // refresh chart graphics
        }
    }

    private void configureCharts() {
        charts.put(ChartType.TEMPERATURE, (LineChart)findViewById(R.id.chart_temperature));
        charts.put(ChartType.HUMIDITY, (LineChart)findViewById(R.id.chart_humidity));
        charts.put(ChartType.CO2, (LineChart)findViewById(R.id.chart_co2));
        charts.put(ChartType.PARTICLE, (LineChart)findViewById(R.id.chart_particle));
        charts.put(ChartType.OZONE, (LineChart)findViewById(R.id.chart_ozone));

        for (LineChart chart : charts.values()) {
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            chart.getDescription().setEnabled(false);
            chart.setDrawBorders(true);
            chart.setTouchEnabled(false);
        }
    }

    private List<MockDataPoint> parseData(String rawData, int limit) {
        // TODO use date not data-id for limit?

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(ArrayList.class, MockDataPoint.class).getType();
        List<MockDataPoint> result = gson.fromJson(rawData, type);
        if (result.size() > limit)
            result = result.subList(result.size() - limit, result.size());
        return result;
    }
}
