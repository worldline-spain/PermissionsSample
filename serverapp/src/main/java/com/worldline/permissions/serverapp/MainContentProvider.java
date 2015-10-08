package com.worldline.permissions.serverapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;
import java.util.UUID;

/**
 * Created by a557114 on 08/10/2015.
 */
public class MainContentProvider extends ContentProvider {

    private static final String TAG = "MainContentProvider";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "Query: " + uri);
        MatrixCursor cursor = new MatrixCursor(new String[]{"_ID", "DATA"});
        cursor.addRow(new Object[]{"1", UUID.randomUUID().toString()});
        cursor.addRow(new Object[]{"2", UUID.randomUUID().toString()});
        cursor.addRow(new Object[]{"3", UUID.randomUUID().toString()});
        cursor.addRow(new Object[]{"4", UUID.randomUUID().toString()});
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getType: " + uri);
        return "vnd.android.cursor.item/test";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG, "Insert: " + uri);
        return ContentUris.withAppendedId(uri, 3);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "Delete: " + uri);
        return new Random().nextInt();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "Update: " + uri);
        return new Random().nextInt();
    }
}
