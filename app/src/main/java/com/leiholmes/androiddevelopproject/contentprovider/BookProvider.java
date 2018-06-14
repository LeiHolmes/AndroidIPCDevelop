package com.leiholmes.androiddevelopproject.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Description:
 * author         xulei
 * Date           2018/6/14
 */
public class BookProvider extends ContentProvider {
    private static final String TAG = "TestContentProvider";

    @Override
    public boolean onCreate() {
        Log.i(TAG, "ContentProvider: onCreate, current thread：" + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "ContentProvider: query, current thread：" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(TAG, "ContentProvider: getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(TAG, "ContentProvider: insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "ContentProvider: delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "ContentProvider: update");
        return 0;
    }
}
