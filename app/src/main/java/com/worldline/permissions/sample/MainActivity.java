package com.worldline.permissions.sample;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.worldline.permissions.serverapp.IConnectionInterface;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String QUERY_ACTION = "com.worldline.permissions.server.QUERY_APP";
    public static final String BIND_ACTION = "com.worldline.permissions.server.REQUEST_INFO";
    public static final String BROADCAST_ACTION = "com.worldline.permissions.server.ADD_INFO";
    private static final String ADD_INFO_PERMISSION = "com.worldline.permissions.ADD_INFO";
    public static final String AUTHORITY = "com.worldline.permissions.serverapp";

    private View content;
    private TextView serviceContent;
    private TextView providerData;
    private ServiceConnection bindConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IConnectionInterface binder = IConnectionInterface.Stub.asInterface(service);

            try {
                setData(binder.basicInt());

                setData(binder.basicLong());

                setData(binder.basicBoolean());

                setData(binder.basicFloat());

                setData(binder.basicDouble());

                setData(binder.basicString());

            } catch (RemoteException e) {
                e.printStackTrace();
                setData(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);
        serviceContent = (TextView) findViewById(R.id.serviceContent);
        providerData = (TextView) findViewById(R.id.providerData);

        findViewById(R.id.queryActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQueryActivity();
            }
        });

        findViewById(R.id.bindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindExternalService();
            }
        });

        findViewById(R.id.broadcastData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcastEvent();
            }
        });

        findViewById(R.id.contentProvider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestProviderData();
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
            Snackbar.make(content, "Permission QUERY_INFO not granted", Snackbar.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException nfe) {
            Snackbar.make(content, "Server app not found", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void bindExternalService() {
        Intent intent = new Intent(BIND_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentServices(intent, Context.BIND_AUTO_CREATE);

        if (resolveInfoList.size() > 0) {
            for (ResolveInfo resolveInfo : resolveInfoList) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;

                ComponentName name = new ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name);

                Intent serviceIntent = new Intent();
                serviceIntent.setAction(BIND_ACTION);
                serviceIntent.setComponent(name);

                try {
                    bindService(serviceIntent, bindConnection, Context.BIND_AUTO_CREATE);
                } catch (SecurityException se) {
                    se.printStackTrace();
                    Snackbar.make(content, "Permission REQUEST_INFO not granted", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setData(Object data) {
        serviceContent.setText(serviceContent.getText().toString() + "\n" + (data.getClass()) + ": " + String.valueOf(data));
    }

    private void sendBroadcastEvent() {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        sendBroadcast(intent);

        if (ContextCompat.checkSelfPermission(this, ADD_INFO_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(content, "Without ADD_INFO permission, broadcast is not delivered", Snackbar.LENGTH_LONG).show();
        }
    }

    private void requestProviderData() {
        try {
            Uri uri = Uri.EMPTY.buildUpon().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).build();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_ID", "5");
            contentValues.put("DATA", UUID.randomUUID().toString());

            String type = getContentResolver().getType(uri);
            setProviderData("type", type);

            Cursor query = getContentResolver().query(uri, null, null, null, null);
            setProviderData("query", query);

            Uri insert = getContentResolver().insert(uri, contentValues);
            setProviderData("insert", insert);

            int update = getContentResolver().update(uri, contentValues, null, null);
            setProviderData("update", update);

            int delete = getContentResolver().delete(uri, null, null);
            setProviderData("delete", delete);
        } catch (SecurityException se) {
            se.printStackTrace();

            Snackbar.make(content, "Permissions QUERY_INFO & ADD_INFO are not granted", Snackbar.LENGTH_LONG).show();
        }
    }

    private void setProviderData(String type, Object data) {
        providerData.setText(providerData.getText().toString() + "\n" + type + ": " + String.valueOf(data));
    }

}
