package com.leiholmes.androidipcdevelop.contentprovider;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leiholmes.androidipcdevelop.R;

/**
 * Description:   Contentprovider使用
 * author         xulei
 * Date           2018/6/14 16:24
 */
public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://com.leiholmes.androiddevelopproject.contentprovider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
    }
}
