package com.nederlonder.wqms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nederlonder.wqms.mock.MockDataPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoricalActivity extends AppCompatActivity {

    private final String TAG = "[HistoricalActivity]";
    private static String TAG_RAW_DATA = "LIVE_DATA";


    public static void start(Context context, String rawData) {
        Intent intent = new Intent(context, HistoricalActivity.class);
        intent.putExtra(TAG_RAW_DATA, rawData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        Gson gson = new Gson();
        String rawMockData = getIntent().getStringExtra(TAG_RAW_DATA);
        Type type = TypeToken.getParameterized(ArrayList.class, MockDataPoint.class).getType();
        ArrayList<MockDataPoint> mockData = gson.fromJson(rawMockData, type);
        Log.d(TAG, "loaded " + mockData.size() + " elements");

        TextView text = findViewById(R.id.historical_text_view);
        text.setText("Data History Graph (coming soon)\n\nmock data: ("+mockData.size()+" data points)\n" + mockData);

    }
}
