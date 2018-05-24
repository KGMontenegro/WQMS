package com.nederlonder.wqms;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private String TAG = "[MainActivity]";

    private ImageButton logoButton;
    private Button monitorLiveGraph;
    private Button monitorHistoricalGraph;
    private Button aboutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoButton = findViewById(R.id.logo_button);
        aboutButton = findViewById(R.id.about_button);
        monitorLiveGraph = findViewById(R.id.button_monitor_live_graph);
        monitorHistoricalGraph = findViewById(R.id.button_monitor_historical_graph);

        setupButtons();
    }

    private void setupButtons() {

        // todo set up logo redirections
//        logoButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Rect hitRect = new Rect();
//                view.getHitRect(hitRect);
//                Log.d(TAG, "onTouch: logo hitRect is: " + hitRect);
////                view.per
//                return true;
//            }
//        });
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean clickWasOnRight = true;
                if (clickWasOnRight)
                    openWithChrome("http://www.sonoma.edu");
                else
                    openWithChrome("http://www.atu.edu");
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the About activity
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        monitorLiveGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveGraphActivity.start(MainActivity.this);
            }
        });

        monitorHistoricalGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoricalActivity.start(MainActivity.this);
            }
        });
    }

    private void openWithChrome(String urlString) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            startActivity(intent);
        }
    }
}
