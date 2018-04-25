package com.nederlonder.wqms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nederlonder.wqms.network.MockDataLoader;
import com.nederlonder.wqms.network.MockJsonFetcher;
import com.nederlonder.wqms.network.RawJsonFetcher;

/**
 * Page to select which graph to view - live or time range.
 */
public class MonitoringActivity extends AppCompatActivity {

    private Button monitorLiveGraph;
    private Button monitorHistoricalGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        monitorLiveGraph = findViewById(R.id.button_monitor_live_graph);
        monitorLiveGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MockDataLoader loader = new MockDataLoader();
                String mockData = loader.readJSONfile(getResources().openRawResource(R.raw.mock_data));
                LiveGraphActivity.start(MonitoringActivity.this, mockData);
//                MockJsonFetcher mockDataFetcher = new MockJsonFetcher();
//                mockDataFetcher.setListener(new RawJsonFetcher.CallbackListener() {
//                    @Override
//                    public void onCallback(String result) {
//                        LiveGraphActivity.start(MonitoringActivity.this, result);
//                    }
//                });
//                mockDataFetcher.execute();
                // todo fix ui/responsiveness issue
            }
        });

        monitorHistoricalGraph = findViewById(R.id.button_monitor_historical_graph);
        monitorHistoricalGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MockDataLoader loader = new MockDataLoader();
                String mockData = loader.readJSONfile(getResources().openRawResource(R.raw.mock_data));
                HistoricalActivity.start(MonitoringActivity.this, mockData);
//                RawJsonFetcher mockDataFetcher = new RawJsonFetcher();
//                mockDataFetcher.setListener(new RawJsonFetcher.CallbackListener() {
//                    @Override
//                    public void onCallback(String result) {
//                        HistoricalActivity.start(MonitoringActivity.this, result);
//                    }
//                });
//                mockDataFetcher.execute();
                // todo fix ui/responsiveness issue
            }
        });
    }
}
