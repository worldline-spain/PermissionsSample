package com.worldline.permissions.serverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by a557114 on 08/10/2015.
 */
public class MainReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "*****", Toast.LENGTH_SHORT).show();
    }
}
