package com.worldline.permissions.serverapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.queryActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQueryActivity();
            }
        });
    }

    private void launchQueryActivity() {
        Intent intent = new Intent(this, QueryInfoActivity.class);
        startActivity(intent);
    }
}
