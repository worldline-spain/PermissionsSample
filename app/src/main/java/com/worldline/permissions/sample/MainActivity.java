package com.worldline.permissions.sample;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String QUERY_ACTION = "com.worldline.permissions.server.QUERY_APP";
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        findViewById(R.id.queryActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQueryActivity();
            }
        });
    }

    private void launchQueryActivity() {
        Intent intent = new Intent(QUERY_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        try {
            startActivity(intent);
        } catch (SecurityException se) {
            se.printStackTrace();
            Snackbar.make(content, "Permission not available", Snackbar.LENGTH_SHORT).show();
        }
    }
}
