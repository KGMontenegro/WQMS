package com.nederlonder.wqms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nederlonder.wqms.mock.MockDataLoader;

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
                // todo fix ui/responsiveness issue
                // ^^ do the fetching in LiveGraphActivity
            }
        });

        monitorHistoricalGraph = findViewById(R.id.button_monitor_historical_graph);
        monitorHistoricalGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoricalActivity.start(MonitoringActivity.this);
            }
        });
    }
}
