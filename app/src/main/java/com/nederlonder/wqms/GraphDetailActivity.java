package com.nederlonder.wqms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GraphDetailActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, GraphDetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_detail);
    }
}
